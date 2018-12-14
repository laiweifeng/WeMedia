package com.wei.news.headline.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.wei.news.MainActivity;
import com.wei.news.R;
import com.wei.news.headline.TagsDialog;
import com.wei.news.headline.ui.NewsSearchActivity;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.widget.SearchBoxView;
import com.wei.news.utils.TagsManager;
import com.wei.news.sdk.base.BaseFragment;
import com.wei.news.headline.adapter.NewsFragmentAdapter;
import com.wenhuaijun.easytagdragview.bean.SimpleTitleTip;
import com.wenhuaijun.easytagdragview.bean.Tip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeadlineFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tl_tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.iv_tag_edit)
    ImageView iv_tag_edit;

    @BindView(R.id.searchview)
    SearchBoxView searchBoxView;

    private ArrayList<TabFragment> fragments;
    private NewsFragmentAdapter newsFragmentAdapter;
    private int mSelectPosition;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_headline;
    }

    @Override
    public void init() {}


    @Override
    public void initData() {
        fragments = new ArrayList<>();
        List<Tip> dragTips = TagsManager.getNewsDragTips();
        for (int i = 0; i < dragTips.size(); i++) {
            SimpleTitleTip simpleTitleTip= (SimpleTitleTip) dragTips.get(i);
            TabFragment tabFragment = new TabFragment();
            tabFragment.setTitle(simpleTitleTip.getTip());
            fragments.add(tabFragment);
        }


        newsFragmentAdapter = new NewsFragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(newsFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setVerticalScrollbarPosition(0);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        for (int i = 0; i < dragTips.size(); i++) {
            SimpleTitleTip simpleTitleTip= (SimpleTitleTip) dragTips.get(i);
            tabLayout.getTabAt(i).setText(simpleTitleTip.getTip());
        }

    }

    private void updateData(){
        fragments = new ArrayList<>();

        if(mSelectPosition>0){
            newsFragmentAdapter = new NewsFragmentAdapter(getChildFragmentManager(), fragments);
            viewPager.setAdapter(newsFragmentAdapter);
        }

        List<Tip> dragTips = TagsManager.getNewsDragTips();
        for (int i = 0; i < dragTips.size(); i++) {
            SimpleTitleTip simpleTitleTip= (SimpleTitleTip) dragTips.get(i);
            TabFragment tabFragment = new TabFragment();
            tabFragment.setTitle(simpleTitleTip.getTip());
            fragments.add(tabFragment);
        }

        newsFragmentAdapter = new NewsFragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(newsFragmentAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        for (int i = 0; i < dragTips.size(); i++) {
            SimpleTitleTip simpleTitleTip= (SimpleTitleTip) dragTips.get(i);
            tabLayout.getTabAt(i).setText(simpleTitleTip.getTip());
        }
    }




    @Override
    public void lazyData() {

    }

    @Override
    public void setListener() {
        iv_tag_edit.setOnClickListener(this);
        searchBoxView.setOnClickListener(this);
    }


    public void removeFragments(){
        FragmentTransaction fragmentTransaction=((MainActivity)getContext()).getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            fragmentTransaction.remove(fragment);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_tag_edit:
                TagsDialog tagsDialog=new TagsDialog(getContext());
                tagsDialog.show();

                tagsDialog.setOnDismissListener(new TagsDialog.OnDismissListener() {
                    @Override
                    public void onDismiss(boolean isDataChannger,int selectPosition) {
                        mSelectPosition=selectPosition;

                        if(isDataChannger){

                            removeFragments();
                            updateData();
                        }

                        if(selectPosition!=-1){
                            tabLayout.getTabAt(selectPosition).select();
                        }
                    }
                });
                break;
            case R.id.searchview:
                IntentManager.startActivity(getContext(),NewsSearchActivity.class,null);
                break;
        }
    }

}
