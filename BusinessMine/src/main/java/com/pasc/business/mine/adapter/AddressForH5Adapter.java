package com.pasc.business.mine.adapter;

import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.business.mine.R;
import com.pasc.business.mine.bean.AddressJsJson;
import com.pasc.business.mine.params.AddressItem;

import java.util.List;


public class AddressForH5Adapter extends BaseQuickAdapter<AddressItem, BaseViewHolder> {
    AddressJsJson addressJsJson;
    public AddressForH5Adapter(List<AddressItem> datas, AddressJsJson addressJsJson) {
        super(R.layout.mine_user_item_address_h5, datas);
        this.addressJsJson = addressJsJson;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressItem item) {
        helper.setText(R.id.tv_name, item.addressName)
                .setText(R.id.tv_address, item.provinceName + item.cityName + item.countyName + item.detailAddress)
                .setText(R.id.tv_phone, item.addressMobile);
        if ("1".equals(item.isDefault)) {
            helper.getView(R.id.tv_default).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_default).setVisibility(View.GONE);
        }
        if("0".equals(addressJsJson.getType())){
            if (item.id.equals(addressJsJson.getRequestId())) {
                helper.getView(R.id.iv_default_address).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.iv_default_address).setVisibility(View.GONE);
            }
        }else if("1".equals(addressJsJson.getType())) {
            if(item.id.equals(addressJsJson.getSendId())){
                helper.getView(R.id.iv_default_address).setVisibility(View.VISIBLE);
            }else{
                helper.getView(R.id.iv_default_address).setVisibility(View.GONE);
            }
        }
        int position = getData().indexOf(item); //当前加载的position
        helper.setVisible(R.id.v_divider, position != getData().size() - 1);

    }
}