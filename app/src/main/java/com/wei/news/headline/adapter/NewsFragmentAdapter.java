package com.wei.news.headline.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.wei.news.headline.fragments.TabFragment;

import java.util.List;

public class NewsFragmentAdapter extends FragmentStatePagerAdapter {
    private List<TabFragment> fragments;

    public void notifyDataSetChanged(List<TabFragment> fragments){
        this.fragments = fragments;
        notifyDataSetChanged();
    }


    public NewsFragmentAdapter(FragmentManager fm, List<TabFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //        super.destroyItem(container, position, object);
    }
}