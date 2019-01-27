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
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicSheetDetailComponent;
import com.watson.pureenjoy.music.http.entity.sheet.SheetDetailResponse;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicSheetDetailContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicSheetDetailPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSheetDetailAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

import static com.watson.pureenjoy.music.app.MusicConstants.SHEET_INFO;

@Route(path = RouterHub.MUSIC_SHEET_DETAIL_ACTIVITY)
public class MusicSheetDetailActivity extends MusicBaseActivity<MusicSheetDetailPresenter> implements MusicSheetDetailContract.View {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.background)
    ImageView mHeaderBackground;
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
        return R.layout.music_activity_sheet_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        mTopBar.setTitleColor(Color.WHITE);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setHeaderData();
        initListener();
        mPresenter.requestSheetDetail(this, mSheetInfo.getListid());
    }

    private void setHeaderData() {
        String imgUrl = StringUtil.isEmpty(mSheetInfo.getPic()) ? mSheetInfo.getPic_300() : mSheetInfo.getPic();
        mImageLoader.loadImage(this,
                CommonImageConfigImpl
                        .builder()
                        .blurValue(25)
                        .url(imgUrl)
                        .fallback(R.drawable.music_hot_sheet_bg)
                        .errorPic(R.drawable.music_hot_sheet_bg)
                        .imageView(mHeaderBackground)
                        .build());

        mImageLoader.loadImage(this,
                CommonImageConfigImpl
                        .builder()
                        .url(imgUrl)
                        .imageRadius(20)
                        .imageView(mHeaderImg)
                        .build());
        int count = Integer.parseInt(mSheetInfo.getListenum());
        String countText = count > 10000 ? count / 10000 + "万" : count + "";
        mCount.setText(countText);
        mTitle.setText(mSheetInfo.getTitle());
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
    }

    @Override
    public void setSheetDetailInfo(SheetDetailResponse response) {
        mDescription.setText(response.getDesc());
        mNum.setText(getString(R.string.music_song_num, response.getContent().size()));
        mCollection.setText(getString(R.string.music_collection_num, response.getCollectnum()));
        mAdapter.setNewData(response.getContent());
    }

    @Override
    public Context getContext() {
        return this;
    }

}