package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicAlbumComponent;
import com.watson.pureenjoy.music.http.entity.album.AlbumInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicAlbumContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicAlbumPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicAlbumAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.CustomLoadingMoreView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.FastBlurUtil;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtil;

import static com.watson.pureenjoy.music.app.MusicConstants.ALBUM_INFO;

@Route(path = RouterHub.MUSIC_ALBUM_ACTIVITY)
public class MusicAlbumActivity extends MusicBaseActivity<MusicAlbumPresenter> implements MusicAlbumContract.View,
        OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R2.id.top_back)
    ImageView mTopBack;
    @BindView(R2.id.top_title)
    TextView mTopTitle;
    @BindView(R2.id.root)
    RelativeLayout mRoot;
    @BindView(R2.id.img)
    ImageView mHeaderImg;
    @BindView(R2.id.title)
    TextView mTitle;
    @BindView(R2.id.des)
    TextView mDes;
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    MusicAlbumAdapter mAdapter;
    @Inject
    CustomLoadingMoreView mCustomLoadingMoreView;

    private boolean isRefresh;

    private ImageLoader mImageLoader;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicAlbumComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        return R.layout.music_activity_album;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        mTopTitle.setText(getString(R.string.music_recommend_album));
        mAdapter.setLoadMoreView(mCustomLoadingMoreView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        initListener();
        getData(0, true);
    }

    private void initListener() {
        mTopBack.setOnClickListener(v -> finish());
        mSmartRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ARouter.getInstance().build(RouterHub.MUSIC_ALBUM_DETAIL_ACTIVITY)
                    .withParcelable(ALBUM_INFO, ((AlbumInfo)adapter.getData().get(position)))
                    .navigation();
        });
    }

    private void getData(int offset, boolean showLoading) {
        if (offset == 0) {
            isRefresh = true;
        }
        mPresenter.requestRecommendAlbum(this, offset, 10 , showLoading);
    }

    @Override
    public void setHeaderData(AlbumInfo info) {
        Glide.with(this)
                .asBitmap()
                .load(info.getPic_radio())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap bitmapBlur = FastBlurUtil.doBlur(resource, 25, true);
                        mRoot.setBackground(new BitmapDrawable(getResources(), bitmapBlur));
                    }
                });

        mImageLoader.loadImage(this,
                CommonImageConfigImpl
                        .builder()
                        .url(info.getPic_radio())
                        .fallback(R.drawable.music_placeholder)
                        .errorPic(R.drawable.music_placeholder)
                        .imageRadius(20)
                        .imageView(mHeaderImg)
                        .build());
        mTitle.setText(info.getTitle());
        mDes.setText(info.getInfo());
    }

    @Override
    public void getRecommendAlbumSuccess(boolean haveMore) {
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
        mAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onLoadMoreRequested() {
        getData(mAdapter.getData().size(), false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData(0, false);
    }

}