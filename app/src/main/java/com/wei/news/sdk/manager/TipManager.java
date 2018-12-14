package com.wei.news.sdk.manager;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.wei.news.App;

public class TipManager {

    public  static void showTip( int stringid){
        Toast.makeText(App.getContext(),stringid,Toast.LENGTH_LONG).show();
    }
    public  static void showTip( String message){
        Toast.makeText(App.getContext(),message,Toast.LENGTH_LONG).show();
    }
}
