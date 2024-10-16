package com.pasc.business.mine.activity;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pasc.business.mine.R;
import com.pasc.business.mine.adapter.CreditScoreItemAdapter;
import com.pasc.business.mine.bean.CreditInfo;
import com.pasc.business.mine.bean.CreditScoreItem;
import com.pasc.business.mine.net.CreditBiz;
import com.pasc.business.mine.util.EventConstants;
import com.pasc.business.mine.util.RouterTable;
import com.pasc.business.mine.widget.CreditScoreView;
import com.pasc.business.user.PascUserConfig;
import com.pasc.business.user.PascUserManager;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.activity.BaseActivity;
import com.pasc.lib.base.event.BaseEvent;
import com.pasc.lib.base.user.IUserInfo;
import com.pasc.lib.base.util.DateUtils;
import com.pasc.lib.base.util.StatusBarUtils;
import com.pasc.lib.net.resp.BaseRespObserver;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.widget.toolbar.PascToolbar;
import com.trello.rxlifecycle2.android.ActivityEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by huangtebian535 on 2018/06/07.
 */
public class MyCreditScoreActivity extends BaseActivity {

  public static final String INTENT_KEY_CREDIT_INFO = "intent_key_credit_info";
  PascToolbar ctvTitle;
  RecyclerView rv;
  LinearLayout llNotCertificationPage;
  private final List<CreditScoreItem> data = new ArrayList<>();
  private CreditScoreItemAdapter adapter;
  private android.view.View header;
  private CreditScoreView creditScoreView;
  private View topView;
  private TextView tvQueryDate;
  private final int COLOR_START = R.color.blue_38b0ff;
  private final int COLOR_END = R.color.blue_7fa2ff;
  private int height;

  public static void start(Context context) {
    Intent intent = new Intent(context, MyCreditScoreActivity.class);
    context.startActivity(intent);
  }

  private void initViews() {
    ctvTitle = findViewById(R.id.title_view);
    topView = findViewById(R.id.top_view);
    rv = findViewById(R.id.rv);
    llNotCertificationPage = findViewById(R.id.ll_not_certification_page);
    header = View.inflate(this, R.layout.mine_header_my_credit_score, null);
    creditScoreView = (CreditScoreView) header.findViewById(R.id.csv);

    tvQueryDate = (TextView) header.findViewById(R.id.tv_query_date);
  }

  @Override
  protected int layoutResId() {
    return R.layout.mine_activity_my_credit_score;
  }

  @Override
  protected void onInit(@Nullable Bundle bundle) {
    initViews();
    EventBus.getDefault().register(this);

    inflateMyCreditScoreData();
    rv.setLayoutManager(new LinearLayoutManager(this));
    adapter = new CreditScoreItemAdapter(data);
    adapter.addHeaderView(header);
    rv.setAdapter(adapter);
    setListener();
    ctvTitle.enableUnderDivider(false);
    ctvTitle.setBackgroundColor(getResources().getColor(COLOR_START));
    StatusBarUtils.setStatusBarBackgroundColor(MyCreditScoreActivity.this, topView,
        getResources().getColor(COLOR_START));
    //    ctvTitle.setOnLeftClickListener(new View.OnClickListener() {
    //      @Override public void onClick(View v) {
    //        finish();
    //      }
    //    });

    initData();
  }

  public int getScollYDistance() {
    LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
    int position = layoutManager.findFirstVisibleItemPosition();
    View firstVisiableChildView = layoutManager.findViewByPosition(position);
    int itemHeight = firstVisiableChildView.getHeight();
    return (position) * itemHeight - firstVisiableChildView.getTop();
  }

