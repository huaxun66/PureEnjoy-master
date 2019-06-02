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
import com.watson.pureenjoy.music.di.component.DaggerMusicRankComponent;
import com.watson.pureenjoy.music.http.entity.rank.RankInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicRankContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicRankPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRankAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.RANK_INFO;

@Route(path = RouterHub.MUSIC_RANK_ACTIVITY)
public class MusicRankActivity extends MusicBaseActivity<MusicRankPresenter> implements MusicRankContract.View {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    MusicRankAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicRankComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_rank;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTopBar.setTitleColor(Color.WHITE);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        initListener();
        mPresenter.requestRankList(this);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        mAdapter.setOnItemClickListener((adapter, view, position) -> ARouter.getInstance().build(RouterHub.MUSIC_RANK_DETAIL_ACTIVITY)
                .withParcelable(RANK_INFO, ((RankInfo)adapter.getData().get(position)))
                .navigation());
    }

    @Override
    public Context getContext() {
        return this;
    }

}