package com.watson.pureenjoy.news.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.app.listener.AppBarStateChangeListener;
import com.watson.pureenjoy.news.di.component.DaggerNewsSpecialComponent;
import com.watson.pureenjoy.news.http.entity.NewsSpecialItem;
import com.watson.pureenjoy.news.mvp.contract.NewsSpecialContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsSpecialPresenter;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsSpecialAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.DividerItemDecoration;
import me.jessyan.armscomponent.commonres.view.flowlayout.FlowLayout;
import me.jessyan.armscomponent.commonres.view.flowlayout.TagAdapter;
import me.jessyan.armscomponent.commonres.view.flowlayout.TagFlowLayout;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtil;

import static com.watson.pureenjoy.news.app.NewsConstants.PHOTO_SET_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.POST_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.SPECIAL_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.URL;
import static me.jessyan.armscomponent.commonsdk.utils.StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA;

@Route(path = RouterHub.NEWS_SPECIAL_ACTIVITY)
public class NewsSpecialActivity extends NewsBaseActivity<NewsSpecialPresenter> implements NewsSpecialContract.View {
    @BindView(R2.id.abl_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R2.id.iv_bg)
    ImageView mTopBg;
    @BindView(R2.id.iv_back)
    ImageView mBack;
    @BindView(R2.id.tv_name)
    TextView toolBarTitle;
    @BindView(R2.id.tfl_tag)
    TagFlowLayout mTagFlowLayout;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @Autowired(name = SPECIAL_ID)
    public String specialId;

    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    NewsSpecialAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewsSpecialComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(NewsSpecialActivity.this,0,null);
        return R.layout.news_activity_news_special;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(adapter);
        mPresenter.requestNewsSpecial(specialId);
    }

    private void initListener() {
        mBack.setOnClickListener(v -> finish());
        adapter.setOnItemClickListener((adapter, view, position) -> {
            NewsSpecialItem item = (NewsSpecialItem) adapter.getItem(position);
            if (item.getItemType() == NewsSpecialItem.TYPE_PHOTO_SET) {
                ARouter.getInstance().build(RouterHub.NEWS_PHOTO_SET_ACTIVITY)
                        .withString(PHOTO_SET_ID, item.getEntity().getSkipID())
                        .navigation();
            } else if (item.getItemType() == NewsSpecialItem.TYPE_NORMAL) {
                //调用系统播放器
                if (item.getEntity().getVideoinfo() != null) {
                    String url = item.getEntity().getVideoinfo().getMp4_url();
                    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                    mediaIntent.setDataAndType(Uri.parse(url), mimeType);
                    startActivity(mediaIntent);
                } else {
                    ARouter.getInstance().build(RouterHub.NEWS_DETAIL_ACTIVITY)
                            .withString(POST_ID, item.getEntity().getPostid())
                            .withString(URL, item.getEntity().getUrl())
                            .navigation();
                }
            }
        });
        Drawable expandBack = getResources().getDrawable(R.drawable.public_nav_back_white);
        Drawable collBack = getResources().getDrawable(R.drawable.public_nav_back);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPAND) {//展开状态
                    toolBarTitle.setText("");
                    mBack.setImageDrawable(expandBack);
                    StatusBarUtil.setTranslucentForCoordinatorLayout(NewsSpecialActivity.this, DEFAULT_STATUS_BAR_ALPHA);
                } else if (state == State.COLLAPSED) {//折叠状态
                    toolBarTitle.setText(getString(R.string.news_special));
                    StatusBarUtil.setTransparent(NewsSpecialActivity.this);
                } else {//中间状态
                    toolBarTitle.setText(getString(R.string.news_special));
                    mBack.setImageDrawable(collBack);
                }
            }
        });
    }

    @Override
    public void setBanner(String imgUrl) {
        ImageLoader mImageLoader = ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        mImageLoader.loadImage(this,
                CommonImageConfigImpl
                        .builder()
                        .errorPic(R.drawable.news_image_bg)
                        .fallback(R.drawable.news_image_bg)
                        .url(imgUrl)
                        .imageView(mTopBg)
                        .build());
    }

    @Override
    public void setNewsSpecialList(List<NewsSpecialItem> specialList) {
        updateTag(specialList);
        adapter.setNewData(specialList);
    }


    private void updateTag(List<NewsSpecialItem> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) return;
            List<String> tagList = new ArrayList<>();
            for (NewsSpecialItem item : list) {
                if (item.getItemType() == NewsSpecialItem.TYPE_HEADER) {
                    tagList.add(item.getTitle());
                }
            }
            mTagFlowLayout.setAdapter(new TagAdapter<String>(tagList) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.news_special_tag_layout_item, null);
                    textView.setText(o);
                    return textView;
                }
            });
            mTagFlowLayout.setOnTagClickListener((view, position, parent) -> {
                String title = ((TextView) ((ViewGroup) view).getChildAt(0)).getText().toString().trim();
                int adapterPosition = adapter.getPositionFromTitle(title);
                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(adapterPosition, 0);
                return false;
            });
        }
    }
}
