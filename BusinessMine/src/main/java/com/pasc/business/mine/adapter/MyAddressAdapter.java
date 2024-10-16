package com.pasc.business.mine.adapter;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.business.mine.R;
import com.pasc.business.mine.params.AddressItem;

import java.util.List;

/**
 * Created by chentuxi
 * 我的地址
 */

public class MyAddressAdapter extends BaseQuickAdapter<AddressItem, BaseViewHolder> {

  public MyAddressAdapter(List<AddressItem> datas) {
    super(R.layout.mine_item_my_address, datas);
  }

  @Override
  protected void convert(BaseViewHolder helper, AddressItem item) {
    helper.setText(R.id.tv_name, getAddressName(item))
        .setText(R.id.tv_address, item.provinceName+" " + (item.cityName == null ? "" : item.cityName)
                +" " + (item.countyName == null ? "" : item.countyName) +" "+ item.detailAddress)
        .setText(R.id.tv_phone, getMobile(item));
    if ("1".equals(item.isDefault)) {
      helper.getView(R.id.tv_default).setVisibility(View.VISIBLE);
    } else {
      helper.getView(R.id.tv_default).setVisibility(View.GONE);
    }

    helper.addOnClickListener(R.id.iv_edit_address);
  }

  private String getMobile(AddressItem item) {
    return  new StringBuilder().append(item.addressMobile.substring(0,3))
            .append(" ").append(item.addressMobile.substring(3,7))
            .append(" ").append(item.addressMobile.substring(7,11)).toString();
  }

  private String getAddressName(AddressItem item) {
    if (item.addressName.length()>4){
      return item.addressName.substring(0,3)+"...";
    }
    return item.addressName;
  }
}
