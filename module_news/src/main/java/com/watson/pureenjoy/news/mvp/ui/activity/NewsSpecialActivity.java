package com.watson.pureenjoy.news.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.di.component.DaggerNewsSpecialComponent;
import com.watson.pureenjoy.news.http.entity.NewsSpecialItem;
import com.watson.pureenjoy.news.mvp.contract.NewsSpecialContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsSpecialPresenter;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsSpecialAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonres.view.flowlayout.FlowLayout;
import me.jessyan.armscomponent.commonres.view.flowlayout.TagAdapter;
import me.jessyan.armscomponent.commonres.view.flowlayout.TagFlowLayout;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.news.app.NewsConstants.PHOTO_SET_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.POST_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.SPECIAL_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.URL;

@Route(path = RouterHub.NEWS_SPECIALACTIVITY)
public class NewsSpecialActivity extends BaseSupportActivity<NewsSpecialPresenter> implements NewsSpecialContract.View {
    @BindView(R2.id.topBar)
    TopBar mTopBar;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.iv_banner)
    ImageView mBanner;
    @BindView(R2.id.tfl_tag)
    TagFlowLayout mTagFlowLayout;

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
        return R.layout.news_activity_news_special;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mPresenter.requestNewsSpecial(specialId);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());
        adapter.setOnItemClickListener((adapter, view, position) -> {
            NewsSpecialItem item = (NewsSpecialItem)adapter.getItem(position);
            if (item.getItemType() == NewsSpecialItem.TYPE_PHOTO_SET) {
                ARouter.getInstance().build(RouterHub.NEWS_PHOTOSETCTIVITY)
                        .withString(PHOTO_SET_ID, item.getEntity().getSkipID())
                        .navigation();
            } else if (item.getItemType() == NewsSpecialItem.TYPE_NORMAL){
                //调用系统播放器
                if (item.getEntity().getVideoinfo()!=null) {
                    String url = item.getEntity().getVideoinfo().getMp4_url();
                    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                    mediaIntent.setDataAndType(Uri.parse(url), mimeType);
                    startActivity(mediaIntent);
                } else {
                    ARouter.getInstance().build(RouterHub.NEWS_DETAILACTIVITY)
                            .withString(POST_ID, item.getEntity().getPostid())
                            .withString(URL, item.getEntity().getUrl())
                            .navigation();
                }
            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void setBanner(String imgUrl) {
        Glide.with(this).load(imgUrl).into(mBanner);
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
                String title = ((TextView) ((ViewGroup)view).getChildAt(0)).getText().toString().trim();
                int adapterPosition = adapter.getPositionFromTitle(title);
                ((LinearLayoutManager)layoutManager).scrollToPositionWithOffset(adapterPosition,0);
                return false;
            });
        }
    }
}
