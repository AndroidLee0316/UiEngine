package com.pasc.business.feedback;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pasc.lib.base.util.StatusBarUtils;
import com.pasc.lib.widget.toolbar.PascToolbar;

import static com.pasc.business.feedback.FeedBackActivity.LAYOUT_CS;
import static com.pasc.business.feedback.FeedBackActivity.LAYOUT_SMT;
import static com.pasc.business.feedback.FeedBackActivity.LAYOUT_TEMPLATE;
import static com.pasc.business.feedback.FeedBackActivity.LAYOUT_TYPE;

/**
 * 反馈成功  by zc 2018年06月07日13:45:42
 */
public class FeedBackSuccessActivity extends BaseStatusActivity {

    TextView roundTextView;
    private PascToolbar mToolBar;
    private int layoutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarColor(this, true);
        layoutType =getIntent().getIntExtra(LAYOUT_TYPE,0);
        setLayout();
        roundTextView = findViewById(R.id.rtv_done);
        mToolBar = findViewById(R.id.ctv_topbar);
        ImageButton imageButton = mToolBar.addCloseImageButton();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        roundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (layoutType == LAYOUT_TEMPLATE) {
            imageButton.setVisibility(View.GONE);
            timer.start();
        }
    }

    private void setLayout() {
        if (layoutType == LAYOUT_TEMPLATE) {
            setContentView(R.layout.feedback_activity_feed_back_success);
        } else if (layoutType == LAYOUT_CS || layoutType == LAYOUT_SMT) {
            setContentView(R.layout.feedback_activity_feed_back_success_cs);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (layoutType == LAYOUT_TEMPLATE) {
            timer.cancel();
        }
    }
    /**
     * 倒数计时器
     */
    private CountDownTimer timer = new CountDownTimer(3500, 1000) {
        /**
         * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG,"count down "+millisUntilFinished);
            long secondLg=millisUntilFinished / 1000;
            if(secondLg==0){
                return;
            }
            roundTextView.setText(String.format(getResources().getString(R.string.business_mine_done), "" + secondLg));
        }


        /**
         * 倒计时完成时被调用
         */
        @Override
        public void onFinish() {
            Log.d(TAG,"count down finish");
            finish();
        }
    };


}
