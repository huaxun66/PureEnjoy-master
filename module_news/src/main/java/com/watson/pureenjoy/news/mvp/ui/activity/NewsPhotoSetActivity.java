package com.watson.pureenjoy.news.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.di.component.DaggerNewsPhotoSetComponent;
import com.watson.pureenjoy.news.http.entity.NewsPhotoSet;
import com.watson.pureenjoy.news.mvp.contract.NewsPhotoSetContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsPhotoSetPresenter;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsSpecialAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonres.view.flowlayout.TagFlowLayout;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.news.app.NewsConstants.PHOTO_SET_ID;

@Route(path = RouterHub.NEWS_PHOTOSETCTIVITY)
public class NewsPhotoSetActivity extends BaseSupportActivity<NewsPhotoSetPresenter> implements NewsPhotoSetContract.View {
    @BindView(R2.id.topBar)
    TopBar mTopBar;
    @BindView(R2.id.viewPager)
    ViewPager mViewPager;
    @BindView(R2.id.ll_photo_set_bottom)
    LinearLayout mPhotoSetBottom;
    @BindView(R2.id.tv_photo_set_title)
    TextView mTitle;
    @BindView(R2.id.tv_photo_set_content)
    TextView mContent;

    @Autowired(name = PHOTO_SET_ID)
    public String photoId;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewsPhotoSetComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_news_photo_set;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
        mPresenter.requestNewsPhotoSet(photoId);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> finish());

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
    public void setNewsPhotoSet(NewsPhotoSet newsPhotoSet) {
        ArmsUtils.snackbarText(newsPhotoSet.toString());
    }
}
