package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.SimpleClickSupport;
import com.tmall.wireless.tangram.support.async.CardLoadSupport;

import org.json.JSONArray;

public class TangramEngineBuilder {

  TangramBuilder.InnerBuilder builder;
  private SimpleClickSupport clickSupport;
  private CardLoadSupport cardLoadSupport;
  private boolean autoLoadMore = true;
  private RecyclerView bindView;
  private Integer preLoadNumber;
  private JSONArray data = null;
  private int offsetLeft;
  private int offsetTop;
  private int offsetRight;
  private int offsetBottom;

  public TangramEngineBuilder(Context context) {
    // 初始化TangramBuilder
    builder = TangramBuilder.newInnerBuilder(context);
  }

  public <V extends View> TangramEngineBuilder registerCell(String type,
      @NonNull Class<? extends BaseCell> cellClz, @NonNull Class<V> viewClz) {
    builder.registerCell(type, cellClz, viewClz);
    return this;
  }

  public TangramEngine build() {
    // 构建引擎
    final TangramEngine engine = builder.build();

    // 添加点击支持
    if (clickSupport != null) {
      engine.addSimpleClickSupport(clickSupport);
    }

    if (cardLoadSupport != null) {
      engine.addCardLoadSupport(cardLoadSupport);
    }

    // 开启自动加载更多
    engine.enableAutoLoadMore(autoLoadMore);

    // 绑定到recyclerView
    if (bindView != null) {
      engine.bindView(bindView);

      // 绑定自动触发加载更多
      bindView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          engine.onScrolled();
        }
      });
    }

    // 设置偏移量
    engine.getLayoutManager().setFixOffset(offsetLeft, offsetTop, offsetRight, offsetBottom);

    // 设置预加载
    if (preLoadNumber != null) {
      engine.setPreLoadNumber(preLoadNumber);
    }

    // 设置数据
    if (data != null) {
      engine.setData(data);
    }

    return engine;
  }

  public TangramEngineBuilder setDefaultCells() {
    // 注册自定义视图
    builder.registerCell("component-image", ImageCell.class, ImageView.class);
    builder.registerCell("component-cardHeader", CardHeaderCell2.class,
        CardHeaderView2.class);  // 头部
    builder.registerCell("component-bannerTwoText", BannerTwoTextCell.class,
        BannerTwoTextView.class); // 横幅带两文本
    builder.registerCell("component-iconTwoText", IconTwoTextCell.class, IconTwoTextView.class);
    builder.registerCell("component-iconTwoTextSalt", IconTwoTextSaltCell.class,
        IconTwoTextSaltView.class);
    builder.registerCell("component-iconTwoTextScore", IconTwoTextScoreCell.class,
        IconTwoTextScoreView.class); // 文明分
    builder.registerCell("component-singleText", SingleTextCell.class, SingleTextView.class);
    builder.registerCell("component-iconText", IconTextCell.class, IconTextView.class);  // 图标文本
    builder.registerCell("component-iconTextNearbyService", IconTextNearbyServiceCell.class,
        IconTextNearbyServiceView.class); // 周边服务的图标文本
    builder.registerCell("component-dividerHorizontal", HorizontalDividerCell.class,
        HorizontalDividerView.class);  //水平分割线
    builder.registerCell("component-placeholder", PlaceholderCell.class,
        PlaceholderView.class);  //占位视图
    builder.registerCell("component-infoItem", InfoItemCell.class, InfoItemView.class);  //信息项
    builder.registerCell("component-infoItemNoImg", InfoItemNoImgCell.class,
        InfoItemNoImgView.class);  //无图信息项
    builder.registerCell("component-infoItemOneImg", InfoItemOneImgCell.class,
        InfoItemOneImgView.class);  //单图信息项
    builder.registerCell("component-infoItemThreeImg", InfoItemThreeImgCell.class,
        InfoItemThreeImgView.class);  //三图信息项
    builder.registerCell("component-marqueeText", MarqueeTextCell.class,
        MarqueeTextView.class);  //文本跑马灯
    builder.registerCell("component-address", AddressCell.class, AddressView.class);  // 地址组件
    builder.registerCell("component-ratioImage", RatioImageCell.class,
        RatioImageView.class);  // 比例图片
    builder.registerCell("component-roundedImage", RoundedImageCell.class,
        RoundedImageView.class);  // 圆形图片
    builder.registerCell("component-imgText", ImgTextCell.class, ImgTextView.class);  // 图片文本
    builder.registerCell("component-horizontalScroll", HorizontalScrollCell.class,
        HorizontalScrollView.class); // 横向滑动组件
    builder.registerCell("component-gridLayout", GridLayoutCell.class,
        GridLayoutView.class); // 图标文本网格组件
    builder.registerCell("component-twoIconText", TwoIconTextCell.class, TwoIconTextView.class);
    builder.registerCell("component-twoIconTwoText", TwoIconTwoTextCell.class,
        TwoIconTwoTextView.class);
    builder.registerCell("component-nearbyService", NearbyServiceCell.class,
        NearbyServiceView.class);
    builder.registerCell("component-menu", MenuCell.class, MenuView.class); // 周边服务的两行文本
    builder.registerCell("component-personalHeader", PersonalHeaderCell.class,
        PersonalHeaderView.class);
    builder.registerCell("component-personalHeader2", PersonalHeader2Cell.class,
        PersonalHeader2View.class);
    builder.registerCell("component-personalInfo", PersonalInfoCell.class, PersonalInfoView.class);
    builder.registerCell("component-card", CardCell.class, CardView.class);
    return this;
  }

  public TangramEngineBuilder setClickSupport(SimpleClickSupport clickSupport) {
    this.clickSupport = clickSupport;
    return this;
  }

  public TangramEngineBuilder setCardLoadSupport(CardLoadSupport cardLoadSupport) {
    this.cardLoadSupport = cardLoadSupport;
    return this;
  }

  public TangramEngineBuilder setAutoLoadMore(boolean autoLoadMore) {
    this.autoLoadMore = autoLoadMore;
    return this;
  }

  public TangramEngineBuilder setBindView(RecyclerView bindView) {
    this.bindView = bindView;
    return this;
  }

  public TangramEngineBuilder setFixOffset(int left, int top, int right, int bottom) {
    this.offsetLeft = left;
    this.offsetTop = top;
    this.offsetRight = right;
    this.offsetBottom = bottom;
    return this;
  }

  public TangramEngineBuilder setPreLoadNumber(int preLoadNumber) {
    this.preLoadNumber = preLoadNumber;
    return this;
  }

  public TangramEngineBuilder setData(JSONArray data) {
    this.data = data;
    return this;
  }
}
