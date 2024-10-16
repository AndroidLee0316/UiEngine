package com.pasc.business.affair.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.business.affair.db.SearchInfo;
import com.pasc.business.affair.net.SecondPageBiz;
import com.pasc.business.affair.resp.SearchAffairInfoResp;
import com.pasc.business.affair.router.ARouterPath;
import com.pasc.business.affair.router.ARouterUtil;
import com.pasc.lib.affair.R;
import com.pasc.lib.base.activity.BaseActivity;
import com.pasc.lib.widget.ClearEditText;
/*import com.pingan.cs.base.BaseActivity;
import com.pingan.cs.utlis.ARouterPath;
import com.pingan.cs.utlis.ARouterUtil;*/

import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/1
 */
@Route(path = ARouterPath.MAIN_SEARCH_AFFAIR)
public class AffairSearchActivity extends BaseActivity implements View.OnClickListener {
    public CompositeDisposable disposables = new CompositeDisposable();
    private ClearEditText editText;
    private RecyclerView recyclerView;
    private LinearLayout llEmpty;
    private ImageView ivIcon;
    private TextView tvInfo;
    private MyAdapter adapter;
    private String searchKey;

    private List<SearchInfo> datas = new ArrayList<>();
    @Override protected int layoutResId() {
        return R.layout.activity_search;
    }

    @Override protected void onInit(@Nullable Bundle bundle) {
      initView();
      initData();
    }

    protected void initView() {
        findViewById(R.id.tv_cacle).setOnClickListener(this);
        editText = findViewById(R.id.et_name);
        recyclerView = findViewById(R.id.recyclerview);
        llEmpty = findViewById(R.id.ll_empty);
        ivIcon = findViewById(R.id.iv_icon);
        tvInfo = findViewById(R.id.tv_info);
        adapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    protected void initData() {
        //        datas.add(new SearchInfoBean("行政审批事项", SearchInfoBean.TYPE_TEXT));
        //        datas.add(new SearchInfoBean("事业单位变更登记", "常熟市工商管理局", "res://ic_more_bybz"));
        //        datas.add(new SearchInfoBean("事业单位注销登记", "常熟市事业单位管理登记局", "res://ic_more_wyks"));
        //        datas.add(new SearchInfoBean("废钢船登记", "常熟市海事局", "res://ic_more_sjjsq"));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tag = editable.toString().trim();
                if (tag.length() > 0) {
                    searchKey = tag;
                    datas.clear();
                    adapter.notifyDataSetChanged();
                    llEmpty.setVisibility(View.GONE);
                    //                    llEmpty.setVisibility(View.VISIBLE);
                } else {
                    llEmpty.setVisibility(View.GONE);
                }

            }
        });
        adapter.notifyDataSetChanged();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //TODO:你自己的业务逻辑
                    searchKey = editText.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchKey)) {
                        getSearchInfo(searchKey);
                    }

                    return true;
                }
                return false;
            }
        });

    }

    private void getSearchInfo(String query) {
        Disposable houseSecurityDisposable = SecondPageBiz.getSearchAffairInfo(query)
                .subscribe(new Consumer<SearchAffairInfoResp>() {
                    @Override
                    public void accept(SearchAffairInfoResp infoResp)
                            throws Exception {
                        if (infoResp.list != null && infoResp.list.size() > 0) {
                            llEmpty.setVisibility(View.GONE);
                            datas.clear();
                            datas.addAll(infoResp.list);
                            SearchInfo info = new SearchInfo();
                            info.name = getResources().getString(R.string.main_search_hit);
                            info.typeitem = SearchAffairInfoResp.TYPE_TEXT;
                            datas.add(0, info);
                            adapter.notifyDataSetChanged();

                        } else {
                            datas.clear();

                            adapter.notifyDataSetChanged();
                            llEmpty.setVisibility(View.VISIBLE);
                            tvInfo.setText(getResources().getString(R.string.main_search_no_result));
                        }


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        datas.clear();
                        adapter.notifyDataSetChanged();
                        tvInfo.setText(getResources().getString(R.string.main_search_no_result));
                        llEmpty.setVisibility(View.VISIBLE);
                    }
                });
        disposables.add(houseSecurityDisposable);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == SearchAffairInfoResp.TYPE_ICON_TWOTEXT) {
                View view = LayoutInflater.from(AffairSearchActivity.this).inflate(R.layout.item_search, parent, false);
                MyHolder holder = new MyHolder(view);
                return holder;
            } else {
                View view = LayoutInflater.from(AffairSearchActivity.this).inflate(R.layout.item_search_text, parent, false);
                HeaderHolder holder = new HeaderHolder(view);
                return holder;
            }


        }

        /**
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final SearchInfo bean = datas.get(position);
            int type = getItemViewType(position);
            if (type == SearchAffairInfoResp.TYPE_TEXT) {
                MyHolder myHolder = (MyHolder) holder;
                int index = 0;
                if (!TextUtils.isEmpty(bean.title)) {
                    index = bean.title.indexOf(searchKey);
                    SpannableString spannableString = new SpannableString(bean.title);
                    if (bean.title.contains(searchKey)) {
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff7878")), index, index + searchKey.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    myHolder.title.setText(spannableString);
                } else {
                    myHolder.title.setText("");
                }
                myHolder.info.setText(AffairLobbyUtil.setAffaitName(bean.type + ""));
                myHolder.icon.setVisibility(View.GONE);
                myHolder.view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ARouterUtil.startH5(AffairSearchActivity.this,bean.h5LinkURL);
                    }
                });
            } else {
                ((HeaderHolder) holder).title.setText(bean.name);

            }

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }


        @Override
        public int getItemViewType(int position) {
            return datas.get(position).typeitem;
        }


        class MyHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView title;
            TextView info;
            View view;

            public MyHolder(View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.img_icon);
                title = itemView.findViewById(R.id.title);
                info = itemView.findViewById(R.id.info);
                view = itemView;
            }
        }

        class HeaderHolder extends RecyclerView.ViewHolder {
            TextView title;

            public HeaderHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);

            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
