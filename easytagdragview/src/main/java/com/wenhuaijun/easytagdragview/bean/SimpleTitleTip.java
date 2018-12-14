package com.wenhuaijun.easytagdragview.bean;

public class SimpleTitleTip implements Tip {
    private int id;
    private String tip;
    private boolean isSelected;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isSelected() {
        return isSelected;
    }
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    @Override
    public String toString() {
        return "tip:"+ tip;
    }

}
