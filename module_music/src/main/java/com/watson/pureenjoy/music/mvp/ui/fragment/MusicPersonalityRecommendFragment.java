package com.watson.pureenjoy.music.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.R2;
import com.watson.pureenjoy.music.di.component.DaggerMusicPersonalityRecommendComponent;
import com.watson.pureenjoy.music.http.entity.RecommendItem;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendAlbumInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendListInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendRadioInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResult;
import com.watson.pureenjoy.music.mvp.contract.MusicPersonalityRecommendContract;
import com.watson.pureenjoy.music.mvp.presentert.MusicPersonalityRecommendPresenter;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRecommendAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

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
        return LayoutInflater.from(getContext()).inflate(R.layout.music_personality_recommend_fragment, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = mAdapter.getItemViewType(position);
                switch (type) {
                    case RecommendItem.TYPE_BANNER: //Banner
                    case RecommendItem.TYPE_HEADER: //标题
                        return 3;
                    default:
                        return 1;
                }
            }
        });
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
            switch (view.getId()) {
                case R.id.iv_fm:
                    break;
                case R.id.tv_recommend:
                    break;
                case R.id.iv_songSheet:
                    ARouter.getInstance().build(RouterHub.MUSIC_SONG_SHEET_ACTIVITY).navigation(getContext());
                    break;
                case R.id.iv_rank:
                    break;
            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void setRecommendResult(RecommendResult recommendResult) {
        if (isRefresh) {
            mSmartRefreshLayout.finishRefresh(true);
            isRefresh = false;
        }
        setRecommendData(recommendResult);
    }

    private void setRecommendData(RecommendResult recommendResult) {
        ArrayList<RecommendItem> list = new ArrayList<>();
        if (recommendResult.getRecommendFocus().isSuccess()) {
            list.add(new RecommendItem(recommendResult.getRecommendFocus()));
        }
        if (recommendResult.getRecommendList().isSuccess()) {
            list.add(new RecommendItem(getString(R.string.music_recommend_list)));
            for(RecommendListInfo info : recommendResult.getRecommendList().getResult()) {
                list.add(new RecommendItem(info));
            }
        }
        if (recommendResult.getRecommendAlbum().isSuccess()) {
            list.add(new RecommendItem(getString(R.string.music_recommend_album)));
            for(RecommendAlbumInfo info : recommendResult.getRecommendAlbum().getResult()) {
                list.add(new RecommendItem(info));
            }
        }
        if (recommendResult.getRecommendRadio().isSuccess()) {
            list.add(new RecommendItem(getString(R.string.music_recommend_radio)));
            for(RecommendRadioInfo info : recommendResult.getRecommendRadio().getResult()) {
                list.add(new RecommendItem(info));
            }
        }
        mAdapter.setNewData(list);
    }
}
