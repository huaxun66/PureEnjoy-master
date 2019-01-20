package com.watson.pureenjoy.news.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.app.listener.OnRecyclerItemClickListener;
import com.watson.pureenjoy.news.di.component.DaggerNewsChannelManagerComponent;
import com.watson.pureenjoy.news.http.entity.ChannelItem;
import com.watson.pureenjoy.news.mvp.contract.NewsChannelManagerContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsChannelManagerPresenter;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsChannelManagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.ClickUtils;

import static com.watson.pureenjoy.news.app.NewsConstants.CHANNEL_SELECTED;
import static com.watson.pureenjoy.news.app.NewsConstants.CLICK_TYPE_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.RECOMMEND_TYPE_ID;
import static com.watson.pureenjoy.news.http.entity.ChannelItem.TYPE_TITLE;

/**
 * ================================================
 * Created by Watson on 01/05/2019
 * ================================================
 */

@Route(path = RouterHub.NEWS_CHANNEL_MANAGER_ACTIVITY)
public class NewsChannelManagerActivity extends NewsBaseActivity<NewsChannelManagerPresenter> implements NewsChannelManagerContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.finish_tv)
    TextView mFinish;
    @BindView(R2.id.edit_tv)
    TextView mEdit;
    @BindView(R2.id.close_rl)
    RelativeLayout mClose;
    @BindView(R2.id.scroll_view)
    ScrollView mScrollView;

    @Inject
    NewsChannelManagerAdapter mAdapter;
    @Inject
    GridLayoutManager mGridLayoutManager;

    @Autowired(name = CHANNEL_SELECTED)
    ArrayList<ChannelItem> selectedChannels;

    private boolean isEditState = false;
    private boolean hasChanged = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewsChannelManagerComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_channel_manager;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = mAdapter.getItemViewType(position);
                switch (type) {
                    case 1: //标题
                        return 4;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter.itemTouchHelper.attachToRecyclerView(mRecyclerView);
        initListener();
        mPresenter.getRecommendChannels(selectedChannels);
    }

    private void initListener() {
        mClose.setOnClickListener(v -> closePage());
        //点击编辑按钮进入编辑状态
        mEdit.setOnClickListener(v -> {
            isEditState = true;
            mFinish.setVisibility(View.VISIBLE);
            mEdit.setVisibility(View.GONE);
            mAdapter.setEdit(true);
            mAdapter.notifyDataSetChanged();
        });
        //点击完成按钮结束编辑
        mFinish.setOnClickListener(v -> {
            isEditState = false;
            mFinish.setVisibility(View.GONE);
            mEdit.setVisibility(View.VISIBLE);
            mAdapter.setEdit(false);
            mAdapter.notifyDataSetChanged();
        });
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                isEditState = true;
                mFinish.setVisibility(View.VISIBLE);
                mEdit.setVisibility(View.GONE);
                mAdapter.setEdit(true);
                //这里不能通过notifyDataSetChanged()刷新，否则导致长按拖拽失效
                ImageView icon;
                TextView name;
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    View item = mRecyclerView.getChildAt(i);
                    icon = item.findViewById(R.id.img_icon);
                    name = item.findViewById(R.id.tv_name);
                    if (mAdapter.getItemViewType(i) != TYPE_TITLE) {
                        if (i > mAdapter.getRecommendTitlePosition()) {
                            icon.setVisibility(View.GONE);
                        } else {
                            if (isAllowDragOrDelete(i)) {
                                icon.setVisibility(View.VISIBLE);
                                name.setBackground(getResources().getDrawable(R.drawable.news_shape_round_grey_bg));
                            } else {
                                icon.setVisibility(View.GONE);
                                name.setBackground(getResources().getDrawable(R.drawable.news_shape_round_white_bg));
                            }
                        }
                    }
                }
                int position = vh.getLayoutPosition();
                if (isSelectedChannel(position) && isAllowDragOrDelete(position)) {
                    mAdapter.itemTouchHelper.startDrag(vh);
                    hasChanged = true;
                }
            }

            @Override
            public void onScrollClick(RecyclerView.ViewHolder vh) {
                //执行拖拽
                int position = vh.getLayoutPosition();
                if (isSelectedChannel(position) && isAllowDragOrDelete(position) && isEditState) {
                    mAdapter.itemTouchHelper.startDrag(vh);
                    hasChanged = true;
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if (ClickUtils.isFastClick()) {
                    return;
                }
                int position = vh.getLayoutPosition();
                if (isSelectedChannel(position)) {
                    if (!isEditState || (isEditState && !isAllowDragOrDelete(position))) {
                        clickToChannel(mAdapter.getItem(position).getTypeId());
                    } else {
                        for (int i = position; i < mAdapter.getRecommendTitlePosition(); i++) {
                            Collections.swap(mAdapter.getData(), i, i + 1);
                        }
                        mAdapter.notifyDataSetChanged();
                        hasChanged = true;
                    }
                } else {
                    for (int i = position; i > mAdapter.getRecommendTitlePosition(); i--) {
                        Collections.swap(mAdapter.getData(), i, i - 1);
                    }
                    mAdapter.notifyDataSetChanged();
                    hasChanged = true;
                }
            }
        });
        mAdapter.setItemOnSelectListener(new NewsChannelManagerAdapter.ItemOnSelectListener() {
            public void onSelectedChanged() {
                mScrollView.setAlpha(0.5f);
            }

            public void clearView() {
                mScrollView.setAlpha(1.0f);
            }
        });
    }

    private boolean isSelectedChannel(int position) {
        return position < mAdapter.getRecommendTitlePosition();
    }

    @Override
    public void setRecommendChannels(List<ChannelItem> recommendChannels) {
        ArrayList<ChannelItem> allChannels = new ArrayList<>();
        allChannels.addAll(selectedChannels);
        allChannels.add(generateRecommendTitleItem());
        allChannels.addAll(recommendChannels);
        mAdapter.setNewData(allChannels);
    }

    private ChannelItem generateRecommendTitleItem() {
        ChannelItem titleItem = new ChannelItem();
        titleItem.setType(TYPE_TITLE);
        titleItem.setTypeId(RECOMMEND_TYPE_ID);
        return titleItem;
    }

    private boolean isAllowDragOrDelete(int position) {
        return position != 0;
    }

    /**
     * 关闭页面
     * 1. 栏目有变化，则需要更新，否则直接关闭
     */
    private void closePage(){
        if (hasChanged) {
            ArrayList<ChannelItem> selectedChannels = mAdapter.getSelectedChannels();
            mPresenter.updateSelectedChannels(selectedChannels);
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(CHANNEL_SELECTED, selectedChannels);
            setResult(RESULT_OK, intent);
        }
        finishWithAnim();
    }

    /**
     * 非编辑状态下，点击我的频道下的栏目直接跳转
     * 1. 栏目有变化，先更新再跳转，否则直接跳转
     */
    private void clickToChannel(String clickTypeId) {
        Intent intent = new Intent();
        if (hasChanged) {
            ArrayList<ChannelItem> selectedChannels = mAdapter.getSelectedChannels();
            mPresenter.updateSelectedChannels(selectedChannels);
            intent.putParcelableArrayListExtra(CHANNEL_SELECTED, selectedChannels);
        }
        intent.putExtra(CLICK_TYPE_ID, clickTypeId);
        setResult(RESULT_OK, intent);
        finishWithAnim();
    }

    private void finishWithAnim() {
        finish();
        overridePendingTransition(R.anim.public_translate_top_to_center, R.anim.public_translate_center_to_bottom);
    }

    @Override
    public void onBackPressed() {
        closePage();
    }


}
