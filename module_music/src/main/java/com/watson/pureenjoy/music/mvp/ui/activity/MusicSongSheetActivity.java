package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicSongSheetComponent;
import com.watson.pureenjoy.music.mvp.contract.MusicSongSheetContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicSongSheetPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSongSheetAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.CustomLoadingMoreView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.MUSIC_SONG_SHEET_ACTIVITY)
public class MusicSongSheetActivity extends MusicBaseActivity<MusicSongSheetPresenter> implements MusicSongSheetContract.View,
        OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    MusicSongSheetAdapter mAdapter;
    @Inject
    CustomLoadingMoreView mCustomLoadingMoreView;

    private boolean isRefresh;
    private int currentPageNo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicSongSheetComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_song_sheet;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAdapter.setLoadMoreView(mCustomLoadingMoreView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        initListener();
        getData(0, true);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        mSmartRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

    private void getData(int pageNo, boolean showLoading) {
        if (pageNo == 0) {
            isRefresh = true;
            currentPageNo= 0;
        }
        mPresenter.requestSongSheetList(this, pageNo, 10, showLoading);
    }

    @Override
    public void getSongSheetListSuccess(boolean haveMore) {
        if (isRefresh) {
            mSmartRefreshLayout.finishRefresh(true);
            isRefresh = false;
        } else {
            if (haveMore) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.loadMoreComplete();
            }
        }
        currentPageNo++;
        mAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onLoadMoreRequested() {
        getData(currentPageNo, false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData(0, false);
    }
}