  private void setListener() {
    ctvTitle.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            ctvTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            height = creditScoreView.getHeight();
            rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
              @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrolledY = getScollYDistance();
                if (scrolledY <= 0) {
                  ctvTitle.setBackgroundColor(getResources().getColor(COLOR_START));
                } else if (scrolledY > 0 && scrolledY <= height) {
                  float scale = (float) dy / height;
                  int color = getTintColor(scale);
                  ctvTitle.setBackgroundColor(color);
                  StatusBarUtils.setStatusBarBackgroundColor(MyCreditScoreActivity.this, topView,
                      color);
                } else {
                  ctvTitle.setBackgroundColor(getResources().getColor(COLOR_END));
                  StatusBarUtils.setStatusBarBackgroundColor(MyCreditScoreActivity.this, topView,
                      getResources().getColor(COLOR_END));
                }
              }
            });
          }
        });
  }

  private @ColorInt int getTintColor(float scale) {
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    return (int) (argbEvaluator.evaluate(scale, getResources().getColor(COLOR_START),
        getResources().getColor(COLOR_END)));
  }

  private void initData() {
    showLoading();
    CreditBiz.queryCredit()
        .compose(this.<CreditInfo>bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(new BaseRespObserver<CreditInfo>() {
          @Override public void onError(int code, String msg) {
            super.onError(code, msg);
            //埋点
            dismissLoading();
            switch (code) {
              case 11100://用户未实名
                llNotCertificationPage.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
                break;
              case 201://暂无评分
                creditScoreView.showScore(-1, 850, "");
                Calendar calendar = Calendar.getInstance();
                //获取系统的日期
                //年
                int year = calendar.get(Calendar.YEAR);
                //月
                int month = calendar.get(Calendar.MONTH) + 1;
                //日
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                tvQueryDate.setText("查询时间：" + year + "-" + month + "-" + day);
                break;
            }
          }

          @Override public void onSuccess(CreditInfo info) {
            dismissLoading();
            llNotCertificationPage.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            if (info != null) {
              creditScoreView.showScore(Integer.parseInt(
                  TextUtils.isEmpty(info.credooScore) ? "0" : info.credooScore),
                  Integer.parseInt(TextUtils.isEmpty(info.credooScoreMax) ? "850"
                      : info.credooScoreMax), info.credooScoreDesc);
              tvQueryDate.setText("查询时间：" + DateUtils.dateFormat(
                  DateUtils.getTime(info.dataBuildTime,
                      DateUtils.DATE_AND_TIME_FORMAT)));

              //SPUtils.getInstance()
              //    .setParam(UserConstant.REPORT_SCORE_DESC,
              //        info.credooScoreDesc);
              //User user = UserManager.getInstance().getUser();
              //              user.report = info.credooScoreDesc;
              //UserRouterBiz.fetchUserInfoServices().syncUser(user);
              EventBus.getDefault().post(new BaseEvent(EventConstants.USER_MODIFY_USER));
              //埋点
            }
          }
        });
  }

  /**
   * 点击事件
   */
  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      int i = view.getId();
      if (i == R.id.rtv_goto_certification) {
        PascUserManager.getInstance().toCertification(PascUserConfig.CERTIFICATION_TYPE_ALL,null);
      } else if (i == R.id.rtv_cancel_certification) {
        finish();
      } else {
      }
    }
  };

  /**
   * 实名认证成功后关闭自身
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void onEventMsg(BaseEvent event) {
    if (EventConstants.USER_CERTIFICATE_SUCCEED.equals(event.getTag())) {
      finish();
    }
  }

  public static void launch(Context context, CreditInfo creditInfo) {
    Intent intent = new Intent(context, MyCreditScoreActivity.class);
    intent.putExtra(INTENT_KEY_CREDIT_INFO, creditInfo);
    context.startActivity(intent);
  }

  private void inflateMyCreditScoreData() {
    CreditScoreItem item = new CreditScoreItem(R.drawable.mine_ic_credit_portrait, "身份特征", "",
        "身份认证信息，包括但不限于求学、就业和各种行为留下与信用相关的个人记录，呈现出的相关身份特征；");
    this.data.add(item);
    CreditScoreItem item1 =
        new CreditScoreItem(R.drawable.mine_ic_constract, "履约能力", "", "考量您与信用相关的资产和负债信息，判断您的履约能力；");
    this.data.add(item1);
    CreditScoreItem item2 =
        new CreditScoreItem(R.drawable.mine_ic_medal, "失信风险", "", "您在过往的金融和非金融场景中留下的不诚信记录或违约记录；");
    this.data.add(item2);
    CreditScoreItem item3 =
        new CreditScoreItem(R.drawable.mine_ic_wallet, "消费偏好", "",
            "依据您的购物、缴费、转账等行为数据，呈现您与消费相关的信用特征；");
    this.data.add(item3);
    CreditScoreItem item4 =
        new CreditScoreItem(R.drawable.mine_ic_behaviour, "行为特征", "",
            "根据您上网、使用手机等信用相关的行为数据，反映其信用状况；");
    this.data.add(item4);
    CreditScoreItem item5 =
        new CreditScoreItem(R.drawable.mine_chat_msg, "社交信用", "", "根据您线上线下的社交数据，判断失信成本的高低；");
    this.data.add(item5);
    CreditScoreItem item6 =
        new CreditScoreItem(R.drawable.mine_ic_increase, "成长潜力", "", "基于上述六点，评估其未来信用的上升趋势和空间；");
    this.data.add(item6);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onLoginExitButton(BaseEvent event) {
    if (EventConstants.USER_CERTIFICATE_FINISH.equals(event.getTag())) {
      finish();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (llNotCertificationPage.isShown()) {
      //User user = UserRouterBiz.fetchUserInfoServices().getUser();
      //user.idPassed = "0";
      //user.sex = "0";
      //user.save();
      IUserInfo userInfo = AppProxy.getInstance().getUserManager().getUserInfo();
      userInfo.setIdPassed("0");
      userInfo.setSex("0");
      AppProxy.getInstance().getUserManager().updateUser(userInfo);
      EventBus.getDefault()
          .post(new BaseEvent(EventConstants.USER_CERTIFICATE_NOT));//直接返回了  通知其它页面刷新ui
    }
    EventBus.getDefault().unregister(this);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
  }
}

