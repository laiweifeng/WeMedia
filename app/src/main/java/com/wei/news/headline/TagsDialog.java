package com.wei.news.headline;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wei.news.R;
import com.wei.news.utils.L;
import com.wei.news.sdk.manager.SharePreferenceManager;
import com.wei.news.utils.TagsManager;
import com.wenhuaijun.easytagdragview.EasyTipDragView;
import com.wenhuaijun.easytagdragview.bean.Tip;
import com.wenhuaijun.easytagdragview.widget.TipItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagsDialog extends Dialog {

    @BindView(R.id.easy_tip_drag_view)
    EasyTipDragView easyTipDragView;
    private OnDismissListener onDismissListener;

    private boolean isDataChannger;
    private int selectPosition=-1;

    public TagsDialog(@NonNull Context context) {
        this(context,R.style.Dialog_Fullscreen);
    }

    public TagsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogBottom); // 添加动画

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tag_select);
        ButterKnife.bind(this, this);
        initDialog();

        //设置已包含的标签数据
        easyTipDragView.setAddData(TagsManager.getNewsAddTips());
        //设置可以添加的标签数据
        easyTipDragView.setDragData(TagsManager.getNewsDragTips());
        //在easyTipDragView处于非编辑模式下点击item的回调（编辑模式下点击item作用为删除item）
        easyTipDragView.setSelectedListener(new TipItemView.OnSelectedListener() {
            @Override
            public void onTileSelected(Tip entity, int position, View view) {
                List<Boolean> mSelected= easyTipDragView.getDragTipAdapter().getmTextSelected();


                selectPosition=position;
                for (int i=0;i<mSelected.size();i++){
                    if (i!=position){
                        easyTipDragView.getDragTipAdapter().getmTextSelected().set(i,false);
                    }else{
                        easyTipDragView.getDragTipAdapter().getmTextSelected().set(i,true);
                    }
                }
                easyTipDragView.getDragTipAdapter().notifyDataSetChanged();

                dismiss();
            }
        });
        //设置每次数据改变后的回调（例如每次拖拽排序了标签或者增删了标签都会回调）
        easyTipDragView.setDataResultCallback(new EasyTipDragView.OnDataChangeResultCallback() {
            @Override
            public void onDataChangeResult(ArrayList<Tip> tips) {
                isDataChannger=true;
                L.v( "onDataChangeResult:"+tips.toString());
                SharePreferenceManager.putObject(getContext(),"news_tags",tips);
            }
        });
        //设置点击“确定”按钮后最终数据的回调
        easyTipDragView.setOnCompleteCallback(new EasyTipDragView.OnCompleteCallback() {
            @Override
            public void onComplete(ArrayList<Tip> tips) {
                L.d("onComplete：" + tips.toString());
                isDataChannger=true;
                SharePreferenceManager.putObject(getContext(),"news_tags",tips);
                dismiss();
            }
        });

    }

    private void initDialog() {
        setTitle(R.string.all_tag);

        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(onDismissListener!=null){
            onDismissListener.onDismiss(isDataChannger,selectPosition);
        }
    }

    public  void setOnDismissListener(OnDismissListener listener){
        this.onDismissListener=listener;
    }

    public interface OnDismissListener{
        void onDismiss(boolean isDataChannger,int selectPosition);
    }
}
