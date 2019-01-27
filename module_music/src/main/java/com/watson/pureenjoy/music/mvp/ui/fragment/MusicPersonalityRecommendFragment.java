package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicPersonalityRecommendComponent;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendItem;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendListInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicPersonalityRecommendContract;
import com.watson.pureenjoy.music.mvp.presenter.MusicPersonalityRecommendPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRecommendAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.music.app.MusicConstants.SHEET_INFO;

@Route(path = RouterHub.MUSIC_PERSONALITY_RECOMMEND_FRAGMENT)
public class MusicPersonalityRecommendFragment extends MusicBaseFragment<MusicPersonalityRecommendPresenter> implements MusicPersonalityRecommendContract.View {
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    MusicRecommendAdapter mAdapter;
    @Inject
    GridLayoutManager mGridLayoutManager;

    private boolean isRefresh;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMusicPersonalityRecommendComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.music_fragment_personality_recommend, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        initListener();
        mPresenter.requestRecommendResponse(getContext());
    }

    private void initListener() {
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPresenter.requestRecommendResponse(getContext());
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_fm) {

            } else if (view.getId() == R.id.tv_recommend) {

            } else if (view.getId() == R.id.iv_songSheet) {
                ARouter.getInstance().build(RouterHub.MUSIC_SONG_SHEET_ACTIVITY).navigation(getContext());
            } else if (view.getId() == R.id.iv_rank) {
                ARouter.getInstance().build(RouterHub.MUSIC_RANK_ACTIVITY).navigation(getContext());
            } else if (view.getId() == R.id.tv_title) {
                String title = ((TextView)view).getText().toString();
                if (title.equals(getString(R.string.music_recommend_list))) {
                    ARouter.getInstance().build(RouterHub.MUSIC_SONG_SHEET_ACTIVITY).navigation(getContext());
                } else if (title.equals(getString(R.string.music_recommend_album))) {

                } else if (title.equals(getString(R.string.music_recommend_radio))) {

                }
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemViewType(position) == RecommendItem.TYPE_RECOMMEND_SONG) {
                RecommendListInfo mRecommendListInfo = ((RecommendItem)adapter.getData().get(position)).getRecommendListInfo();
                SheetInfo mSheetInfo = new SheetInfo();
                mSheetInfo.setTitle(mRecommendListInfo.getTitle());
                mSheetInfo.setListid(mRecommendListInfo.getListid());
                mSheetInfo.setListenum(mRecommendListInfo.getListenum());
                mSheetInfo.setPic(mRecommendListInfo.getPic());
                ARouter.getInstance().build(RouterHub.MUSIC_SHEET_DETAIL_ACTIVITY)
                        .withParcelable(SHEET_INFO, mSheetInfo)
                        .navigation();
            } else if (adapter.getItemViewType(position) == RecommendItem.TYPE_NEW_ALBUM) {

            } else if (adapter.getItemViewType(position) == RecommendItem.TYPE_ANCHOR_RADIO) {

            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void getRecommendResponseFinish() {
        if (isRefresh) {
            mSmartRefreshLayout.finishRefresh(true);
            isRefresh = false;
        }
    }

}
