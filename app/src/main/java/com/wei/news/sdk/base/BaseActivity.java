package com.wei.news.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;

import com.wei.news.R;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.utils.L;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {


	private LoadStatusView loadStatusView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
		loadStatusView = new LoadStatusView(this);
		init();
		initData();
		setListener();
	}

	/**
	 * 添加加载状态背景
	 */
	public void addLoadView(int rootViewId) {
		((ViewGroup)findViewById(rootViewId)).addView(loadStatusView);
	}

	public LoadStatusView getLoadStatusView(){
		return  loadStatusView;
	}


	public Context getContext(){
		return this;
	}

	public abstract int getLayoutId();

	public abstract void init();

	public abstract void initData();

	public abstract void setListener();


}
