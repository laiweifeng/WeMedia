package com.wei.news.sdk.widget;

import com.wei.news.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
 

public class MessageDialog extends Dialog implements View.OnClickListener {
	private Context mContext;
	private Button btn_ok;
	private Button btn_cancel;
	private TextView messageTxt;
	private OnButtonClickListener onButtonClickListener;
	private OnDismissListener onDismissListener;



	public MessageDialog(Context context) {
		super(context);
		this.mContext=context;
		setContentView(R.layout.dialog_message);
		findViews();
		setListener();
	}


	private void findViews() {
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		messageTxt = (TextView) findViewById(R.id.tv_message);
	}
	
	private void setListener() {
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}
	
	public MessageDialog setMeassage(String msg){
		messageTxt.setText(msg);
		return this;
	}



	public void setOnButtonClickListener(OnButtonClickListener listener){
		this.onButtonClickListener=listener;
	}

	public interface OnButtonClickListener{
		void onOk(View v);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			onButtonClickListener.onOk(btn_ok);
			dismiss();
			break;
		case  R.id.btn_cancel:
			dismiss();
			break;
		}
	}
	
	

}
