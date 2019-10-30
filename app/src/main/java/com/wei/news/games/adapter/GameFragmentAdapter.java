package com.wei.news.games.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

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