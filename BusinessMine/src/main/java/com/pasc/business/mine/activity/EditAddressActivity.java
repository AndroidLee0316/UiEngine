package com.pasc.business.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pasc.business.mine.R;
import com.pasc.business.mine.bean.JsAddressJson;
import com.pasc.business.mine.callback.OnResultListener;
import com.pasc.business.mine.config.AddressManager;
import com.pasc.business.mine.manager.UserAddressManager;
import com.pasc.business.mine.params.AddressItem;
import com.pasc.business.mine.params.AddressParam;
import com.pasc.business.mine.params.AreaItem;
import com.pasc.business.mine.params.UpdateAddressParam;
import com.pasc.business.mine.resp.AddressIdResp;
import com.pasc.business.mine.resp.SecondAreaItem;
import com.pasc.business.mine.resp.ThirdAreaItem;
import com.pasc.business.mine.server.AddressServerType;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.business.mine.util.FixMemLeakUtil;
import com.pasc.lib.base.util.InputMethodUtils;
import com.pasc.lib.base.util.JsonUtils;
import com.pasc.lib.base.util.LegalityUtils;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.widget.ClearEditText;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.common.ConfirmDialogFragment;
import com.pasc.lib.widget.dialognt.SelectReminderDialog;
import com.pasc.lib.widget.pickerview.OptionsPickerView;
import com.pasc.lib.widget.toolbar.PascToolbar;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * chentuxi
 * 编辑地址
 */
public class EditAddressActivity extends BaseStatusActivity implements View.OnClickListener {
  public static String EVENT_LABEL = "添加地址";
  PascToolbar toolbar;
  ClearEditText etName;
  ClearEditText etPhone;
  TextView tvChooseAddress;
  ClearEditText etAddress;
  TextView tvSaveAddress;
  ImageView ivSwitch;
  TextView tvArea;
  LinearLayout llArea;
  LinearLayout llDefaultAddress;
  private AddressItem updateAddressItem;
  private Boolean switchStatus = false;
  CompositeDisposable disposables = new CompositeDisposable();
  OptionsPickerView pvOptions;
  List<AreaItem> options1Items = new ArrayList<>();
  List<List<SecondAreaItem>> options2Items = new ArrayList<>();
  List<List<List<ThirdAreaItem>>> options3Items = new ArrayList<>();
  String province, city, country;
  String provinceName = "", cityName = "", countryName = "";
  Intent intent;
  String addressBtn;
  int option1 = 9, option2 = 5, option3 = 0;
  private TextView tvPhone;
  private TextView tvAddress;
  private TextView tvName;
  private TextView tvDelAdress;
  private boolean hasDefaultAddr;
  private int position;
  private EditText etCode;

  @Override
  protected int layoutResId() {
    return R.layout.mine_activity_edit_address;
  }

  @Override
  protected void onInit(@Nullable Bundle bundle) {
    etName = findViewById(R.id.et_name);
    etPhone = findViewById(R.id.et_phone);
    toolbar = findViewById(R.id.ctv_title);
    tvChooseAddress = findViewById(R.id.tv_choose_address);
    etAddress = findViewById(R.id.et_address);
    tvPhone = findViewById(R.id.tv_phone);
    tvAddress = findViewById(R.id.tv_address_detail);
    tvName = findViewById(R.id.tv_name);
    ivSwitch = findViewById(R.id.iv_in_app_switch);
    tvArea = findViewById(R.id.tv_area);
    tvDelAdress = findViewById(R.id.tv_delete_address);
    llArea = findViewById(R.id.ll_area);
    llDefaultAddress = findViewById(R.id.ll_set_default_address);
    etCode = findViewById(R.id.et_zcode);

    ivSwitch.setOnClickListener(this);
    llArea.setOnClickListener(this);
    tvSaveAddress = toolbar.addRightTextButton("完成");
    tvSaveAddress.setOnClickListener(this);
    tvDelAdress.setOnClickListener(this);

    tvSaveAddress.setId(R.id.tv_save_address);
    tvSaveAddress.setTextColor(getResources().getColor(R.color.theme_color));
    tvSaveAddress.setAlpha(0.3f);
    tvSaveAddress.setTextSize(getResources().getInteger(R.integer.toolbar_right_textsize));

    initIntenData();
    getWheelData(false);
    initViewData();
  }

