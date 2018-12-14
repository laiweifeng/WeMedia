package com.wenhuaijun.easytagdragview.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wenhuaijun.easytagdragview.widget.DragDropGirdView;
import com.wenhuaijun.easytagdragview.bean.Tip;
import com.wenhuaijun.easytagdragview.R;
import com.wenhuaijun.easytagdragview.widget.TipItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wenhuaijun on 2016/5/26 0026.
 */
public class DragTipAdapter extends AbsTipAdapter implements View.OnLongClickListener, TipItemView.OnDeleteClickListener{

    private boolean  isEditing =false;
    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("", "");
    private TipItemView.OnSelectedListener mListener;
    private TipItemView.OnDeleteClickListener deleteClickListener;
    private OnFirstDragStartCallback callback;

    private int lastPosition = -1; // 记录上一次选中的位置，-1表示未选中
    private boolean multiChoose; // 表示当前适配器是否允许多选


    public DragTipAdapter(Context context, DragDropListener dragDropListener, TipItemView.OnDeleteClickListener deleteClickListener) {
        super(context, dragDropListener);
        this.deleteClickListener =deleteClickListener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TipItemView view =null;
        if(convertView!=null&&convertView instanceof TipItemView){
            view =(TipItemView)convertView;
        }else{
            view = (TipItemView)View.inflate(mContext, R.layout.view_tag_item, null);
        }
        if(isEditing){
            view.showDeleteImg();
        }else{
            view.hideDeleteImg();
        }

        if (position<getmTextSelected().size()) {
            int resId = getmTextSelected().get(position) ? R.color.colorPrimary : R.drawable.tag_item_bg;
            view.findViewById(R.id.tagview_title).setBackgroundResource(resId);
        }
        //设置点击监听
        view.setItemListener(position, mListener);
        view.setOnLongClickListener(this);
        //设置删除监听
        view.setDeleteClickListener(position, deleteClickListener);
        //绑定数据
        view.renderData(getItem(position));
        return view;
    }

    @Override
    protected Tip getDragEntity(View view) {
        return ((TipItemView)view).getDragEntity();
    }

    public void setItemSelectedListener(TipItemView.OnSelectedListener mListener){
        this.mListener =mListener;
    }
    @Override
    public boolean onLongClick(View v) {
        //开启编辑模式
        startEdittingStatus(v);
        return true;
    }
    //删除按钮点击时
    @Override
    public void onDeleteClick(Tip entity, int position, View view) {
        tips.remove(position);
        refreshData();

    }
    public void refreshData(){
        notifyDataSetChanged();
        mDragDropListener.onDataSetChangedForResult(tips);
    }
    public ArrayList<Tip> getData(){
        return tips;
    }
    public void setFirtDragStartCallback(OnFirstDragStartCallback callback) {
        this.callback = callback;
    }

    public interface OnFirstDragStartCallback {
        void firstDragStartCallback();
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void cancelEditingStatus(){
        isEditing =false;
        notifyDataSetChanged();
    }
    private  void startEdittingStatus(View v){
        if(!isEditing){
            isEditing =true;
            if(callback!=null){
                callback.firstDragStartCallback();
            }
            notifyDataSetChanged();

        }
        v.startDrag(EMPTY_CLIP_DATA, new View.DragShadowBuilder(),
                DragDropGirdView.DRAG_FAVORITE_TILE, 0);
    }


}
