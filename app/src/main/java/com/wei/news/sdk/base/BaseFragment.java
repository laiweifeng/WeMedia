package com.wei.news.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.utils.L;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

	private boolean isFragmentVisibleToUser;
	private boolean isViewCreate;
	private boolean canLoadData=true;
	private LoadStatusView loadStatusView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), null);
		ButterKnife.bind(this, view);
		isViewCreate=true;
		loadStatusView = new LoadStatusView(getContext());
		return view;
	}

	/**
	 * 添加加载状态背景
	 */
	public void addLoadView(int rootViewId) {
		if(getView()!=null){
			((ViewGroup)getView().findViewById(rootViewId)).addView(loadStatusView);
		}
	}

	public LoadStatusView getLoadStatusView(){
		return  loadStatusView;
	}

	//前一个fragment对用户来说是隐藏还是显示，
	// 这个方法仅仅工作在FragmentPagerAdapter中，
	// 不能被使用在一个普通的activity
	//可以重写 onHiddenChanged() 方法来得知当前 fragment 的状态。
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
//		L.d("setUserVisibleHint");
		isFragmentVisibleToUser=isVisibleToUser;
		if(canLoadData()){
            lazyData();
        }
	}


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initData();
        setListener();
    }


	public Context getContext() {
		return getActivity();
	}

	public abstract int getLayoutId();

	public abstract void init();

	/**
	 * 懒加载数据方法 只作用于FragmentPagerAdapter
	 */
	public abstract void lazyData();
	public abstract void initData();

	public abstract void setListener();

	/**
	 * 是否可加载数据 只作用于FragmentPagerAdapter
	 * @return
	 */
	public boolean canLoadData(){
        return isViewCreate&&isFragmentVisibleToUser&&canLoadData;
    }

	/**
	 * 设置是否可加载数据 只作用于FragmentPagerAdapter
	 * @param b
	 */
	public void setCanLoadData(boolean b){
        canLoadData=b;
    }

	public boolean isFragmentVisibleToUser() {
		return isFragmentVisibleToUser;
	}
}
