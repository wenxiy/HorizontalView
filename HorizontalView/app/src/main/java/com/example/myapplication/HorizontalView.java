package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalView extends ViewGroup {
    private int lastInterceptX;
    private int lastInterceptY;
    private int lastX;
    private int lastY;
    int currentIndex = 0;
    int childwidth = 0;
    private Scroller scroller;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                int distance = getScrollX() - currentIndex * childwidth;
                if (Math.abs(distance) > childwidth / 2) {
                    if (distance > 0) {
                        currentIndex++;
                    } else {
                        currentIndex++;
                    }
                }
                smoothScrollTo(currentIndex * childwidth, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                scrollBy(-deltaX, 0);
                break;
        }
        lastX = x;
        lastY = y;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        scroller.startScroll(getScrollX(),getScrollY(),destX-getScrollY(),destY-getScrollY(),1000);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {//如果是平行滑动的就去让父View处理，返回true
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastInterceptX;
                int deltaY = y - lastInterceptY;
                if (Math.abs(deltaX) - Math.abs(deltaY) > 0) {
                    intercept = true;
                    break;
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        lastX = x;
        lastY = y;
        lastInterceptX = x;
        lastInterceptY = y;
        return intercept;
    }

    public HorizontalView(Context context) {
        super(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSide = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //设置子元素的宽与高
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(childWidth * getChildCount(), childHeight);//设置宽度
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(childWidth * getChildCount(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSide, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int childWidth = width;
                child.layout(left, 0, left + width, child.getMeasuredHeight());
                left += width;
            }
        }
    }


}
