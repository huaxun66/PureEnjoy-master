package com.watson.pureenjoy.music.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;

public class MusicMainActivity extends MusicBaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouterHub.MUSIC_FRAGMENT).navigation();
        loadFragment(fragment);
    }


    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, fragment);
        transaction.commit();
    }


}
