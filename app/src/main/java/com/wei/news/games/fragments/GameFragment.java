package com.wei.news.games.fragments;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.wei.news.MainActivity;
import com.wei.news.R;
import com.wei.news.games.adapter.GameFragmentAdapter;
import com.wei.news.games.ui.DownloadManagerActivity;
import com.wei.news.sdk.widget.SearchBoxView;
import com.wei.news.utils.TagsManager;
import com.wei.news.sdk.base.BaseFragment;
import com.wenhuaijun.easytagdragview.bean.SimpleTitleTip;
import com.wenhuaijun.easytagdragview.bean.Tip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GameFragment extends BaseFragment {


    @BindView(R.id.tl_tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.ib_downloadManager)
    ImageButton ib_downloadManager;

    private ArrayList<GameTabFragment> fragments;
    private GameFragmentAdapter gameFragmentAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    public void init() {


    }


    @Override
    public void initData() {
        fragments = new ArrayList<>();
        List<Tip> gamesTags = TagsManager.getGamesTags();
        for (int i = 0; i < gamesTags.size(); i++) {
            SimpleTitleTip simpleTitleTip= (SimpleTitleTip) gamesTags.get(i);
            GameTabFragment gameTabFragment = new GameTabFragment();
            gameTabFragment.setTitle(simpleTitleTip.getTip());
            fragments.add(gameTabFragment);
        }

        gameFragmentAdapter = new GameFragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(gameFragmentAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setVerticalScrollbarPosition(0);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        for (int i = 0; i < gamesTags.size(); i++) {
            SimpleTitleTip simpleTitleTip= (SimpleTitleTip) gamesTags.get(i);
            tabLayout.getTabAt(i).setText(simpleTitleTip.getTip());
        }
    }

    @Override
    public void lazyData() {

    }

    @Override
    public void setListener() {
        ib_downloadManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),DownloadManagerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeFragments();
    }

    public void removeFragments(){
        FragmentTransaction fragmentTransaction=((MainActivity)getContext()).getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            fragmentTransaction.remove(fragment);
        }
    }
}
