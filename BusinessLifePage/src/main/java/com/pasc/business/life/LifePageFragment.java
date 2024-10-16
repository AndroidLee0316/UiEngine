package com.pasc.business.life;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pasc.business.life.event.GotoMainEvent;
import com.pasc.business.life.event.LocationEvent;
import com.pasc.business.life.net.LifePageBiz;
import com.pasc.business.life.resp.DataBoardHouseModel;
import com.pasc.business.life.resp.LocationModel;
import com.pasc.business.life.router.location.Callback;
import com.pasc.business.life.router.location.LocationJumper;
import com.pasc.business.life.router.location.PortionLocation;
import com.pasc.business.life.util.ViewUtils;
import com.pasc.business.workspace.BaseConfigurableFragment;
import com.pasc.lib.base.event.BaseEvent;
import com.pasc.lib.base.util.JsonUtils;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.widget.tangram.BgContentView;
import com.pasc.lib.widget.tangram.BgContyentModel;
import com.pasc.lib.widget.tangram.DividerHorizontalModle;
import com.pasc.lib.widget.tangram.DividerHorizontalView;
import com.pasc.lib.widget.tangram.FourTxtModel;
import com.pasc.lib.widget.tangram.FourTxtView;
import com.pasc.lib.widget.tangram.IconTwoTxtView;
import com.pasc.lib.widget.tangram.LifeIconTwoTxtModel;
import com.pasc.lib.widget.tangram.NearbyServiceCell;
import com.pasc.lib.widget.tangram.NearbyServiceView;
import com.pasc.lib.widget.tangram.IconTextCell;
import com.pasc.lib.widget.tangram.IconTextView;
import com.pasc.lib.widget.tangram.TangramEngineBuilder;
import com.pasc.lib.workspace.handler.CardLoadHandler;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.async.AsyncLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by chendaixi947 on 2019/5/6
 *
 * @version 1.0
 */
public class LifePageFragment extends BaseConfigurableFragment {
    public static final String CARD_HOUSE_SECURITY = "cardHouseSecurity";

