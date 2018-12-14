package com.wei.news.sdk.manager;

import android.content.Context;
import android.view.View;

public class InputMethodManager {

    public static void hideInput(Context context,View view){
        android.view.inputmethod.InputMethodManager InputMethodManager=
                (android.view.inputmethod.InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
