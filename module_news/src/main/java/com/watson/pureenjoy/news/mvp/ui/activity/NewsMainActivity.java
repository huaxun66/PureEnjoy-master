package com.watson.pureenjoy.news.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.news.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;

public class NewsMainActivity extends NewsBaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouterHub.NEWS_FRAGMENT).navigation();
        loadFragment(fragment);
    }


    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, fragment);
        transaction.commit();
    }
}
