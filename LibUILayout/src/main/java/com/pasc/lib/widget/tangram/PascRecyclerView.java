package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chenruihan410.
 */
public class PascRecyclerView extends RecyclerView implements NestedScrollingParent2 {

  public static final char RIGHT = 'r';
  public static final char LEFT = 'l';
  public static final char BOTTOM = 'b';
  public static final char TOP = 't';

  private float downX;    //按下时 的X坐标
  private float downY;    //按下时 的Y坐标

  private NestedScrollingParentHelper parentHelper;

  public PascRecyclerView(Context context) {
    super(context);
    init();
  }

  private void init() {
    parentHelper = new NestedScrollingParentHelper(this);
  }

  public PascRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PascRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent e) {
    float x = e.getX();
    float y = e.getY();
    switch (e.getAction()) {
      case MotionEvent.ACTION_DOWN:
        downX = x;
        downY = y;
        break;
      case MotionEvent.ACTION_MOVE:
        float dx = x - downX;
        float dy = y - downY;
        int orientation = getOrientation(dx, dy);
        switch (orientation) {
          case RIGHT:
            return false;
          case LEFT:
            return false;
          default:
            break;
        }
    }
    return super.onInterceptTouchEvent(e);
  }

  private int getOrientation(float dx, float dy) {
    if (Math.abs(dx) > Math.abs(dy)) {
      return dx > 0 ? RIGHT : LEFT;
    } else {
      return dy > 0 ? BOTTOM : TOP;
    }
  }

  @Override
  public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes,
      int type) {
    return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
  }

  @Override
  public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes,
      int type) {
    parentHelper.onNestedScrollAccepted(child, target, axes);
    super.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type);
  }

  @Override
  public void onStopNestedScroll(@NonNull View target, int type) {
    parentHelper.onStopNestedScroll(target, type);
    stopNestedScroll(type);
  }

  @Override
  public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
      int dyUnconsumed, int type) {
    final int oldScrollY = getScrollY();
    scrollBy(0, dyUnconsumed);
    final int myConsumed = getScrollY() - oldScrollY;
    final int myUnconsumed = dyUnconsumed - myConsumed;
    dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null, type);
  }

  @Override
  public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
      int type) {
    final RecyclerView rv = (RecyclerView) target;
    if ((dy < 0 && isRvScrolledToTop(rv)) || (dy > 0 && !isScrolledToBottom(this))) {
      scrollBy(0, dy);
      consumed[1] = dy;
      return;
    }
    super.dispatchNestedPreScroll(dx, dy, consumed, null, type);
  }

  private static boolean isScrolledToBottom(RecyclerView recyclerView) {
    return !recyclerView.canScrollVertically(1);
  }

  private static boolean isRvScrolledToTop(RecyclerView rv) {
    final LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
    return lm.findFirstVisibleItemPosition() == 0
        && lm.findViewByPosition(0).getTop() == 0;
  }

  @Override
  public int getNestedScrollAxes() {
    return parentHelper.getNestedScrollAxes();
  }
}

