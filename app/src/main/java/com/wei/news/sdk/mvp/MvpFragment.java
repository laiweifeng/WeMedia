package com.wei.news.sdk.mvp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wei.news.sdk.base.BaseFragment;
import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.utils.L;


public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
