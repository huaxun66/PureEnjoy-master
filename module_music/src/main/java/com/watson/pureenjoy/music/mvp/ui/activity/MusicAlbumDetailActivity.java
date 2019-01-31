package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicAlbumDetailComponent;
import com.watson.pureenjoy.music.http.entity.album.AlbumDetailResponse;
import com.watson.pureenjoy.music.http.entity.album.AlbumInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicAlbumDetailContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicAlbumDetailPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicAlbumDetailAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.FastBlurUtil;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtil;

import static android.view.View.GONE;
import static com.watson.pureenjoy.music.app.MusicConstants.ALBUM_INFO;

@Route(path = RouterHub.MUSIC_ALBUM_DETAIL_ACTIVITY)
public class MusicAlbumDetailActivity extends MusicBaseActivity<MusicAlbumDetailPresenter> implements MusicAlbumDetailContract.View {
    @BindView(R2.id.top_bar)
    ConstraintLayout mTopBar;
    @BindView(R2.id.top_back)
    ImageView mTopBack;
    @BindView(R2.id.top_title)
    TextView mTopTitle;
    @BindView(R2.id.root)
    RelativeLayout mRoot;
    @BindView(R2.id.img)
    ImageView mHeaderImg;
    @BindView(R2.id.count)
    TextView mCount;
    @BindView(R2.id.title)
    TextView mTitle;
    @BindView(R2.id.des)
    TextView mDescription;
    @BindView(R2.id.comment)
    TextView mComment;
    @BindView(R2.id.share)
    TextView mShare;
    @BindView(R2.id.download)
    TextView mDownload;
    @BindView(R2.id.multiSelect)
    TextView mMultiSelect;
    @BindView(R2.id.num)
    TextView mNum;
    @BindView(R2.id.collection)
    TextView mCollection;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MusicAlbumDetailAdapter mAdapter;

    @Autowired(name = ALBUM_INFO)
    AlbumInfo mAlbumInfo;

    private ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicAlbumDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        return R.layout.music_activity_album_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        mTopTitle.setText(getString(R.string.music_recommend_album));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setHeaderData();
        initListener();
        mPresenter.requestAlbumDetail(this, mAlbumInfo.getAlbum_id());
    }

    private void setHeaderData() {
        Glide.with(this)
                .asBitmap()
                .load(mAlbumInfo.getPic_big())
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
                        .url(mAlbumInfo.getPic_big())
                        .fallback(R.drawable.music_placeholder)
                        .errorPic(R.drawable.music_placeholder)
                        .imageRadius(20)
                        .imageView(mHeaderImg)
                        .build());
        mCount.setVisibility(GONE);
        mTitle.setText(mAlbumInfo.getTitle());
        mDescription.setText(mAlbumInfo.getInfo());
    }

    private void initListener() {
        mTopBack.setOnClickListener(v -> finish());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollY = 0;
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //折叠
                if (scrollY + dy > 0) {
                    if (scrollY <= 0) {
                        mTopTitle.setFocusable(true);
                        mTopTitle.setFocusableInTouchMode(true);
                        mTopTitle.requestFocus();
                        mTopTitle.setText(mAlbumInfo.getTitle());
                    }
                //展开
                } else {
                    if (scrollY > 0) {
                        mTopTitle.setText(getString(R.string.music_recommend_album));
                    }
                }
                scrollY += dy;
            }
        });
    }


    @Override
    public void setAlbumDetailInfo(AlbumDetailResponse response) {
        mTitle.setText(response.getAlbumInfo().getTitle());
        mDescription.setText(response.getAlbumInfo().getInfo());
        mNum.setText(getString(R.string.music_song_num, response.getSonglist().size()));
        mCollection.setText(getString(R.string.music_collection_num, response.getAlbumInfo().getCollect_num()+""));
        mComment.setText(response.getAlbumInfo().getComment_num()+"");
        mShare.setText(response.getAlbumInfo().getShare_num()+"");
        mAdapter.setNewData(response.getSonglist());
    }

    @Override
    public Context getContext() {
        return this;
    }

}