package com.wei.news.sdk.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentManager{

    public static final String KEY_NAME ="bundle" ;

    public static  void startActivity(Context context, Class<?> activityClass, Bundle bundle){
        Intent intent =new Intent(context,activityClass);
        intent.putExtra(KEY_NAME,bundle);
        context.startActivity(intent);
    }
}
