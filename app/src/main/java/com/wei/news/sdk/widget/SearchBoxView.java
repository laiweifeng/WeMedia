package com.wei.news.sdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wei.news.R;

public class SearchBoxView extends RelativeLayout {

    private Button btn_reload;
    private ImageView iv_icon;
    private TextView tv_message;

    public SearchBoxView(Context context) {
        this(context,null);
    }

    public SearchBoxView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.widget_search_box, this);
        iv_icon = view.findViewById(R.id.iv_icon);
        tv_message = view.findViewById(R.id.tv_message);

    }


    public void setSearchMessage(String message){
        tv_message.setText(message);
    }

    public void setIcon(int drawableId){
        iv_icon.setBackgroundResource(drawableId);
    }


}
