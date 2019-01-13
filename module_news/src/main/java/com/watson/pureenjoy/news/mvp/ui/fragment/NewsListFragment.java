package com.watson.pureenjoy.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.di.component.DaggerNewsListComponent;
import com.watson.pureenjoy.news.http.entity.NewsItem;
import com.watson.pureenjoy.news.mvp.contract.NewsListContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsListPresenter;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.CustomLoadingMoreView;
import me.jessyan.armscomponent.commonres.view.DividerItemDecoration;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.news.app.NewsConstants.PHOTO_SET_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.POST_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.SPECIAL_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.TYPE_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.URL;

public class NewsListFragment extends NewsBaseFragment<NewsListPresenter> implements NewsListContract.View,
        OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    NewsListAdapter adapter;
    @Inject
    List<NewsItem> allData;

    private boolean isRefresh;
    private String typeId;

    public static NewsListFragment getIns(String typeId) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_ID, typeId);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            typeId = bundle.getString(TYPE_ID);
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewsListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.news_fragment_news_list, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        adapter.setLoadMoreView(new CustomLoadingMoreView());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(adapter);
        initListener();
        getData(0, true);
    }

    private void initListener() {
        mRecyclerView.addOnScrollListener(onScrollListener);
        mSmartRefreshLayout.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, mRecyclerView);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            NewsItem item = (NewsItem)adapter.getItem(position);
            if (NewsConstants.SPECIAL.equals(item.getSkipType())) {
                ARouter.getInstance().build(RouterHub.NEWS_SPECIAL_ACTIVITY)
                        .withString(SPECIAL_ID, item.getSpecialID())
                        .navigation();
            } else if (NewsConstants.PHOTO_SET.equals(item.getSkipType())){
                ARouter.getInstance().build(RouterHub.NEWS_PHOTO_SET_ACTIVITY)
                        .withString(PHOTO_SET_ID, item.getPhotosetID())
                        .navigation();
            } else {
                ARouter.getInstance().build(RouterHub.NEWS_DETAIL_ACTIVITY)
                        .withString(POST_ID, item.getPostid())
                        .withString(URL, item.getUrl())
                        .navigation();
            }
        });
    }

    private void getData(int offset, boolean showLoading) {
        if (offset == 0) {
            isRefresh = true;
        }
        mPresenter.requestNewsList(typeId, offset, 10, showLoading);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void setNewsList(List<NewsItem> list) {
        if (isRefresh) {
            mSmartRefreshLayout.finishRefresh(true);
            allData.clear();
            isRefresh = false;
        } else {
            int count = list.size();
            if (count < 10) {
                adapter.loadMoreEnd();
            } else {
                adapter.loadMoreComplete();
            }
        }
        allData.addAll(list);
        adapter.setNewData(allData);
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData(0, false);
    }

    @Override
    public void onLoadMoreRequested() {
        getData(adapter.getData().size(), false);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                    try {
                        if (mContext != null) Glide.with(mContext).resumeRequests();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                    break;
                default:
                    try {
                        if (mContext != null) Glide.with(mContext).pauseRequests();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                    break;
            }
        }
    };


    @Override
    public void onDetach() {
        super.onDetach();
        adapter.onDetach();
    }



}
