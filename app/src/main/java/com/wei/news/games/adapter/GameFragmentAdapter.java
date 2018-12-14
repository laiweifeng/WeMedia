package com.wei.news.games.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.wei.news.games.fragments.GameFragment;
import com.wei.news.games.fragments.GameTabFragment;

import java.util.ArrayList;
import java.util.List;

public class GameFragmentAdapter extends FragmentStatePagerAdapter {
    private List<GameTabFragment> fragments;

    public GameFragmentAdapter(FragmentManager fm, List<GameTabFragment> fragments) {
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