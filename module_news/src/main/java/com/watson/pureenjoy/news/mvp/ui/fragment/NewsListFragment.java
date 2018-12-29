package com.watson.pureenjoy.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
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

import static com.watson.pureenjoy.news.app.NewsConstants.TYPE_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.TYPE_NAME;

public class NewsListFragment extends BaseSupportFragment<NewsListPresenter> implements NewsListContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
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
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(onScrollListener);
        adapter.setOnLoadMoreListener(this, mRecyclerView);
        adapter.setLoadMoreView(new CustomLoadingMoreView());
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
        ArmsUtils.makeText(getContext(), "name:" + name+ " nums:" + list.size());
    }

    @Override
    public void onRefresh() {
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
