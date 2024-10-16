package com.pasc.business.affair.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.business.affair.router.ARouterPath;
import com.pasc.lib.affair.R;
import com.pasc.lib.base.activity.BaseActivity;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/1
 */
@Route(path = ARouterPath.AFFAIR_WOEK_MAYORBOX)
public class MayorBoxActivity extends BaseActivity implements TextWatcher{
   /* private TextEditView name;
    private TextEditView box;
    private TextEditView phone;
    private TextEditView address;
    private TextEditView theem;
    private EditText content;
    private TextView tvSave;*/



    private void initView() {
        /*name = findViewById(R.id.name);
        box = findViewById(R.id.box);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        theem = findViewById(R.id.threem);
        content = findViewById(R.id.et_content);
        tvSave =findViewById(R.id.tv_save);
        tvSave.requestFocus();
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(name.getText())&&!TextUtils.isEmpty(box.getText())&&!TextUtils.isEmpty(phone.getText())&&!TextUtils.isEmpty(address.getText())&&!TextUtils.isEmpty(theem.getText())&&!TextUtils.isEmpty(content.getText().toString().trim())){
                    CommitDialog.showDialog(MayorBoxActivity.this, new OnDialogClick() {
                        @Override
                        public void onSure() {
                            finish();
                        }

                        @Override
                        public void onCalce() {

                        }
                    });
                }else {


                }

            }
        });*/

    }

    private void initData() {
        setTitle("市长信箱");
       /* name.setTitle("姓名").setHintText("请输入姓名").addTextWatcher(this);
        box.setTitle("电子邮件").setHintText("请输入你的电子邮件").addTextWatcher(this);
        phone.setTitle("联系电话").setHintText("请输入联系电话").addTextWatcher(this);
        address.setTitle("联系地址").setHintText("请输入联系地址").addTextWatcher(this);
        theem.setTitle("信件主题").setHintText("请输入信件主题").addTextWatcher(this);
        content.addTextChangedListener(this);*/
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
       /* if (!TextUtils.isEmpty(name.getText())&&!TextUtils.isEmpty(box.getText())&&!TextUtils.isEmpty(phone.getText())&&!TextUtils.isEmpty(address.getText())&&!TextUtils.isEmpty(theem.getText())&&!TextUtils.isEmpty(content.getText().toString().trim())){
            tvSave.setBackgroundResource(R.drawable.bg_common_title);
        }*/
    }

    @Override protected int layoutResId() {
        return R.layout.activity_mayor_box;
    }

    @Override protected void onInit(@Nullable Bundle bundle) {
       initView();
       initData();
    }
}
