package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicRankDetailComponent;
import com.watson.pureenjoy.music.http.entity.rank.RankInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicRankDetailContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicRankDetailPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRankDetailAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

import static com.watson.pureenjoy.music.app.MusicConstants.RANK_INFO;

@Route(path = RouterHub.MUSIC_RANK_DETAIL_ACTIVITY)
public class MusicRankDetailActivity extends MusicBaseActivity<MusicRankDetailPresenter> implements MusicRankDetailContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.background)
    ImageView mHeaderBackground;
    @BindView(R2.id.update_time)
    TextView mUpdateTime;
    @BindView(R2.id.num)
    TextView mNum;
    @BindView(R2.id.collection)
    TextView mCollection;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MusicRankDetailAdapter mAdapter;

    @Autowired(name = RANK_INFO)
    RankInfo mRankInfo;

    private ImageLoader mImageLoader;
    private boolean isRefresh;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicRankDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_rank_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        mTopBar.setTitleColor(Color.WHITE);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setHeaderData();
        initListener();
        getData(0, true);
    }

    private void getData(int offset, boolean showLoading) {
        if (offset == 0) {
            isRefresh = true;
        }
        mPresenter.requestRankDetail(this, mRankInfo.getType(), offset, 10, showLoading);
    }

    @Override
    public void onLoadMoreRequested() {
        getData(mAdapter.getData().size(), false);
    }

    private void setHeaderData() {
        mTopBar.setBackgroundColor(Color.parseColor(mRankInfo.getColor().replace("0x", "#")));
        mImageLoader.loadImage(this,
                CommonImageConfigImpl
                        .builder()
                        .url(mRankInfo.getPic_s444())
                        .fallback(R.drawable.music_hot_sheet_bg)
                        .errorPic(R.drawable.music_hot_sheet_bg)
                        .imageView(mHeaderBackground)
                        .build());
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

    @Override
    public void getRankDetailSuccess(RankInfo rankInfo) {
        if (isRefresh) {
            isRefresh = false;
            mUpdateTime.setText(getString(R.string.music_latest_update) + rankInfo.getUpdate_date());
            mNum.setText(getString(R.string.music_song_num, rankInfo.getBillboard_songnum()!=null ? Integer.parseInt(rankInfo.getBillboard_songnum()) : 100));
        } else {
            if (rankInfo.getHavemore() == 1) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.loadMoreComplete();
            }
        }
        mAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public Context getContext() {
        return this;
    }

}