package com.wei.news.sdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wei.news.R;
import com.wei.news.utils.L;

public class FootView extends RelativeLayout {

    private TextView tv_message;
    private ProgressBar progressBar;
    private OnFootViewErrorClickListener footViewClickListener;

    private boolean noMore=false;

    public final  static int STATUS_LOADING=0;
    public final  static int STATUS_NO_MORE=1;
    public final  static int STATUS_ERROR=2;

    private int curStatus;

    public FootView(Context context) {
        this(context,null);
    }

    public FootView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.foot_view, this);

        tv_message = view.findViewById(R.id.tv_message);
        progressBar = view.findViewById(R.id.progressBar);
        findViewById(R.id.ll_rootView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                L.d("FootView  onClick");
                if(footViewClickListener!=null&&curStatus==STATUS_ERROR){
                    footViewClickListener.OnFootViewClick();
                }
            }
        });


    }


    public void show(){
        this.setVisibility(View.VISIBLE);
    }
    public void hide(){
        this.setVisibility(View.GONE);
    }

    public void showError(){
        tv_message.setText(R.string.tip_click_retry);
        progressBar.setVisibility(View.GONE);
        curStatus=STATUS_ERROR;
    }
    public void showNoMore(){
        tv_message.setText(R.string.no_more);
        progressBar.setVisibility(View.GONE);
        curStatus=STATUS_NO_MORE;
    }

    public void showLoading(){
        tv_message.setText(R.string.loading_tip);
        progressBar.setVisibility(View.VISIBLE);
        curStatus=STATUS_LOADING;
    }

    public boolean isNoMore() {
        return curStatus==STATUS_NO_MORE;
    }
    public boolean isLoadError() {
        return curStatus==STATUS_ERROR;
    }

    public void setOnFootViewErrorClickListener(OnFootViewErrorClickListener listener){
        this.footViewClickListener=listener;
    }

    public  interface OnFootViewErrorClickListener{
        void OnFootViewClick();
    }
}
