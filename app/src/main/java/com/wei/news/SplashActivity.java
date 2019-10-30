package com.wei.news;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;

import androidx.annotation.NonNull;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;
import com.wei.news.sdk.permission.KbPermission;
import com.wei.news.sdk.permission.KbPermissionListener;
import com.wei.news.sdk.permission.KbPermissionUtils;
import com.wei.news.utils.Constant;

public class SplashActivity extends AwesomeSplash {


    @Override
    public void initSplash(final ConfigSplash configSplash) {
        /* you don't have to override every property */
        KbPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE) //需要申请的权限(支持不定长参数)
                .callBack(new KbPermissionListener() {
                    @Override
                    public void onPermit(int requestCode, String... permission) { //允许权限的回调
                    }

                    @Override
                    public void onCancel(int requestCode, String... permission) { //拒绝权限的回调
                        KbPermissionUtils.goSetting(SplashActivity.this); //跳转至当前app的权限设置界面
                    }
                })
                .send();
        initSplashData(configSplash);

    }

    private void initSplashData(ConfigSplash configSplash) {
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.gray_light); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(600); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

//        //Customize Logo
//        configSplash.setLogoSplash(R.drawable.ic_launcher); //or any other drawable
//        configSplash.setAnimLogoSplashDuration(2000); //int ms
//        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        configSplash.setPathSplash(Constant.DROID_LOGO); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(250);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.black_gray); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(250);
        configSplash.setPathSplashFillColor(R.color.black_gray); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("GitHub:LaiWeiFeng");
        configSplash.setTitleTextColor(R.color.colorAccent);
        configSplash.setTitleTextSize(35f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //必须添加，否则第一次请求成功权限不会走回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        KbPermission.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}