  private void changeLoginButton(int type) {
    if (type == 0) {   //登陆按钮可点击
      tvSaveAddress.setEnabled(true);
      tvSaveAddress.setAlpha(1.0f);
    } else if (type == 1) { //登陆按钮不可点击
      tvSaveAddress.setAlpha(0.3f);
      tvSaveAddress.setEnabled(false);
    }
  }

  private void changeStatus(EditText... editTexts) {
    if (editTexts[0].getText().toString().trim().length() > 0
        && editTexts[0].getText().toString().trim().length() <= 1
        || editTexts[0].getText().toString().trim().length() > 15) {
      editTexts[0].setTextColor(getResources().getColor(R.color.red_ff4d4f));
      tvName.setTextColor(getResources().getColor(R.color.red_ff4d4f));
    } else {
      editTexts[0].setTextColor(getResources().getColor(R.color.black_333333));
      tvName.setTextColor(getResources().getColor(R.color.black_333333));
    }

    if (editTexts[1].getText().toString().trim().length() > 0
        && editTexts[1].getText().toString().replaceAll(" ", "").trim().length() < 11) {
      editTexts[1].setTextColor(getResources().getColor(R.color.red_ff4d4f));
      tvPhone.setTextColor(getResources().getColor(R.color.red_ff4d4f));
    } else {
      editTexts[1].setTextColor(getResources().getColor(R.color.black_333333));
      tvPhone.setTextColor(getResources().getColor(R.color.black_333333));
    }

    if (editTexts[2].getText().toString().trim().length() > 0
        && editTexts[2].getText().toString().trim().length() < 2
        || editTexts[2].getText().toString().trim().length() > 50) {
      editTexts[2].setTextColor(getResources().getColor(R.color.red_ff4d4f));
      tvAddress.setTextColor(getResources().getColor(R.color.red_ff4d4f));
    } else {
      editTexts[2].setTextColor(getResources().getColor(R.color.black_333333));
      tvAddress.setTextColor(getResources().getColor(R.color.black_333333));
    }

    if (editTexts[0].getText().toString().trim().length() < 2 ||
        editTexts[0].getText().toString().trim().length() > 15 ||
        editTexts[1].getText().toString().trim().length() <= 0 ||
        editTexts[2].getText().toString().trim().length() <= 1 ||
        editTexts[2].getText().toString().trim().length() > 50 ||
        TextUtils.isEmpty(tvChooseAddress.getText().toString().trim())) {
      changeLoginButton(1);
      return;
    }

    changeLoginButton(0);
  }

