package com.wei.news;

import android.os.Message;
import android.widget.RadioGroup;

import com.wei.news.games.fragments.GameFragment;
import com.wei.news.live.fragments.LiveFragment;
import com.wei.news.sdk.base.BaseActivity;
import com.wei.news.headline.fragments.HeadlineFragment;
import com.wei.news.me.fragments.MeFragment;
import com.wei.news.sdk.base.FragmentFactory;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.video.fragments.VideoFragment;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rg_group)
    RadioGroup radioGroup;
    private FragmentFactory mFragmentFactory;

    //是否可退出应用
    boolean isBack=false;

    private static final int HANDLER_BLACK=0;

    ArrayList<Disposable> disposables=new ArrayList<>();

    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mFragmentFactory = FragmentFactory.getInstance().init(this, R.id.fl_container);

    }

    @Override
    public void initData() {
        mFragmentFactory.showFragment(HeadlineFragment.class);
    }

    @Override
    public void setListener() {
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentFactory.removeFragments();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_news:
                mFragmentFactory.showFragment(HeadlineFragment.class);

                break;
            case R.id.rb_video:
                mFragmentFactory.showFragment(VideoFragment.class);

                break;
            case R.id.rb_game:
                mFragmentFactory.showFragment(GameFragment.class);

                break;
            case R.id.rb_live:
                mFragmentFactory.showFragment(LiveFragment.class);
                break;
            case R.id.rb_me:
                mFragmentFactory.showFragment(MeFragment.class);

                break;
        }
    }


    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_BLACK:
                    isBack=false;
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if(!isBack){
            isBack=true;
            handler.sendEmptyMessageDelayed(HANDLER_BLACK,2000);
            TipManager.showTip(R.string.exit);
           return;
        }
        super.onBackPressed();
        RetrofitCreateHelper.getInstance().clearDisposables(disposables);
    }

    public  void addDisposable(Disposable disposable){
        disposables.add(disposable);
    }



}