    private static final String TAG = "LifePageFragment";
    private boolean mIsFirstCreate = true;
    private boolean mIsOnEvent;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private Card mLocationCard;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsFirstCreate = true;
        TextView mTitleView = view.findViewById(com.pasc.lib.life.R.id.ctv_title);
        ViewUtils.setPaddingToolbar(mTitleView, getContext());
        setViewPos();
    }

    @Override
    protected boolean isPullToRefreshEnable() {
        return false;
    }

    private void setViewPos() {
        if (mIsFirstCreate && mIsOnEvent) {
            BaseCell card = engine.findCellById("cardLifeBottum");
            engine.topPosition(card);
        }
        mIsOnEvent = false;
        mIsFirstCreate = false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoMainEvent event) {
        mIsOnEvent = true;
        if (!mIsFirstCreate) {
            BaseCell card = engine.findCellById("cardLifeBottum");
            engine.topPosition(card);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        StatusBarUtils.setStatusBarColor(getActivity(), true);
        doLoadCardById(CARD_HOUSE_SECURITY);
    }

    @Override
    protected String getConfigId() {
        return "pasc.smt.lifepage";
    }

    @Override
    protected void addCell(TangramEngineBuilder builder) {
        // 头部
        builder.registerCell("component-mapContent", NearbyServiceCell.class, NearbyServiceView.class);
        builder.registerCell("component-dividerHorizontal", DividerHorizontalModle.class, DividerHorizontalView.class);//阴影效果分割线
        builder.registerCell("component-iconTwoText", LifeIconTwoTxtModel.class, IconTwoTxtView.class);
        builder.registerCell("component-dataBoardHouse", FourTxtModel.class, FourTxtView.class);
        builder.registerCell("component-iconText", IconTextCell.class, IconTextView.class);
        //交通出行类grid item布局
        builder.registerCell("component-bgContent", BgContyentModel.class, BgContentView.class);
    }

    @Override
    protected void initCardLoadHandlers(HashMap<String, CardLoadHandler> cardLoadHandlers) {
        cardLoadHandlers.put("getHouseInfo", new CardLoadHandler() {
            @Override
            public void loadData(TangramEngine tangramEngine, Card card, AsyncLoader.LoadedCallback loadedCallback) {
                getHouseInfo(card, loadedCallback);
            }
        });
        cardLoadHandlers.put("getNearByInfo", new CardLoadHandler() {
            @Override
            public void loadData(TangramEngine tangramEngine, Card card, AsyncLoader.LoadedCallback loadedCallback) {
                //附近生活 定位修改
                mLocationCard = card;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessage(BaseEvent event) {
        if (event instanceof LocationEvent) {
            //定位消息
            if (mLocationCard != null) {
                setLocation(mLocationCard, new AsyncLoader.LoadedCallback() {
                    @Override
                    public void finish() {

                    }

                    @Override
                    public void fail(boolean loaded) {

                    }

                    @Override
                    public void finish(List<BaseCell> cells) {

                    }
                });
            }
        }
    }

    private void setLocation(final Card card, final AsyncLoader.LoadedCallback callback) {
        //获取位置信息
        LocationJumper.getILocation().getLocation(getActivity(), new Callback() {
            @Override
            public void getLocation(final PortionLocation portionLocation) {
                getActivity().runOnUiThread(() -> {
                    String s;
                    if (portionLocation != null) {
                        s = portionLocation.district + portionLocation.street;
                        LocationModel lm = null;
                        JSONObject extras = card.extras;
                        try {
                            JSONArray items = extras.getJSONArray("items");
                            if (items.length() > 0) {
                                String item = items.getString(0);
                                lm = JsonUtils.fromJson(item, LocationModel.class);
                                lm.street = portionLocation.street;
                                lm.location = s;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            replaceCardCells(card, lm, callback);
                        } catch (JSONException e) {
                            Log.e(TAG, "getLocation: JSONException -> " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    private void getHouseInfo(final Card card, @NonNull final AsyncLoader.LoadedCallback
            callback) {
        card.id = CARD_HOUSE_SECURITY;
        //获取住房保障数据
        Disposable houseSecurityDisposable = LifePageBiz.getHouseInfo()
                .subscribe(houseSecurityResp -> {
                    DataBoardHouseModel model = new DataBoardHouseModel();
                    model.data = houseSecurityResp;
                    PascLog.d(TAG, "getHouseInfo=" + houseSecurityResp.toString());
                    replaceCardCells(card, model, callback);

                }, throwable -> {
                });
        mDisposables.add(houseSecurityDisposable);
    }


    /**
     * 替换card的cells.
     *
     * @param card     要替换数据的card.
     * @param json     cell对应的json文件.
     * @param callback 新增回调.
     */
    private void replaceCardCells(Card card, String json, AsyncLoader.LoadedCallback
            callback) throws JSONException {
        JSONArray items = new JSONArray();
        JSONObject item = new JSONObject(json);
        items.put(item);
        replaceCardCellsByItemsWithCache(card, items, callback, null);
    }

    /**
     * 替换card的cells.
     *
     * @param card      要替换数据的card.
     * @param cellModel cell对应的Java对象.
     * @param callback  新增回调.
     */
    private void replaceCardCells(Card card, Object cellModel, AsyncLoader.LoadedCallback
            callback) throws JSONException {
        replaceCardCellsWithCache(card, cellModel, callback, null);
    }

    /**
     * 替换card的cells，先放缓存的cell.
     *
     * @param card     要替换cells的card.
     * @param model    新的model.
     * @param callback 回调.
     * @param cache    缓存的cell.
     */
    private void replaceCardCellsWithCache(Card card, Object model, @NonNull AsyncLoader
            .LoadedCallback callback, LinkedHashMap<String, BaseCell> cache) throws JSONException {
        String newsJson = JsonUtils.toJson(model);
        JSONArray items = new JSONArray();
        JSONObject item = new JSONObject(newsJson);
        items.put(item);
        replaceCardCellsByItemsWithCache(card, items, callback, cache);
    }

    private void replaceCardCellsByItemsWithCache(Card card, JSONArray items, @NonNull
            AsyncLoader.LoadedCallback callback, LinkedHashMap<String, BaseCell> cache) throws
            JSONException {
        LinkedHashMap<String, BaseCell> cellMap = new LinkedHashMap<String, BaseCell>();
        // 放缓存的
        if (cache != null) {
            Collection<BaseCell> cellsCache = cache.values();
            for (BaseCell cell : cellsCache) {
                cellMap.put(cell.stringType, cell);
            }
        }
        // 放旧的
        List<BaseCell> cellsOld = card.getCells();
        for (BaseCell cell : cellsOld) {
            cellMap.put(cell.stringType, cell);
        }
        // 放新的
        List<BaseCell> cells = engine.parseComponent(items);
        for (BaseCell cell : cells) {
            cellMap.put(cell.stringType, cell);
        }

        // 放同一个map是为了过滤type相同的
        Collection<BaseCell> newCellsCollection = cellMap.values();
        ArrayList<BaseCell> newCells = new ArrayList<>();
        newCells.addAll(newCellsCollection);

        // 替换cells
        engine.replaceCells(card, newCells);

        if (callback != null) {
            callback.finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return com.pasc.lib.life.R.layout.fragment_life_page;
    }

}
