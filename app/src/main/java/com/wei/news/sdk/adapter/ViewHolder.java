package com.wei.news.sdk.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Listview 通用ViewHolder
 */
public class ViewHolder {
    private View mConvertView;
    //用来存布局中的各个组件，以键值对形式
    private HashMap<Integer,View> mViews = new HashMap<>();
    //ViewHolder构造函数，只有当convertView为空的时候才创建
    public ViewHolder(Context context,View convertView, ViewGroup parent, int layouId) {
        convertView = LayoutInflater.from(context).inflate(layouId,parent,false);
        convertView.setTag(this);       //将其setTag()
        mConvertView = convertView;
    }
    //返回一个ViewHolder对象
    public static ViewHolder getHolder(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context,convertView,parent,layoutId);
        }else {
            return (ViewHolder) convertView.getTag();
        }
    }
    //返回一个View的子类对象，因为不确定用户布局有什么组件，相当于findViewById
    //这里返回一个泛型，也可以返回一个View或Object
    public <T extends View>T getView(int resId) {
        View view = mViews.get(resId);  //从集合中取出这个组件
        if (view == null) {         //如果为空，说明为第一屏
            view = mConvertView.findViewById(resId);    //从convertView中找
            mViews.put(resId,view);     
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
