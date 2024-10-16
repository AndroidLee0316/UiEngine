//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.pasc.business.mine.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.pasc.business.mine.R;
import com.pasc.lib.base.util.ScreenUtils;

public class ChooseOptionDialog extends Dialog {
    public static final String TAG = "ChooseOptionDialog";
    private TextView tvFirst;
    private TextView tvSecond;
    private TextView tvCancel;

    public ChooseOptionDialog(Context context, int resId) {
        super(context, R.style.choose_option_dialog);
        this.setContentView(resId);
        this.tvFirst = (TextView)this.findViewById(R.id.tv_first);
        this.tvSecond = (TextView)this.findViewById(R.id.tv_second);
        this.tvCancel = (TextView)this.findViewById(R.id.tv_cancel);
        this.setLayoutParams(context, resId);
    }

    private void setLayoutParams(Context context, int resId) {
        int widthPixels = ScreenUtils.getScreenWidth();
        View contentView = LayoutInflater.from(context).inflate(resId, (ViewGroup)null);
        this.setContentView(contentView);
        this.tvFirst = (TextView)this.findViewById(R.id.tv_first);
        this.tvSecond = (TextView)this.findViewById(R.id.tv_second);
        this.tvCancel = (TextView)this.findViewById(R.id.tv_cancel);
        MarginLayoutParams params = (MarginLayoutParams)contentView.getLayoutParams();
        params.width = widthPixels ;
        contentView.setLayoutParams(params);
        this.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        this.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {

        tvFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSelectedListener != null) {
                    onSelectedListener.onFirst();
                }

                ChooseOptionDialog.this.dismiss();
            }
        });

        tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSelectedListener != null) {
                    onSelectedListener.onSecond();
                }

                ChooseOptionDialog.this.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseOptionDialog.this.dismiss();
            }
        });
    }

    public interface OnSelectedListener {
        void onFirst();

        void onSecond();

        void onCancel();
    }
}
