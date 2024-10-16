package com.pasc.business.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;
import com.pasc.business.mine.R;
import com.pasc.business.mine.net.MineBiz;
import com.pasc.business.mine.util.EventConstants;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.event.BaseEvent;
import com.pasc.lib.base.user.IUserInfo;
import com.pasc.lib.base.util.InputMethodUtils;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.net.resp.BaseRespObserver;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.widget.ClearEditText;
import com.pasc.lib.widget.toolbar.PascToolbar;
import com.trello.rxlifecycle2.android.ActivityEvent;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by zhangxu678 on 2018/11/28.
 */
public class NicknameActivity extends BaseStatusActivity {
  PascToolbar pascToolbar;
  ClearEditText editText;
  TextView rightTextView;

  @Override protected int layoutResId() {
    return R.layout.mine_activity_nickname;
  }

  @Override protected void onInit(@Nullable Bundle bundle) {
    initViews();
    setListeners();
  }

  private void initViews() {
    pascToolbar = findViewById(R.id.common_title);
    editText = findViewById(R.id.tv_nickname);
    pascToolbar.addCloseImageButton().setOnClickListener(view -> finish());
    rightTextView = pascToolbar.addRightTextButton(getString(R.string.mine_finish));
    rightTextView.setTextColor(getResources().getColor(R.color.theme_color));
    rightTextView.setAlpha(0.3f);
    editText.setText(generateNickname());
    editText.setSelection(editText.getText().length());
    editText.setFocusable(true);
    editText.setFocusableInTouchMode(true);
    editText.requestFocus();
    InputMethodUtils.showKeyboard(editText);
  }

  private void setListeners() {
    editText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void afterTextChanged(Editable editable) {
        String str = editable.toString().trim();
        if (str.length() >= 2 && str.length() <= 15) {
          rightTextView.setEnabled(true);
          rightTextView.setTextColor(getResources().getColor(R.color.theme_color));
          rightTextView.setAlpha(1.0f);
        } else {
          rightTextView.setEnabled(false);
          rightTextView.setTextColor(getResources().getColor(R.color.theme_color));
          rightTextView.setAlpha(0.3f);
        }
        if(str.length()>15){
          editable.delete(15,str.length());
        }
      }
    });
    rightTextView.setOnClickListener(view -> modifyName(editText.getText().toString().trim()));
  }

  private void modifyName(final String name) {
    showLoading();
    String sexStr = AppProxy.getInstance().getUserManager().getUserInfo().getSex();
    MineBiz.modifyUserMsg(AppProxy.getInstance().getUserManager().getToken(), name,
        TextUtils.isEmpty(sexStr) ? 0 : Integer.parseInt(sexStr))
        .compose(this.<VoidObject>bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(new BaseRespObserver<VoidObject>() {
          @Override public void onError(int code, String msg) {
            ToastUtils.toastMsg(msg);
            dismissLoading();
            HashMap<String, String> map = new HashMap<>();
            map.put("set_nickname", "修改昵称失败："+msg);
            EventUtils.onEvent( EventUtils.E_PERSONAL_INFO,
                    EventUtils.L_USER_DATA, map);
          }

          @Override public void onSuccess(VoidObject voidObject) {
            super.onSuccess(voidObject);
            //ToastUtils.toastMsg("修改成功");
            IUserInfo userInfo = AppProxy.getInstance().getUserManager().getUserInfo();
            userInfo.setNickName(name);
            userInfo.setNickNameStatus("1");
            AppProxy.getInstance().getUserManager().updateUser(userInfo);
            EventBus.getDefault().post(new BaseEvent(EventConstants.USER_MODIFY_USER));
            dismissLoading();

            HashMap<String, String> map = new HashMap<>();
            map.put("set_nickname", "修改昵称成功");
            EventUtils.onEvent( EventUtils.E_PERSONAL_INFO,
                    EventUtils.L_USER_DATA, map);
            finish();
          }
        });
  }
  private String generateNickname(){
    boolean isChanged="1".equals(AppProxy.getInstance().getUserManager().getUserInfo().getNickNameStatus());
    IUserInfo userInfo=AppProxy.getInstance().getUserManager().getUserInfo();
    if(isChanged){
      return userInfo.getNickName();
    }
    if(AppProxy.getInstance().getUserManager().isCertified()){
      return maskRealName(userInfo.getUserName());
    }
    return userInfo.getNickName();
  }
  private String maskRealName(String username){
    String maskName="";
    if (!TextUtils.isEmpty(username)) {
      maskName =
          username.length() > 2 ? username.substring(0, 1) + "*" + username.substring(
              username.length() - 1, username.length())
              : "*" + username.substring(username.length() - 1, username.length());
    }
    return maskName;
  }
}
