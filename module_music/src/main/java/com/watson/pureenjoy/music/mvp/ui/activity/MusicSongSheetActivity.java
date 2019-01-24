package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.di.component.DaggerMusicSongSheetComponent;
import com.watson.pureenjoy.music.mvp.contract.MusicSongSheetContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicSongSheetPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSongSheetAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.CustomLoadingMoreView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.TAG_SELECTED;

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
    private String currentTag = MusicConstants.TAG_ALL; //默认全部

    private final int REQUEST_CODE = 100;

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
        mTopBar.setTitleColor(Color.WHITE);
        mAdapter.setLoadMoreView(mCustomLoadingMoreView);
        mAdapter.setSelectedTag(currentTag);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        initListener();
        getData(0, true);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        mSmartRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tag_rl) {
                Intent intent = new Intent(getContext(), MusicSheetTagActivity.class);
                intent.putExtra(TAG_SELECTED, currentTag);
                startActivityForResult(intent, REQUEST_CODE);
                overridePendingTransition(R.anim.public_translate_bottom_to_center, R.anim.public_translate_center_to_top);
            }
        });
        mAdapter.setOnTagClickListener(tag -> {
            currentTag = tag;
            getData(0, true);
        });
    }

    private void getData(int pageNo, boolean showLoading) {
        if (pageNo == 0) {
            isRefresh = true;
            currentPageNo= 0;
        }
        if (currentTag.equals(MusicConstants.TAG_ALL)) {
            mPresenter.requestSongSheetList(this, pageNo, 10, showLoading);
        } else {
            mPresenter.requestSongSheetListByTag(this, currentTag, pageNo, 10, showLoading);
        }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    if (data.getStringExtra(TAG_SELECTED) !=  null) {
                        currentTag = data.getStringExtra(TAG_SELECTED);
                        mAdapter.setSelectedTag(currentTag);
                        mAdapter.notifyItemChanged(1);
                        getData(0, true);
                    }
                }
                break;
        }
    }
}