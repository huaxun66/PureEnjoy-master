package com.watson.pureenjoy.music.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.MUSIC_SONG_SHEET_ACTIVITY)
public class MusicSongSheetActivity extends MusicBaseActivity implements
        OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;


//    @Inject
//    RecyclerView.LayoutManager layoutManager;
//    @Inject
//    MusicSongSheetAdapter adapter;
//    @Inject
//    List<SheetInfo> allData;

    private boolean isRefresh;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerMusicSongSheetComponent
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_song_sheet;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        adapter.setLoadMoreView(new CustomLoadingMoreView());
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
//        mRecyclerView.setAdapter(adapter);
//        initListener();
        getData(0, true);
    }

    private void getData(int pageNo, boolean showLoading) {
        if (pageNo == 0) {
            isRefresh = true;
        }

    }


    @Override
    public void onLoadMoreRequested() {

    }

//    @Override
//    public void setSongSheetList(List<SheetInfo> sheetList, boolean haveMore) {
//
//    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}