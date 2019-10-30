package com.wei.news.headline.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

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