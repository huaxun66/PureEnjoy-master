package com.watson.pureenjoy.music.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicSheetTagComponent;
import com.watson.pureenjoy.music.mvp.contract.MusicSheetTagContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicSheetTagPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSheetTagAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.TAG_SELECTED;

@Route(path = RouterHub.MUSIC_SHEET_TAG_ACTIVITY)
public class MusicSheetTagActivity extends MusicBaseActivity<MusicSheetTagPresenter> implements MusicSheetTagContract.View {
    @BindView(R2.id.top_bar)
    TopBar mTopBar;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    MusicSheetTagAdapter mAdapter;

    @Autowired(name = TAG_SELECTED)
    String selectedTag;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMusicSheetTagComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.music_activity_sheet_tag;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTopBar.setTitleColor(Color.WHITE);
        mTopBar.setLeftTextColor(Color.WHITE);
        mTopBar.setLeftTextClickListener(v -> finish());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initListener();
        mPresenter.requestSheetTagList(this, selectedTag);
    }

    private void initListener() {
        mTopBar.setLeftTextClickListener(v -> finishWithAnim());
        mAdapter.setOnTagClickListener(tag -> {
            if (!tag.equals(selectedTag)) {
                Intent intent = new Intent();
                intent.putExtra(TAG_SELECTED, tag);
                setResult(RESULT_OK, intent);
            }
            finishWithAnim();
        });
    }

    private void finishWithAnim() {
        finish();
        overridePendingTransition(R.anim.public_translate_top_to_center, R.anim.public_translate_center_to_bottom);
    }


    @Override
    public Context getContext() {
        return this;
    }
}