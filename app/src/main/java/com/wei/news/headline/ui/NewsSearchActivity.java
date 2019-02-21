package com.wei.news.headline.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wei.news.R;
import com.wei.news.headline.adapter.NewsAdapter;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.headline.presenter.NewsSearchPresenter;
import com.wei.news.headline.view.INewsSearchView;
import com.wei.news.sdk.manager.InputMethodManager;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.mvp.MvpActivity;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.sdk.widget.FootView;
import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class NewsSearchActivity extends MvpActivity<NewsSearchPresenter> implements INewsSearchView, View.OnClickListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, FootView.OnFootViewErrorClickListener, LoadStatusView.onReloadClickListener, TextView.OnEditorActionListener {


    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.lv_tab)
    ListView listView;



    private NewsAdapter newsAdapter;
    private FootView footView;
    private ArrayList<TypeListEntity.Data> newsList;
    ArrayList<Disposable> disposables=new ArrayList<>();
    private String content;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        footView = new FootView(getContext());
        footView.setVisibility(View.GONE);
        listView.addFooterView(footView);

        addLoadView(R.id.rootview);
    }

    @Override
    public void initData() {
        newsList = new ArrayList();
        newsAdapter =new NewsAdapter(getContext(), newsList,R.layout.item_tab);
        listView.setAdapter(newsAdapter);
    }

    @Override
    public void setListener() {
        iv_back.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
        footView.setOnFootViewErrorClickListener(this);
        getLoadStatusView().setonReloadClickListener(this);
        et_content.setOnEditorActionListener(this);
    }

    @Override
    public void loadData(TypeListEntity data) {
        if(data!=null){
            newsList.clear();
            newsList.addAll(data.getData());
            newsAdapter.notifyDataSetChanged();
            return;
        }
    }

    @Override
    public void addData(TypeListEntity data) {
        if(data.getData()!=null){
            newsList.addAll(data.getData());
            newsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMoreLoadingView() {
        footView.showLoading();
    }

    @Override
    public void showErrorMoreLoadView() {
        footView.showError();
    }

    @Override
    public void showFootView() {
        footView.setVisibility(View.VISIBLE);
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void showLoadingView() {
        getLoadStatusView().showLoading();
        btn_search.setEnabled(false);
    }

    @Override
    public void hideLoadingView() {
        getLoadStatusView().hideLoadStatus();
        btn_search.setEnabled(true);
    }

    @Override
    public void showNoMore() {
        footView.showNoMore();
    }

    @Override
    public void showReload() {
        getLoadStatusView().showReload();
    }


    @Override
    protected NewsSearchPresenter createPresenter() {
        return new NewsSearchPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_search:
                search();
                break;
        }
    }

    private void search() {
        L.d("search .... ");
        footView.setVisibility(View.GONE);
        newsList.clear();
        newsAdapter.notifyDataSetChanged();

        content = et_content.getText().toString();
        if(TextUtils.isEmpty(content)){
            TipManager.showTip(R.string.input_search_content);
            return;
        }
        InputMethodManager.hideInput(getContext(),et_content);
        mvpPresenter.loadData(content);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(absListView.getLastVisiblePosition()==absListView.getCount()-3){
            showMoreLoadingView();
        }

        if((firstVisibleItem+visibleItemCount)==totalItemCount
                &&totalItemCount>2
                &&absListView.getLastVisiblePosition()==absListView.getCount()-1
                &&!footView.isLoadError()){
            mvpPresenter.addData(content);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TypeListEntity.Data data= (TypeListEntity.Data) parent.getItemAtPosition(position);
        Bundle bundle=new Bundle();
        bundle.putString("url", data.getUrl());
        bundle.putString("title", data.getTitle());
        IntentManager.startActivity(getContext(), NewsDetailsActivity.class,bundle);

        data.setViewed(true);
        newsList.set(position,data);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnFootViewClick() {
        mvpPresenter.addData(content);
    }

    @Override
    public void OnReloadClick() {
        mvpPresenter.loadData(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitCreateHelper.getInstance().clearDisposables(disposables);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            search();
           return true;
        }
        return false;
    }
}
