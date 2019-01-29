package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtil;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

import static com.watson.pureenjoy.music.app.MusicConstants.RANK_INFO;

@Route(path = RouterHub.MUSIC_RANK_DETAIL_ACTIVITY)
public class MusicRankDetailActivity extends MusicBaseActivity<MusicRankDetailPresenter> implements MusicRankDetailContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R2.id.top_bar)
    ConstraintLayout mTopBar;
    @BindView(R2.id.top_back)
    ImageView mTopBack;
    @BindView(R2.id.top_title)
    TextView mTopTitle;
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

    private boolean isRefresh;
    private ImageLoader mImageLoader;

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
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        return R.layout.music_activity_rank_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        mTopTitle.setText(getString(R.string.music_rank));
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
        mPresenter.requestRankDetail(this, mRankInfo.getType(), offset, 20, showLoading);
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
                        .imageView(mHeaderBackground)
                        .build());
    }

    private void initListener() {
        mTopBack.setOnClickListener(v -> finish());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollY = 0;
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollY += dy;
                if (scrollY > 0) {
                    mTopTitle.setText(mRankInfo.getName());
                } else {
                    mTopTitle.setText(getString(R.string.music_rank));
                }
            }
        });
    }

    @Override
    public void getRankDetailSuccess(RankInfo rankInfo) {
        if (isRefresh) {
            isRefresh = false;
            mUpdateTime.setText(getString(R.string.music_latest_update) + rankInfo.getUpdate_date());
            mNum.setText(getString(R.string.music_song_num, rankInfo.getBillboard_songnum()!=null ? Integer.parseInt(rankInfo.getBillboard_songnum()) : 100));
        } else {
            if (StringUtil.isEmpty(rankInfo.getBillboard_songnum()) || mAdapter.getData().size() < Integer.parseInt(rankInfo.getBillboard_songnum())) {
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