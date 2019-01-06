package com.watson.pureenjoy.news.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.di.component.DaggerNewsPhotoSetComponent;
import com.watson.pureenjoy.news.http.entity.NewsPhotoSet;
import com.watson.pureenjoy.news.mvp.contract.NewsPhotoSetContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsPhotoSetPresenter;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsPhotoSetAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.PageTransformer.Transformers.AccordionTransformer;
import me.jessyan.armscomponent.commonres.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.news.app.NewsConstants.PHOTO_SET_ID;

@Route(path = RouterHub.NEWS_PHOTO_SET_ACTIVITY)
public class NewsPhotoSetActivity extends BaseSupportActivity<NewsPhotoSetPresenter> implements NewsPhotoSetContract.View {
    @BindView(R2.id.iv_back)
    ImageView mBack;
    @BindView(R2.id.tv_index)
    TextView mIndex;
    @BindView(R2.id.viewPager)
    ViewPager mViewPager;
    @BindView(R2.id.ll_photo_set_bottom)
    LinearLayout mBottomContainer;
    @BindView(R2.id.tv_photo_set_title)
    TextView mTitle;
    @BindView(R2.id.tv_photo_set_content)
    TextView mContent;

    @Inject
    NewsPhotoSetAdapter adapter;

    @Autowired(name = PHOTO_SET_ID)
    public String photoId;

    private List<NewsPhotoSet.PhotosEntity> photos;
    private boolean isHide = false;


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
        mBack.setOnClickListener(v -> finish());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mIndex.setText((position + 1) + "/" + photos.size());
                mContent.setText(photos.get(position).getNote());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter.setOnItemClickListener((view, position) -> {
            isHide = !isHide;
            if (isHide) {
                mBack.animate().translationY(-mBack.getBottom()).setDuration(300).start();
                mIndex.animate().translationY(-mIndex.getBottom()).setDuration(300).start();
                mBottomContainer.animate().translationY(mBottomContainer.getHeight()).setDuration(300).start();
            }else {
                mBack.animate().translationY(0).setDuration(300).start();
                mIndex.animate().translationY(0).setDuration(300).start();
                mBottomContainer.animate().translationY(0).setDuration(300).start();
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
    public void setNewsPhotoSet(NewsPhotoSet newsPhotoSet) {
        if (newsPhotoSet != null && newsPhotoSet.getPhotos() != null && newsPhotoSet.getPhotos().size() > 0) {
            photos = newsPhotoSet.getPhotos();
            List<String> imageList = new ArrayList<>();
            for (NewsPhotoSet.PhotosEntity item : photos) {
                imageList.add(item.getImgurl());
            }
            adapter.setImageList(imageList);
            mViewPager.setAdapter(adapter);
            mViewPager.setPageTransformer(true, new AccordionTransformer()); //动画
            mViewPager.setCurrentItem(0);
            mContent.setText(photos.get(0).getNote());
            mTitle.setText(newsPhotoSet.getSetname());
            mIndex.setText("1/" + photos.size());
        }
    }
}
