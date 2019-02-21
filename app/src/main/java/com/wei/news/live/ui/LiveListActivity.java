package com.wei.news.live.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.news.R;
import com.wei.news.live.adapter.LiveHostAdapter;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.presenter.LiveListPresenter;
import com.wei.news.live.view.ILiveListView;
import com.wei.news.sdk.manager.CacheManager;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.mvp.MvpActivity;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.sdk.widget.FootView;
import com.wei.news.sdk.widget.GridViewWithHeaderAndFooter;
import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class LiveListActivity extends MvpActivity<LiveListPresenter> implements ILiveListView, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, LoadStatusView.onReloadClickListener, View.OnClickListener, TextView.OnEditorActionListener {

    @BindView(R.id.gv_live_list)
    GridViewWithHeaderAndFooter gv_live_list;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.btn_search)
    Button btn_search;



    private FootView footView;

    LiveHostAdapter liveHostAdapter;

    ArrayList<LiveListEntity.Data> liveListDataList;
    ArrayList<Disposable> disposables=new ArrayList<>();

    String tag;
    private String content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_list;
    }

    @Override
    public void init() {

        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        footView = new FootView(getContext());
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle!=null){
            tag = bundle.getString("tag");
        }
        gv_live_list.addFooterView(footView);
        footView.hide();

        addLoadView(R.id.rootview);

    }

    @Override
    public void initData() {

        liveListDataList=new ArrayList<>();
        if(!TextUtils.isEmpty(tag)){
            btn_search.setEnabled(false);
            mvpPresenter.loadData(tag);
            et_content.setText(tag);
        }

        liveHostAdapter = new LiveHostAdapter(getContext(), liveListDataList, R.layout.item_host_live);
        gv_live_list.setAdapter(liveHostAdapter);
    }

    @Override
    public void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        gv_live_list.setOnScrollListener(this);
        gv_live_list.setOnItemClickListener(this);
        getLoadStatusView().setonReloadClickListener(this);
        iv_back.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        et_content.setOnEditorActionListener(this);
    }

    @Override
    public void loadData(List<LiveListEntity.Data> data) {
        liveListDataList.clear();
        if(data!=null){
            liveListDataList.addAll(data);
        }
        liveHostAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List<LiveListEntity.Data> data) {
       liveListDataList.addAll(data);
       liveHostAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMoreLoadView() {
        footView.showError();
    }

    @Override
    public void showMoreLoadingView() {
        footView.showLoading();
    }

    @Override
    public void showFootView() {
        footView.show();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void showLoadingView() {
        if(liveListDataList.size()<=0){
            getLoadStatusView().showLoading();
        }
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingView() {
        btn_search.setEnabled(true);
        getLoadStatusView().hideLoadStatus();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNoMore() {
        footView.showNoMore();
    }

    @Override
    public void showReload() {
        if(liveListDataList.size()<=0){
            getLoadStatusView().showReload();
        }
    }

    @Override
    protected LiveListPresenter createPresenter() {
        return new LiveListPresenter(this);
    }

    @Override
    public void onRefresh() {
        mvpPresenter.loadData(tag);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(absListView.getLastVisiblePosition()==absListView.getCount()-3){
           showMoreLoadingView();
        }

        if((firstVisibleItem+visibleItemCount)==totalItemCount
                &&totalItemCount>2
                &&gv_live_list.getLastVisiblePosition()==gv_live_list.getCount()-1
                &&!footView.isNoMore()){
            mvpPresenter.addData(tag);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LiveListEntity.Data data= (LiveListEntity.Data) parent.getItemAtPosition(position);
        Bundle bundle=new Bundle();
        bundle.putSerializable("LiveData",data);
        IntentManager.startActivity(getContext(), LiveActivity.class,bundle);
    }

    @Override
    public void OnReloadClick() {
        swipeRefreshLayout.setRefreshing(true);
        mvpPresenter.loadData(tag);
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
        footView.hide();
        liveListDataList.clear();
        liveHostAdapter.notifyDataSetChanged();

        content = et_content.getText().toString();
        if(TextUtils.isEmpty(content)){
            TipManager.showTip(R.string.input_search_content);
            return;
        }
        com.wei.news.sdk.manager.InputMethodManager.hideInput(getContext(),et_content);

        btn_search.setEnabled(false);
        mvpPresenter.loadData(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitCreateHelper.getInstance().clearDisposables(disposables);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        search();
        return false;
    }
}
