package com.watson.pureenjoy.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.di.component.DaggerNewsComponent;
import com.watson.pureenjoy.news.http.entity.ChannelItem;
import com.watson.pureenjoy.news.mvp.contract.NewsContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsPresenter;
import com.watson.pureenjoy.news.mvp.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonres.dialog.ProgressDialog;
import me.jessyan.armscomponent.commonres.view.EasyTabBarTxtScroll;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.NEWS_HOMEFRAGMENT)
public class NewsFragment extends BaseSupportFragment<NewsPresenter> implements NewsContract.View {
    @BindView(R2.id.easyTabBar_fragment_news_tab)
    EasyTabBarTxtScroll mTabBar;
    @BindView(R2.id.iv_fragment_news_expend_list)
    ImageView mExpandImage;
    @BindView(R2.id.vp_fragment_news_display)
    ViewPager mViewPager;

    @Inject
    ProgressDialog loadingDialog;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.news_fragment_home, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.requestChannels();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void setChannels(List<ChannelItem> channels) {
        List<String> titleList = new ArrayList<>();
        List<BaseFragment> fragmentList = new ArrayList<>();
        for (ChannelItem item : channels) {
            titleList.add(item.getName());
            fragmentList.add(NewsListFragment.getIns(item.getTypeId(), item.getName()));
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), titleList, fragmentList);
        mViewPager.setAdapter(adapter);
//        mViewPager.setOffscreenPageLimit(titleList.size());
        mTabBar.setViewPager(mViewPager);
    }


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
