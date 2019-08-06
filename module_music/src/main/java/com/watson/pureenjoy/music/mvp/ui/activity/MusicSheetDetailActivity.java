package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.di.component.DaggerMusicSheetDetailComponent;
import com.watson.pureenjoy.music.event.SheetRefreshEvent;
import com.watson.pureenjoy.music.http.entity.sheet.SheetDetailInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetDetailResponse;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.http.entity.song.SongResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicSheetDetailContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicSheetDetailPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSheetDetailAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.FastBlurUtil;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtil;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

import static com.watson.pureenjoy.music.app.MusicConstants.SHEET_INFO;

@Route(path = RouterHub.MUSIC_SHEET_DETAIL_ACTIVITY)
public class MusicSheetDetailActivity extends MusicBaseActivity<MusicSheetDetailPresenter> implements MusicSheetDetailContract.View {
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
    @BindView(R2.id.num)
    TextView mNum;
    @BindView(R2.id.collection)
    TextView mCollection;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MusicSheetDetailAdapter mAdapter;

    @Autowired(name = SHEET_INFO)
    SheetInfo mSheetInfo;

    private ImageLoader mImageLoader;
    private DBManager dbManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicSheetDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        return R.layout.music_activity_sheet_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        dbManager = DBManager.getInstance(this);
        mTopTitle.setText(getString(R.string.music_song_sheet));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setHeaderData();
        initListener();
        mPresenter.requestSheetDetail(this, mSheetInfo.getListid());
    }

    private void setHeaderData() {
        String imgUrl = StringUtil.isEmpty(mSheetInfo.getPic()) ? mSheetInfo.getPic_300() : mSheetInfo.getPic();
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
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
                        .url(imgUrl)
                        .fallback(R.drawable.music_placeholder)
                        .errorPic(R.drawable.music_placeholder)
                        .imageRadius(20)
                        .imageView(mHeaderImg)
                        .build());
        int count = Integer.parseInt(mSheetInfo.getListenum());
        String countText = count > 10000 ? count / 10000 + "万" : count + "";
        mCount.setText(countText);
        mTitle.setText(mSheetInfo.getTitle());
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
                        mTopTitle.setText(mSheetInfo.getTitle());
                    }
                //展开
                } else {
                    if (scrollY > 0) {
                        mTopTitle.setText(getString(R.string.music_song_sheet));
                    }
                }
                scrollY += dy;
            }
        });
        mCollection.setOnClickListener(v -> {
            if (dbManager.isSheetExist(mSheetInfo.getTitle())) {
                Toasty.error(this, getString(R.string.music_sheet_exist)).show();
            } else {
                mSheetInfo.setSongCount(String.valueOf(mAdapter.getItemCount()));
                dbManager.collectSheet(mSheetInfo);
                Toasty.info(this, R.string.music_collect_sheet_success).show();
                EventBusManager.getInstance().post(new SheetRefreshEvent());
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            String songId = ((SheetDetailInfo)adapter.getItem(position)).getSong_id();
            mPresenter.requestSongInfo(this, songId);
        });
    }

    @Override
    public void setSheetDetailInfo(SheetDetailResponse response) {
        mDescription.setText(response.getDesc());
        mNum.setText(getString(R.string.music_song_num, response.getContent().size()));
        mCollection.setText(getString(R.string.music_collection_num, response.getCollectnum()));
        mAdapter.setNewData(response.getContent());
    }

    @Override
    public void setSongInfo(SongResponse response) {

    }

    @Override
    public Context getContext() {
        return this;
    }

}