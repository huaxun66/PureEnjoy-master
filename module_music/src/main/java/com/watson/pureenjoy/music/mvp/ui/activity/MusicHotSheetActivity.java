package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicHotSheetComponent;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicHotSheetContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicHotSheetPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicHotSheetAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.DividerItemDecoration;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.SHEET_INFO;

@Route(path = RouterHub.MUSIC_HOT_SHEET_ACTIVITY)
public class MusicHotSheetActivity extends MusicBaseActivity<MusicHotSheetPresenter> implements MusicHotSheetContract.View {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    MusicHotSheetAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    DividerItemDecoration mItemDecoration;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicHotSheetComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_hot_sheet;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTopBar.setTitleColor(Color.WHITE);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setAdapter(mAdapter);
        initListener();
        mPresenter.requestHotSheetList(this, 50);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ARouter.getInstance().build(RouterHub.MUSIC_SHEET_DETAIL_ACTIVITY)
                    .withParcelable(SHEET_INFO, (SheetInfo)adapter.getData().get(position))
                    .navigation();
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

}