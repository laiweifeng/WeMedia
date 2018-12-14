package com.wei.news.games.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.wei.news.R;
import com.wei.news.games.adapter.GameFragmentAdapter;
import com.wei.news.games.adapter.PictureFragmentAdapter;
import com.wei.news.games.fragments.GameTabFragment;
import com.wei.news.games.fragments.PictureFragment;
import com.wei.news.sdk.base.BaseActivity;
import com.wei.news.utils.TagsManager;
import com.wenhuaijun.easytagdragview.bean.SimpleTitleTip;
import com.wenhuaijun.easytagdragview.bean.Tip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GamePictureActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tv_pageSize)
    TextView tv_pageSize;
    private ArrayList<String> urls;

    @Override
    public int getLayoutId() {
        return R.layout.activity_game_picture;
    }

    @Override
    public void init() {

    }

    @Override
    public void initData() {

        Bundle bundle = getIntent().getBundleExtra("bundle");
        int position=bundle.getInt("position");
        urls = (ArrayList<String>) bundle.getSerializable("picture_urls");


        ArrayList<PictureFragment> fragments = new ArrayList<>();

        for (String url : urls) {
            PictureFragment pictureFragment=new PictureFragment();
            pictureFragment.setUrl(url);
            fragments.add(pictureFragment);
        }


        PictureFragmentAdapter pictureFragmentAdapter =
                new PictureFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pictureFragmentAdapter);
        viewPager.setCurrentItem(position);

        tv_pageSize.setText(position+1+"/"+ urls.size());

    }

    @Override
    public void setListener() {
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_pageSize.setText(position+1+"/"+urls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
