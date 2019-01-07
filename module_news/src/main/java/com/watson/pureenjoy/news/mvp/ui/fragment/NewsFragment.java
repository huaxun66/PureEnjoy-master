package com.watson.pureenjoy.news.mvp.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
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
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.di.component.DaggerNewsComponent;
import com.watson.pureenjoy.news.http.entity.ChannelItem;
import com.watson.pureenjoy.news.mvp.contract.NewsContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsPresenter;
import com.watson.pureenjoy.news.mvp.ui.activity.NewsChannelManagerActivity;
import com.watson.pureenjoy.news.mvp.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonres.dialog.ProgressDialog;
import me.jessyan.armscomponent.commonres.view.EasyTabBarTxtScroll;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

import static com.watson.pureenjoy.news.app.NewsConstants.CHANNEL_SELECTED;
import static com.watson.pureenjoy.news.app.NewsConstants.CLICK_TYPE_ID;

@Route(path = RouterHub.NEWS_FRAGMENT)
public class NewsFragment extends BaseSupportFragment<NewsPresenter> implements NewsContract.View {
    @BindView(R2.id.easyTabBar_fragment_news_tab)
    EasyTabBarTxtScroll mTabBar;
    @BindView(R2.id.iv_fragment_news_expend_list)
    ImageView mExpandImage;
    @BindView(R2.id.vp_fragment_news_display)
    ViewPager mViewPager;

    @Inject
    ProgressDialog loadingDialog;

    private List<ChannelItem> channels;
    private String selectId;
    private final int REQUEST_CODE = 100;

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
        initListener();
        mPresenter.requestSelectedChannels();
    }

    private void initListener() {
        mExpandImage.setOnClickListener(view -> {
            selectId = channels.get(mViewPager.getCurrentItem()).getTypeId();
            ObjectAnimator anim = ObjectAnimator.ofFloat(mExpandImage, "rotation", 0.0F, 360.0F).setDuration(300);
            anim.addListener(new Animator.AnimatorListener(){
                @Override
                public void onAnimationStart(Animator animator) {}
                @Override
                public void onAnimationCancel(Animator animator) {}
                @Override
                public void onAnimationRepeat(Animator animator) {}
                @Override
                public void onAnimationEnd(Animator animator) {
//                    ARouter.getInstance().build(RouterHub.NEWS_CHANNEL_MANAGER_ACTIVITY)
//                            .withParcelableArrayList(CHANNEL_SELECTED, (ArrayList<ChannelItem>) channels)
//                            .withTransition(R.anim.public_translate_bottom_to_center, R.anim.public_translate_center_to_top)
//                            .navigation(getActivity(), REQUEST_CODE);
                    Intent intent = new Intent(getContext(), NewsChannelManagerActivity.class);
                    intent.putParcelableArrayListExtra(CHANNEL_SELECTED, (ArrayList<ChannelItem>) channels);
                    startActivityForResult(intent, REQUEST_CODE);
                    getActivity().overridePendingTransition(R.anim.public_translate_bottom_to_center, R.anim.public_translate_center_to_top);
                }
            });
            anim.start();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    if (data.getParcelableArrayListExtra(CHANNEL_SELECTED) !=  null) {
                        channels = data.getParcelableArrayListExtra(CHANNEL_SELECTED);
                        initChannelLayout(channels);
                    }
                    String id = !StringUtil.isEmpty(data.getStringExtra(CLICK_TYPE_ID)) ? data.getStringExtra(CLICK_TYPE_ID) : selectId;
                    for (int i = 0; i < channels.size(); i++) {
                        if (id.equals(channels.get(i).getTypeId())) {
                            mTabBar.setClick(i);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void setSelectedChannels(List<ChannelItem> channels) {
        this.channels = channels;
        initChannelLayout(channels);
    }

    private void initChannelLayout(List<ChannelItem> channels) {
        List<String> titleList = new ArrayList<>();
        List<BaseFragment> fragmentList = new ArrayList<>();
        for (ChannelItem item : channels) {
            titleList.add(item.getName());
            fragmentList.add(NewsListFragment.getIns(item.getTypeId()));
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), titleList, fragmentList);
        mViewPager.setAdapter(adapter);
//        mViewPager.setOffscreenPageLimit(titleList.size());
        mTabBar.setViewPager(mViewPager);
    }

}
