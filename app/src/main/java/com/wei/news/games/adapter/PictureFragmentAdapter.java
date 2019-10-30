package com.wei.news.games.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.wei.news.games.fragments.GameTabFragment;
import com.wei.news.games.fragments.PictureFragment;

import java.util.List;

public class PictureFragmentAdapter extends FragmentStatePagerAdapter {
    private List<PictureFragment> fragments;

    public PictureFragmentAdapter(FragmentManager fm, List<PictureFragment> fragments) {
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
                super.destroyItem(container, position, object);
    }
}