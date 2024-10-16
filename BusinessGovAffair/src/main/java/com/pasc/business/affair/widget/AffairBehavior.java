package com.pasc.business.affair.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import java.lang.reflect.Field;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/10
 */
public class AffairBehavior extends AppBarLayout.Behavior {
    private static final int TYPE_FLING = 1;
    private boolean isFlinging;
    private boolean shouldBlockNestedScroll;

    public AffairBehavior() {
    }

    public AffairBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        //        Logs.i("TestBehavior", "onInterceptTouchEvent:" + child.getTotalScrollRange());
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Object scroller = getSuperSuperField(this, "mScroller");
            if (scroller != null && scroller instanceof OverScroller) {
                OverScroller overScroller = (OverScroller) scroller;
                overScroller.abortAnimation();
            }
        }
        shouldBlockNestedScroll = false;
        if (isFlinging) {
            shouldBlockNestedScroll = true;
        }
        Log.e("AffairBehavior", "onInterceptTouchEvent = " + shouldBlockNestedScroll);
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    private Object getSuperSuperField(Object paramClass, String paramString) {
        Field field = null;
        Object object = null;
        try {
            field = paramClass.getClass().getSuperclass().getSuperclass().getDeclaredField(paramString);
            field.setAccessible(true);
            object = field.get(paramClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        Log.e("AffairBehavior", "onTouchEvent = " + ev.getAction());
        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        //注意看ViewCompat.TYPE_TOUCH Logs.i("TestBehavior", "onNestedPreScroll:" + child.getTotalScrollRange() + " ,dx:" + dx + " ,dy:" + dy + " ,type:" + type);
        // 返回1时，表示当前target处于非touch的滑动，
        // 该bug的引起是因为appbar在滑动时，CoordinatorLayout内的实现NestedScrollingChild2接口的滑动子类还未结束其自身的fling
        // 所以这里监听子类的非touch时的滑动，然后block掉滑动事件传递给AppBarLayout
        if (type == TYPE_FLING) {
            isFlinging = true;
        }
        Log.e("AffairBehavior", "onNestedPreScroll = " + shouldBlockNestedScroll + "  ==  isFlinging==   " + isFlinging);
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //        Logs.i("TestBehavior", "onNestedScroll: target:" + target.getClass() + " ," + child.getTotalScrollRange() + " ,dxConsumed:" + dxConsumed + " ,dyConsumed:" + dyConsumed + " " + ",type:" + type);

        Log.e("AffairBehavior", "onNestedScroll = " + shouldBlockNestedScroll);
        if (!shouldBlockNestedScroll) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        isFlinging = false;
        shouldBlockNestedScroll = false;
        Log.e("AffairBehavior", "onStopNestedScroll = " + shouldBlockNestedScroll);
    }

}