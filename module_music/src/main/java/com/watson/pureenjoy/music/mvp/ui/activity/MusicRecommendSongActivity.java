package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicRecommendSongComponent;
import com.watson.pureenjoy.music.http.entity.recommendSong.RecommendSongInfo;
import com.watson.pureenjoy.music.http.entity.recommendSong.RecommendSongResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicRecommendSongContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicRecommendSongPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRecommendSongAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.FastBlurUtil;

@Route(path = RouterHub.MUSIC_RECOMMEND_SONG_ACTIVITY)
public class MusicRecommendSongActivity extends MusicBaseActivity<MusicRecommendSongPresenter> implements MusicRecommendSongContract.View {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.root)
    RelativeLayout mRoot;
    @BindView(R2.id.img)
    ImageView mHeaderImg;
    @BindView(R2.id.title)
    TextView mTitle;
    @BindView(R2.id.des)
    TextView mDescription;
    @BindView(R2.id.num)
    TextView mNum;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MusicRecommendSongAdapter mAdapter;

    private ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicRecommendSongComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_recommend_song;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        mTopBar.setTitleColor(Color.WHITE);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initListener();
        mPresenter.requestRecommendSong(this, 100);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
    }

    @Override
    public void setRecommendSong(RecommendSongResponse response) {
        List<RecommendSongInfo> list = response.getContent().get(0).getSong_list();
        Glide.with(this)
                .asBitmap()
                .load(list.get(0).getPic_premium())
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
                        .url(list.get(0).getPic_premium())
                        .fallback(R.drawable.music_placeholder)
                        .errorPic(R.drawable.music_placeholder)
                        .imageRadius(20)
                        .imageView(mHeaderImg)
                        .build());
        mTitle.setText(list.get(0).getTitle()+" - "+list.get(0).getAuthor());
        mDescription.setText(list.get(0).getRecommend_reason());
        mNum.setText(getString(R.string.music_song_num, list.size()));
        mAdapter.setNewData(list);
    }

    @Override
    public Context getContext() {
        return this;
    }

}