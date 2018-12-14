package com.wei.news.sdk.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置viewpage的切换动画,这里设置才能真正实现垂直滑动的viewpager
        setPageTransformer(true, new VerticalTransformer());
    }

    /**
     * 拦截touch事件
     *
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(swapEvent(ev));
        swapEvent(ev);
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapEvent(ev));
    }

    private MotionEvent swapEvent(MotionEvent event) {
        //获取宽高
        float width = getWidth();
        float height = getHeight();
        //将Y轴的移动距离转变成X轴的移动距离
        float swappedX = (event.getY() / height) * width;
        //将X轴的移动距离转变成Y轴的移动距离
        float swappedY = (event.getX() / width) * height;
        //重设event的位置
        event.setLocation(swappedX, swappedY);
        return event;
    }

}
