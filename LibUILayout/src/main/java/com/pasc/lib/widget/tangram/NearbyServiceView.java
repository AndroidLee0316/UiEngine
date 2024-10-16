package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.List;

public class NearbyServiceView extends BaseCardView {

  public NearbyServiceView(@NonNull Context context) {
    super(context);
  }

  private List<IconTextNearbyServiceView> serviceItemViews;
  private TwoIconTwoTextView serviceEntryItemView;

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.component_nearby_service_view, this);

    IconTextNearbyServiceView itemView1 = findViewById(R.id.itemView1);
    IconTextNearbyServiceView itemView2 = findViewById(R.id.itemView2);
    IconTextNearbyServiceView itemView3 = findViewById(R.id.itemView3);
    IconTextNearbyServiceView itemView4 = findViewById(R.id.itemView4);
    serviceItemViews = new ArrayList<IconTextNearbyServiceView>();
    serviceItemViews.add(itemView1);
    serviceItemViews.add(itemView2);
    serviceItemViews.add(itemView3);
    serviceItemViews.add(itemView4);

    serviceEntryItemView = findViewById(R.id.serviceEntryItemView);
  }

  public List<IconTextNearbyServiceView> getServiceItemViews() {
    return serviceItemViews;
  }

  public TwoIconTwoTextView getServiceEntryItemView() {
    return serviceEntryItemView;
  }
}
