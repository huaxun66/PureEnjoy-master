package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.wang.avi.AVLoadingIndicatorView;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.adapter.FragmentViewPagerAdapter;
import me.jessyan.armscomponent.commonres.view.EasyTabBarTxtScroll;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.MUSIC_FRAGMENT)
public class MusicFragment extends MusicBaseFragment {
    @BindView(R2.id.vp_fragment_music_display)
    ViewPager mViewPager;
    @BindView(R2.id.easyTabBar_fragment_music_tab)
    EasyTabBarTxtScroll mTabBar;
    @BindView(R2.id.search_rl)
    RelativeLayout mSearch;
    @BindView(R2.id.avi)
    AVLoadingIndicatorView mIndicatorView;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.music_fragment_home, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
        List<String> titleList = new ArrayList<>();
        List<BaseFragment> fragmentList = new ArrayList<>();
        titleList.add(getString(R.string.music_personality_recommend));
        titleList.add(getString(R.string.music_anchor_radio));
        titleList.add(getString(R.string.music_my_music));
        fragmentList.add((BaseFragment) ARouter.getInstance().build(RouterHub.MUSIC_PERSONALITY_RECOMMEND_FRAGMENT).navigation());
        fragmentList.add((BaseFragment) ARouter.getInstance().build(RouterHub.MUSIC_ANCHOR_RADIO_FRAGMENT).navigation());
        fragmentList.add((BaseFragment) ARouter.getInstance().build(RouterHub.MUSIC_MY_MUSIC_FRAGMENT).navigation());
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getChildFragmentManager(), titleList, fragmentList);
        mViewPager.setAdapter(adapter);
        mTabBar.setViewPager(mViewPager);
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    private void initListener() {
        mSearch.setOnClickListener(v -> {
             //TODO 搜索
        });
    }
}
