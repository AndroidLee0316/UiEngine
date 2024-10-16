package com.pasc.business.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pasc.business.mine.R;
import com.pasc.business.mine.adapter.MyAddressAdapter;
import com.pasc.business.mine.bean.AddressJsJson;
import com.pasc.business.mine.bean.JsAddressJson;
import com.pasc.business.mine.callback.OnResultListener;
import com.pasc.business.mine.callback.UserAddressChangeListener;
import com.pasc.business.mine.manager.UserAddressManager;
import com.pasc.business.mine.params.AddressItem;
import com.pasc.business.mine.resp.AddressIdResp;
import com.pasc.business.mine.resp.AddressListResp;
import com.pasc.business.mine.server.AddressServerType;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.widget.LinearLayoutManagerWrapper;
import com.pasc.lib.widget.dialognt.SelectReminderDialog;
import com.pasc.lib.widget.toolbar.PascToolbar;
import com.pasc.lib.widget.viewcontainer.ViewContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAddressActivity extends BaseStatusActivity implements ViewContainer.OnAddCallback,ViewContainer.OnReloadCallback{

    private static final int ADD_ADDRESS = 10001;
    private static final int UPDATE_ADDRESS = 10002;
    private PascToolbar toolbar;
    private RecyclerView rvAddress;
    private List<AddressItem> myAddressLists;
    private MyAddressAdapter adapter;
    private int mDeletePosition;
    private boolean deleteDefaultAddress;
    private Intent intent;
    private AddressJsJson addressJsJson;
    private String addressBtn;
    private boolean hasDefaultAddr;
    private ViewContainer viewContainer;
    private TextView tvNewAddAddress;
    private UserAddressChangeListener userAddressChangeListener = new UserAddressChangeListener() {
        @Override
        public void addAddress(JsAddressJson addressIdResp) {

        }

        @Override
        public void updateAddress() {

        }

        @Override
        public void deleteAddress() {
            hasDefault();
        }
    };

    private void hasDefault() {
        hasDefaultAddr=false;
        for (AddressItem item : myAddressLists) {
            if ("1".equals(item.isDefault)) {
                hasDefaultAddr = true;
               return;
            }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              addAddress();
        }
    };
    private void addAddress(){
        Bundle bundle = new Bundle();
        bundle.putString("addressTitle", "添加地址");
        bundle.putBoolean("hasDefaultAddr", hasDefaultAddr);
        if (addressBtn != null) {
            bundle.putString("addressBtn", addressBtn);
        }else {

        }
        actionStartForResult(EditAddressActivity.class, bundle, ADD_ADDRESS);
        HashMap<String, String> map = new HashMap<>();
        map.put("add_addr_btn", "点击添加地址按钮");
        EventUtils.onEvent( EventUtils.E_PERSONAL_INFO,
                EventUtils.L_ADDRESS, map);
    }
    @Override
    protected int layoutResId() {
        return R.layout.mine_activity_my_address;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestList(true);
    }

    @Override
    protected void onInit(@Nullable Bundle bundle) {
        toolbar = findViewById(R.id.ctv_title);
        rvAddress = findViewById(R.id.rv_address);

        tvNewAddAddress = toolbar.addRightTextButton("添加地址");
        tvNewAddAddress.setOnClickListener(onClickListener);

        tvNewAddAddress.setTextColor(getResources().getColor(R.color.add_addr_btn_color));
        tvNewAddAddress.setTextSize(getResources().getInteger(R.integer.toolbar_right_textsize));
        initContainer();
        initView();
        initListener();
        UserAddressManager.getInstance().addUserAddressChangeListener(userAddressChangeListener);
    }
    private void initContainer(){
        viewContainer=findViewById(R.id.view_container);
        viewContainer.setAddBtnMessage(R.string.mine_add_addr);
        viewContainer.setAddImage(R.drawable.mine_user_no_address);
        viewContainer.setAddMessage(R.string.mine_no_addr_hint);
        viewContainer.setAddBtnBg(R.drawable.mine_setting_addadress_bg);
        viewContainer.setAddBtnTxtColor(getResources().getColor(R.color.mine_add_addr_color));
        viewContainer.setOnAddCallback(this);
        viewContainer.setOnReloadCallback(this);
    }
    private void requestList(final boolean fromDefault) {
        if (!fromDefault){showLoading();}
        AddressServerType.getDefault().getAddressList(new OnResultListener<AddressListResp>() {
            @Override
            public void onSuccess(AddressListResp resp) {
                dismissLoading();
                List<AddressItem> addressLists = resp.list;
                if (addressLists != null && addressLists.size() > 0) {
                    myAddressLists.clear();
                    myAddressLists.addAll(addressLists);
                    hasDefault();
                    tvNewAddAddress.setVisibility(View.VISIBLE);
                    adapter.setNewData(myAddressLists);
                    viewContainer.showContent(R.id.content);
                } else {
                    viewContainer.showAddLayout();
                    tvNewAddAddress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(String code, String msg) {
                dismissLoading();
                ToastUtils.toastMsg(msg);
                viewContainer.showError();
            }
        });

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
                adapter.remove(mDeletePosition);
                    if (myAddressLists != null && myAddressLists.size() > 0) {
                        adapter.notifyDataSetChanged();
                    }else{
                        rvAddress.setVisibility(View.GONE);
                        tvNewAddAddress.setVisibility(View.GONE);
                    }
                UserAddressManager.getInstance().notifyUserAddressChangeForDelete();
            }

            @Override
            public void onFailed(String code, String msg) {
                dismissLoading();
                ToastUtils.toastMsg(msg);
            }
        });

    }

    private void setDefaultAddress(String id) {
        AddressServerType.getDefault().setDefaultAddress(id, new OnResultListener<VoidObject>() {
            @Override
            public void onSuccess(VoidObject voidObject) {
                requestList(true);
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.toastMsg(msg);
            }
        });


    }

    private void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AddressItem item = myAddressLists.get(position);

                if (view.getId() == R.id.iv_edit_address) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("addressPosition", position);
                    bundle.putString("addressTitle", "编辑联系地址");
                    bundle.putBoolean("hasDefaultAddr", hasDefaultAddr);
                    bundle.putParcelable("updateAddress", item);
                    if (addressBtn != null) {
                        bundle.putString("addressBtn", addressBtn);
                    }
                    MyAddressActivity.this.actionStartForResult(EditAddressActivity.class, bundle, UPDATE_ADDRESS);
                } else if (view.getId() == R.id.tv_delete_address) {
                    mDeletePosition = position;
                    if (addressJsJson != null) {
                        if (item.id.equals(addressJsJson.getRequestId()) && "1".equals(addressJsJson.getType())) {
                            ToastUtils.toastMsg("证件收件人地址不可删除");
                            return;
                        } else if (item.id.equals(addressJsJson.getSendId()) && "0".equals(addressJsJson.getType())) {
                            ToastUtils.toastMsg("证件发件人地址不可删除");
                            return;
                        }
                    }
                    sureDeleteAddress(item.id);
                    deleteDefaultAddress = "1".equals(item.isDefault);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (intent.getExtras() != null && intent.getExtras().getBoolean("needResult")) {
                    JsAddressJson jsAddressJson = new JsAddressJson();
                    AddressItem addressItem = myAddressLists.get(position);
                    jsAddressJson.setName(addressItem.addressName);
                    jsAddressJson.setMobilePhone(addressItem.addressMobile);
                    jsAddressJson.setDetailAddress(addressItem.detailAddress);
                    jsAddressJson.setAddressID(addressItem.id);
                    jsAddressJson.setCity(addressItem.cityName);
                    jsAddressJson.setCityID(addressItem.city);
                    jsAddressJson.setCode(addressItem.code);
                    jsAddressJson.setProvince(addressItem.provinceName);
                    jsAddressJson.setProvinceID(addressItem.province);
                    jsAddressJson.setDistrict(addressItem.countyName);
                    jsAddressJson.setDistrictID(addressItem.county);
                    jsAddressJson.setIsDefault(addressItem.isDefault);
                    jsAddressJson.setAddress(addressItem.provinceName + addressItem.cityName +
                            addressItem.countyName + addressItem.detailAddress);

                    intent.putExtra("jsAddressJson", jsAddressJson);
                    intent.putExtra("addressData", addressItem);
                    setResult(20, intent);
                    MyAddressActivity.this.finish();
                }
            }
        });
    }


    private void initView() {
        toolbar.enableUnderDivider(true);
        toolbar.addCloseImageButton()
                .setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                         onBackPressed();
                      }
                });
        intent = getIntent();
        addressJsJson = (AddressJsJson) intent.getSerializableExtra("addressJsJson");
        addressBtn = intent.getStringExtra("addressBtn");
        initRecyclerView();
    }

    private void initRecyclerView() {
        myAddressLists = new ArrayList<>();
        adapter = new MyAddressAdapter(myAddressLists);
        //添加空布局
        LinearLayoutManagerWrapper manager = new LinearLayoutManagerWrapper(this, LinearLayoutManagerWrapper.VERTICAL, false);
        rvAddress.setAdapter(adapter);
        rvAddress.setLayoutManager(manager);
        rvAddress.setNestedScrollingEnabled(false);
        adapter.bindToRecyclerView(rvAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ADDRESS&&data!=null) {
            if (addressJsJson == null) {
                AddressItem item = data.getParcelableExtra("newAddress");
                rvAddress.setVisibility(View.VISIBLE);
                tvNewAddAddress.setVisibility(View.VISIBLE);
                myAddressLists.add(item);
                adapter.notifyDataSetChanged();
                viewContainer.showContent(R.id.content);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AddressServerType.getDefault().cleanDispoables();
        UserAddressManager.getInstance().removeUserAddressChangeListener(userAddressChangeListener);
        //FixMemLeakUtil.fixLeak(this);
    }


    private void sureDeleteAddress(final String id) {
        final SelectReminderDialog dialog = new SelectReminderDialog(this);
        dialog.setCancelText(getResources().getString(R.string.btn_cancel));
        dialog.setConfirmText(getResources().getString(R.string.btn_del));
        dialog.setmTvContent("确定删除？");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnSelectedListener(new SelectReminderDialog.OnSelectedListener() {
            @Override
            public void onSelected() {
                deleteAddress(id);
            }

            @Override
            public void onCancel() {
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (intent.getExtras() != null && intent.getExtras().getBoolean("needResult")) {
            JsAddressJson jsAddressJson = new JsAddressJson();
            intent.putExtra("jsAddressJson", jsAddressJson);
            setResult(20, intent);
            MyAddressActivity.this.finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override public void add() {
        addAddress();
    }

    @Override public void reload() {
      requestList(false);
    }
}