  private void initViewData() {
    final TextWatcher textChangeListener = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        changeStatus(etName, etPhone, etAddress);
      }
    };
    etName.setFilters(new InputFilter[] { inputFilter });
    etName.addTextChangedListener(textChangeListener);
    etPhone.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count == 1) {
          if (s.length() == 4) {
            etPhone.setText(s.subSequence(0, s.length() - 1) + " "
                + s.subSequence(s.length() - 1, s.length()));
            etPhone.setSelection(5);
          }
          if (s.length() == 9) {
            etPhone.setText(s.subSequence(0, s.length() - 1) + " "
                + s.subSequence(s.length() - 1, s.length()));
            etPhone.setSelection(10);
          }
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        changeStatus(etName, etPhone, etAddress);
      }
    });
    etAddress.addTextChangedListener(textChangeListener);
    etAddress.setFilters(new InputFilter[] { inputFilter });

    toolbar.enableUnderDivider(true);
    toolbar.addCloseImageButton()
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            sureQuitEditAddress();
          }
        });
  }

  private InputFilter inputFilter = new InputFilter() {

    Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2,
        int i3) {
      Matcher matcher = pattern.matcher(charSequence);
      if (!matcher.find()) {
        return null;
      } else {
        return "";
      }
    }
  };

  private void changeTextViewStatus(TextView tvAddress, TextView tvPhone, TextView tvArea) {

  }

  private void getWheelData(final boolean show) {
    AddressServerType.getDefault().getAreaListFromCache(new OnResultListener<List<AreaItem>>() {
      @Override
      public void onSuccess(List<AreaItem> areaItems) {
        setAreaDate(areaItems, show);
      }

      @Override
      public void onFailed(String code, String msg) {

      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (pvOptions != null) {
      pvOptions.dismiss();
    }
  }

  private void setAreaDate(List<AreaItem> areaItems, final boolean show) {
    options1Items.clear();
    for (int i = 0; i < areaItems.size(); i++) {
      if ("0".equals(areaItems.get(i).parentid)) {
        options1Items.add(areaItems.get(i));
        if (areaItems.get(i).cityName != null && provinceName != null && areaItems.get(
            i).cityName.startsWith(provinceName)) {
          option1 = i;
        }
      }
    }
    options2Items.clear();
    for (int j = 0; j < options1Items.size(); j++) {
      List<SecondAreaItem> tempList = options1Items.get(j).children;
      options2Items.add(tempList);
      for (int k = 0; k < tempList.size(); k++) {
        if (tempList.get(k).cityName != null && cityName != null && tempList.get(
            k).cityName.startsWith(cityName)) {
          option2 = k;
        }
      }
    }
    options3Items.clear();
    for (int i = 0; i < options2Items.size(); i++) {
      List<List<ThirdAreaItem>> tempListList = new ArrayList<>();
      for (int j = 0; j < options2Items.get(i).size(); j++) {
        List<ThirdAreaItem> tempList = options2Items.get(i).get(j).children;
        if (options2Items.get(i).get(j).children.size() == 0) {
          tempList = new ArrayList<>();
          ThirdAreaItem temp = new ThirdAreaItem();
          temp.cityName = "";
          temp.parentid = options2Items.get(i).get(j).codeid;
          tempList.add(temp);
          if (temp.cityName != null && countryName != null && temp.cityName.startsWith(
              countryName)) {
            option3 = tempList.size() - 1;
          }
        }
        tempListList.add(tempList);
      }
      options3Items.add(tempListList);
    }
    if (options3Items == null || options3Items.size() == 0) {
      showLoading();
      AddressServerType.getDefault().getAreaListFromNet(new OnResultListener<List<AreaItem>>() {
        @Override
        public void onSuccess(List<AreaItem> areaItems) {
          setAreaDate(areaItems, show);
          dismissLoading();
        }

        @Override
        public void onFailed(String code, String msg) {
          dismissLoading();
        }
      });
    }
    if (show) {
      showOptionsPickerView();
      HashMap<String, String> map = new HashMap<>();
      map.put("area_select", "地址选择器被点击");
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
          EVENT_LABEL, map);
    }
  }

  private void addUserAddress() {
    showLoading();
    if (provinceName.contains("香港") || provinceName.contains("台湾") || provinceName.contains("澳门")) {
      city = "";
      country = "";
    }

    final AddressParam addressParam =
        new AddressParam(getTrimPhone(), etName.getText().toString().trim(),
            etAddress.getText().toString().trim(), province, city, country,
            switchStatus ? "1" : "0", etCode.getText().toString().trim());
    AddressServerType.getDefault()
        .addNewUserAddress(addressParam, new OnResultListener<AddressIdResp>() {
          @Override
          public void onSuccess(AddressIdResp addressIdResp) {
            dismissLoading();
            JsAddressJson jsAddressJson = new JsAddressJson();
            jsAddressJson.setName(addressParam.addressName);
            jsAddressJson.setMobilePhone(addressParam.addressMobile);
            jsAddressJson.setDetailAddress(addressParam.detailAddress);
            jsAddressJson.setCity(cityName);
            jsAddressJson.setCityID(addressParam.city);
            jsAddressJson.setProvince(provinceName);
            jsAddressJson.setProvinceID(addressParam.province);
            jsAddressJson.setDistrict(countryName);
            jsAddressJson.setDistrictID(addressParam.county);
            jsAddressJson.setIsDefault(addressParam.isDefault);
            jsAddressJson.setAddress(provinceName + cityName +
                countryName + addressParam.detailAddress);
            jsAddressJson.setSelectedAddress(provinceName + cityName +
                countryName);
            jsAddressJson.setAddressID(addressIdResp.userAddressId);
            jsAddressJson.setCode(addressParam.code);

            if (addressBtn != null) {
              intent.putExtra("jsAddressJson", jsAddressJson);
              setResult(30, intent);
            }
            UserAddressManager.getInstance().notifyUserAddressChangeForAdd(jsAddressJson);
            HashMap<String, String> map = new HashMap<>();
            map.put("add_addr", "成功");
            EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
                EVENT_LABEL, map);
            finish();
          }

          @Override
          public void onFailed(String code, String msg) {
            dismissLoading();
            ToastUtils.toastMsg(msg);
            HashMap<String, String> map = new HashMap<>();
            map.put("add_addr", "失败" + msg);
            EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
                EVENT_LABEL, map);
          }
        });
  }

  private void updateAddress() {
    showLoading();
    if (provinceName.contains("香港") || provinceName.contains("台湾") || provinceName.contains("澳门")) {
      city = "";
      country = "";
    }

    final UpdateAddressParam addressParam =
        new UpdateAddressParam(getTrimPhone(), etName.getText().toString().trim(),
            etAddress.getText().toString().trim(), province, city, country, updateAddressItem.id,
            switchStatus ? "1" : "0", etCode.getText().toString().trim());

    AddressServerType.getDefault().updateAddress(addressParam, new OnResultListener<VoidObject>() {
      @Override
      public void onSuccess(VoidObject voidObject) {
        dismissLoading();
        UserAddressManager.getInstance().notifyUserAddressChangeForModify();
        AddressParam param = new AddressParam(addressParam.addressMobile, addressParam.addressName,
            addressParam.detailAddress, province, city, country, switchStatus ? "1" : "0",
            addressParam.code);
        setModifyAddressForResult(param);
        HashMap<String, String> map = new HashMap<>();
        map.put("update_addr", "修改地址成功");
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
            EVENT_LABEL, map);
      }

      @Override
      public void onFailed(String code, String msg) {
        dismissLoading();
        ToastUtils.toastMsg(msg);
        HashMap<String, String> map = new HashMap<>();
        map.put("update_addr", "修改地址失败：" + msg);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
            EVENT_LABEL, map);
      }
    });
  }

  @NonNull
  private String getTrimPhone() {
    return etPhone.getText().toString().replaceAll(" ", "").trim();
  }

  private void setModifyAddressForResult(AddressParam addressParam) {
    if (addressBtn != null) {
      JsAddressJson jsAddressJson = new JsAddressJson();
      jsAddressJson.setName(addressParam.addressName);
      jsAddressJson.setMobilePhone(addressParam.addressMobile);
      jsAddressJson.setDetailAddress(addressParam.detailAddress);
      jsAddressJson.setCity(cityName);
      jsAddressJson.setCityID(addressParam.city);
      jsAddressJson.setProvince(provinceName);
      jsAddressJson.setProvinceID(addressParam.province);
      jsAddressJson.setDistrict(countryName);
      jsAddressJson.setDistrictID(addressParam.county);
      jsAddressJson.setIsDefault(addressParam.isDefault);
      jsAddressJson.setAddress(provinceName + cityName +
          countryName + addressParam.detailAddress);
      jsAddressJson.setSelectedAddress(provinceName + cityName +
          countryName);
      jsAddressJson.setAddressID(updateAddressItem.id);
      intent.putExtra("jsAddressJson", jsAddressJson);
      setResult(20, intent);
    }
    finish();
  }

  private void setDefaultAddress(final String id, final AddressParam addressParam) {
    AddressServerType.getDefault().setDefaultAddress(id, new OnResultListener<VoidObject>() {
      @Override
      public void onSuccess(VoidObject voidObject) {
        dismissLoading();
        setModifyAddressForResult(addressParam);
      }

      @Override
      public void onFailed(String code, String msg) {
        dismissLoading();
        setModifyAddressForResult(addressParam);
      }
    });
  }

  private void initIntenData() {
    intent = getIntent();
    if (intent != null) {
      String title = intent.getStringExtra("addressTitle");
      EVENT_LABEL = title;
      hasDefaultAddr = intent.getBooleanExtra("hasDefaultAddr", false);
      ivSwitch.setImageResource(
          hasDefaultAddr ? R.drawable.mine_ic_user_switch_off : R.drawable.mine_ic_user_switch_on);
      switchStatus = !hasDefaultAddr;
      position = intent.getIntExtra("addressPosition", -1);
      toolbar.setTitle(title);
      addressBtn = intent.getStringExtra("addressBtn");
      if (intent.getBooleanExtra("changeBtn", false)) {
        if (addressBtn != null) {
          tvSaveAddress.setText(addressBtn);
        }
      }
      updateAddressItem = intent.getParcelableExtra("updateAddress");
      if (updateAddressItem != null) {
        tvDelAdress.setVisibility(View.VISIBLE);
        changeLoginButton(0);
        etName.setText(updateAddressItem.addressName);
        etName.setSelection(updateAddressItem.addressName.length());
        provinceName = updateAddressItem.provinceName;
        cityName = updateAddressItem.cityName == null ? "" : updateAddressItem.cityName;
        countryName = updateAddressItem.countyName == null ? "" : updateAddressItem.countyName;
        tvChooseAddress.setText(provinceName + " " + cityName + " " + countryName);
        etPhone.setText(new StringBuilder().append(updateAddressItem.addressMobile.substring(0, 3))
            .append(" ").append(updateAddressItem.addressMobile.substring(3, 7))
            .append(" ").append(updateAddressItem.addressMobile.substring(7, 11)).toString());
        etAddress.setText(updateAddressItem.detailAddress);
        if (hasDefaultAddr) {
          ivSwitch.setImageResource(("1".equals(updateAddressItem.isDefault))
              ? R.drawable.mine_ic_user_switch_on : R.drawable.mine_ic_user_switch_off);
          switchStatus = "1".equals(updateAddressItem.isDefault);
        }
        if (addressBtn != null) {
          tvSaveAddress.setText(addressBtn);
        }
        etCode.setText(TextUtils.isEmpty(updateAddressItem.code) ? "" : updateAddressItem.code);
      } else {
        provinceName = AddressManager.getInstance().getProvinceName();
        cityName = AddressManager.getInstance().getCityName();
        countryName = AddressManager.getInstance().getCountryName();
      }
    }
  }

  private void showOptionsPickerView() {
    if (pvOptions != null && pvOptions.isShowing()) {
      return;
    }
    //条件选择器
    pvOptions = new OptionsPickerView.Builder(EditAddressActivity.this,
        new OptionsPickerView.OnOptionsSelectListener() {
          @Override
          public void onOptionsSelect(int options1, int options2, int options3, View v) {

            //返回的分别是三个级别的选中位置
            province = options1Items.get(options1).codeid;
            if (options1Items.size() > options1) {
              provinceName = options1Items.get(options1).cityName;
            }
            option1 = options1;
            if (options2Items.get(options1) != null) {
              if (options2Items.get(options1).size() > options2) {
                city = options2Items.get(options1).get(options2).codeid;
                cityName = options2Items.get(options1).get(options2).cityName;
                option2 = options2;
                if (options3Items.get(options1).get(options2).size() > options3) {
                  country = options3Items.get(options1).get(options2).get(options3).codeid;
                  countryName = options3Items.get(options1).get(options2).get(options3).cityName;
                  option3 = options3;
                }
              } else {
                option2 = 0;
                option3 = 0;
                cityName = "";
                countryName = "";
              }
            }
            tvChooseAddress.setText(provinceName + " " + cityName + " " + countryName);
            changeStatus(etName, etPhone, etAddress);
          }
        }).setSubmitText("确定")//确定按钮文字
        .setCancelText("取消")//取消按钮文字
        .setTitleText("请选择地址")//标题
        .setSubCalSize(17)//确定和取消文字大小
        .setTitleSize(17)//标题文字大小
        .setTitleColor(0xff333333)//标题文字颜色
        .setSubmitColor(getResources().getColor(R.color.theme_color))//确定按钮文字颜色
        .setCancelColor(getResources().getColor(R.color.theme_color))//取消按钮文字颜色
        .setTitleBgColor(0xffffffff)//标题背景颜色 Night mode
        .setBgColor(0xffffffff)//滚轮背景颜色 Night mode
        .setContentTextSize(17)//滚轮文字大小
        .setDividerColor(getResources().getColor(R.color.theme_color))
        .setContentTextSize(17)//滚轮文字大小
        .setDividerColor(getResources().getColor(R.color.theme_color))
        .setCyclic(false, false, false)//循环与否

        .setOutSideCancelable(false)//点击外部dismiss default true
        .build();

    if (options3Items != null && options3Items.size() > 0) {    //根据三级菜单判断有数据就展示选择器
      pvOptions.setPicker(options1Items, options2Items, options3Items);
      pvOptions.setSelectOptions(option1, option2, option3);  //设置默认选中项
      pvOptions.show();
    }
  }

  @Override
  public void onClick(View v) {
    int viewId = v.getId();
    if (viewId == R.id.iv_in_app_switch) {
      switchStatus = !switchStatus;
      ivSwitch.setImageResource(
          switchStatus ? R.drawable.mine_ic_user_switch_on : R.drawable.mine_ic_user_switch_off);
      HashMap<String, String> map = new HashMap<>();
      map.put("set_default_addr", "" + switchStatus);
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
          EVENT_LABEL, map);
    } else if (viewId == R.id.ll_area) {
      InputMethodUtils.hideInputMethod(EditAddressActivity.this);
      getWheelData(true);
      //showOptionsPickerView();
    } else if (viewId == R.id.tv_save_address) {
      String name = etName.getText().toString().trim();
      String phone = getTrimPhone();
      String detailsAddress = etAddress.getText().toString().trim();
      if (TextUtils.isEmpty(name)) {
        ToastUtils.toastMsg("请填写姓名");
        return;
      }

      if (name.length() < 2 || name.length() > 15) {
        ToastUtils.toastMsg("请填写2-15个字的姓名");
        return;
      }

      if (TextUtils.isEmpty(phone)) {
        ToastUtils.toastMsg("请填写手机号码");
        return;
      }

      if (!LegalityUtils.isChinaPhoneLegal(phone)) {
        ToastUtils.toastMsg("请填写正确的手机号码");
        return;
      }

      if (TextUtils.isEmpty(tvChooseAddress.getText().toString().trim())) {
        ToastUtils.toastMsg("请选择地址");
        return;
      }

      if (TextUtils.isEmpty(detailsAddress) || detailsAddress.toString().trim().length() < 2 ||
          detailsAddress.toString().trim().length() > 50) {
        ToastUtils.toastMsg("详细地址需2-50个字符");
        return;
      }

      if (updateAddressItem == null) {
        addUserAddress();
        HashMap<String, String> map = new HashMap<>();
        map.put("add_addr_btn", "添加地址被点击");
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
            EVENT_LABEL, map);
      } else {
        updateAddress();
        HashMap<String, String> map = new HashMap<>();
        map.put("update_addr_btn", "更新地址被点击");
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
            EVENT_LABEL, map);
      }
    } else if (viewId == R.id.tv_delete_address) {
      HashMap<String, String> map = new HashMap<>();
      map.put("del_addr_btn", "删除地址被点击");
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
          EVENT_LABEL, map);
      sureDeleteAddress(updateAddressItem.id);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    AddressServerType.getDefault().cleanDispoables();
    FixMemLeakUtil.fixLeak(this);
  }

  /**
   * 删除
   */
  private void deleteAddress(String id) {
    showLoading();
    AddressServerType.getDefault().deleteAddress(id, new OnResultListener<VoidObject>() {
      @Override
      public void onSuccess(VoidObject voidObject) {
        dismissLoading();
        UserAddressManager.getInstance().notifyUserAddressChangeForDelete();
        setResult(20);
        HashMap<String, String> map = new HashMap<>();
        map.put("del_addr", "成功");
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
            EVENT_LABEL, map);
        finish();
        //ToastUtils.toastMsg("删除成功");
      }

      @Override
      public void onFailed(String code, String msg) {
        dismissLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("del_addr", "失败" + msg);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
            EVENT_LABEL, map);
        ToastUtils.toastMsg(msg);
      }
    });
  }

  private void sureDeleteAddress(final String id) {
    final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
        .setTitle("确定要删除该地址吗？")
        .setConfirmText(getString(R.string.btn_del))
        .setCloseText(getString(R.string.btn_cancel))
        .setConfirmTextColor(getResources().getColor(R.color.mine_dialog_confirm_color))
        .setCloseTextColor(getResources().getColor(R.color.mine_dialog_cancel_color))
        .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
          @Override
          public void onConfirm(ConfirmDialogFragment dialogFragment) {
            deleteAddress(id);
            HashMap<String, String> map = new HashMap<>();
            map.put("del_addr_dialog", "确认删除地址");
            EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
                EVENT_LABEL, map);
          }
        }).setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
          @Override
          public void onClose(ConfirmDialogFragment dialogFragment) {
            dialogFragment.dismiss();
            HashMap<String, String> map = new HashMap<>();
            map.put("del_addr_dialog", "取消删除地址");
            EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
                EVENT_LABEL, map);
          }
        })
        .build();
    commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");
  }

  private void sureQuitEditAddress() {

    final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
        .setTitle(getString(R.string.mine_discard_edition))
        .setConfirmText(getString(R.string.mine_addr_discard))
        .setCloseText(getString(R.string.mine_continue_edit))
        .setConfirmTextColor(getResources().getColor(R.color.mine_dialog_confirm_color))
        .setCloseTextColor(getResources().getColor(R.color.mine_dialog_cancel_color))
        .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
          @Override
          public void onConfirm(ConfirmDialogFragment dialogFragment) {
            finish();
            HashMap<String, String> map = new HashMap<>();
            map.put("edit_addr_dialog", "确认取消编辑地址");
            EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
                EVENT_LABEL, map);
          }
        }).setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
          @Override
          public void onClose(ConfirmDialogFragment dialogFragment) {
            dialogFragment.dismiss();
            HashMap<String, String> map = new HashMap<>();
            map.put("edit_addr_dialog", "不取消编辑地址");
            EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
                EVENT_LABEL, map);
          }
        })
        .build();
    commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");
  }
}

