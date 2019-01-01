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
import com.jess.arms.utils.ArmsUtils;
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
import me.jessyan.armscomponent.commonres.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonres.dialog.ProgressDialog;
import me.jessyan.armscomponent.commonres.view.CustomLoadingMoreView;
import me.jessyan.armscomponent.commonres.view.DividerItemDecoration;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.news.app.NewsConstants.POST_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.TYPE_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.TYPE_NAME;
import static com.watson.pureenjoy.news.app.NewsConstants.URL;

public class NewsListFragment extends BaseSupportFragment<NewsListPresenter> implements NewsListContract.View,
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
    @Inject
    ProgressDialog loadingDialog;

    private boolean isRefresh;
    private String typeId;
    private String name;

    public static NewsListFragment getIns(String typeId, String name) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_ID, typeId);
        bundle.putString(TYPE_NAME, name);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            typeId = bundle.getString(TYPE_ID);
            name = bundle.getString(TYPE_NAME);
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
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(onScrollListener);
        adapter.setOnLoadMoreListener(this, mRecyclerView);
        adapter.setLoadMoreView(new CustomLoadingMoreView());
        mSmartRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            NewsItem item = (NewsItem)adapter.getItem(position);
            if (NewsConstants.SPECIAL_TITLE.equals(item.getSkipType())) {

            } else if (NewsConstants.PHOTO_SET.equals(item.getSkipType())){

            } else {
                ARouter.getInstance().build(RouterHub.NEWS_DETAILACTIVITY)
                        .withString(POST_ID, item.getPostid())
                        .withString(URL, item.getUrl())
                        .navigation();
            }
        });
        getData(0);
    }

    private void getData(int offset) {
        if (offset == 0) {
            isRefresh = true;
        }
        mPresenter.requestNewsList(typeId, offset, 10);
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
        getData(0);
    }

    @Override
    public void onLoadMoreRequested() {
        getData(adapter.getData().size());
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
    public void showMessage(@NonNull String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void showLoading() {
//        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
//        loadingDialog.hide();
    }


}
