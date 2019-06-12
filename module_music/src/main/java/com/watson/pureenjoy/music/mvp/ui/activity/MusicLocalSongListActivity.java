package com.watson.pureenjoy.music.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TITLE;
import static com.watson.pureenjoy.music.app.MusicConstants.KEY_TYPE;

@Route(path = RouterHub.MUSIC_LOCAL_SONG_LIST_ACTIVITY)
public class MusicLocalSongListActivity extends MusicBaseActivity {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;

    private int type;
    private String title;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_local_song_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra(KEY_TYPE, -1);
        title = getIntent().getStringExtra(KEY_TITLE);
        mTopBar.setTitleColor(Color.WHITE);
        Fragment fragment = (BaseFragment)ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_SONG_FRAGMENT)
                .withInt(KEY_TYPE, type)
                .withString(KEY_TITLE, title)
                .navigation();
        loadFragment(fragment);
        initListener();
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
    }


}
