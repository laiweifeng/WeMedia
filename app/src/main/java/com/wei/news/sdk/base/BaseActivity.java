package com.wei.news.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.wei.news.R;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.L;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
		init();
		initData();
		setListener();
	}


	public Context getContext(){
		return this;
	}

	public abstract int getLayoutId();

	public abstract void init();

	public abstract void initData();

	public abstract void setListener();


}
