package com.watson.pureenjoy.music.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.http.entity.rank.RankInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.adapter.FragmentViewPagerAdapter;
import me.jessyan.armscomponent.commonres.view.EasyTabBarTxtScroll;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.RANK_INFO;

@Route(path = RouterHub.MUSIC_LOCAL_MUSIC_ACTIVITY)
public class MusicLocalMusicActivity extends MusicBaseActivity {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.vp_fragment_local)
    ViewPager mViewPager;
    @BindView(R2.id.easyTabBar_fragment_local)
    EasyTabBarTxtScroll mTabBar;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_local_music;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTopBar.setTitleColor(Color.WHITE);
        List<String> titleList = new ArrayList<>();
        List<BaseFragment> fragmentList = new ArrayList<>();
        titleList.add(getString(R.string.music_song));
        titleList.add(getString(R.string.music_artist));
        titleList.add(getString(R.string.music_album));
        titleList.add(getString(R.string.music_folder));
        fragmentList.add((BaseFragment) ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_SONG_FRAGMENT).navigation());
        fragmentList.add((BaseFragment) ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_SINGER_FRAGMENT).navigation());
        fragmentList.add((BaseFragment) ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_ALBUM_FRAGMENT).navigation());
        fragmentList.add((BaseFragment) ARouter.getInstance().build(RouterHub.MUSIC_LOCAL_FOLDER_FRAGMENT).navigation());
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), titleList, fragmentList);
        mViewPager.setAdapter(adapter);
        mTabBar.setViewPager(mViewPager);
        initListener();
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        mTopBar.setRightImageClickListener(v -> ARouter.getInstance().build(RouterHub.MUSIC_SCAN_ACTIVITY).navigation());
    }
}
