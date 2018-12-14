package com.wei.news.sdk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * Listview 通用adapter
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    //需要显示的数据，List中的类型为泛型，因为不知道用户的封装Bean
    private List<T> mDatas;
    
    private Context mContext;
    //布局文件Id
    private int mLayoutId;
    public CommonAdapter(Context context,List<T> data,int layoutId) {
        mDatas = data;
        mContext = context;
        mLayoutId = layoutId;
    }

    public Context getContext(){
        return  mContext;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(mContext,convertView, parent, mLayoutId);
        setDatas(holder,getItem(position));
        return holder.getConvertView();
    }

    /**
     * 为各个item中的控件设置数据
     * @param holder   ViewHolder
     * @param object  从集合中所取的一个对象
     */
    public abstract void setDatas(ViewHolder holder, Object object);
}
