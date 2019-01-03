/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.watson.pureEnjoy.app.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;
import com.watson.pureenjoy.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

/**
 * ================================================
 * 宿主 App 的主页
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">ArmsComponent wiki 官方文档</a>
 * Created by JessYan on 19/04/2018 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseSupportActivity {
    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;

    private int[] normalIcon = {R.drawable.ic_navi_news, R.drawable.ic_navi_video, R.drawable.ic_navi_music};
    private int[] selectIcon = {R.drawable.ic_navi_news, R.drawable.ic_navi_video, R.drawable.ic_navi_music};
    private String[] tabText = new String[3];
    private List<Fragment> fragmentList = new ArrayList<>();
    private long mPressedTime;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //添加tab文字
        tabText[0] = getString(R.string.public_news);
        tabText[1] = getString(R.string.public_video);
        tabText[2] = getString(R.string.public_music);
        //添加fragment
        fragmentList.add((Fragment) ARouter.getInstance().build(RouterHub.NEWS_HOMEFRAGMENT).navigation());
        fragmentList.add((Fragment) ARouter.getInstance().build(RouterHub.VIDEO_HOMEFRAGMENT).navigation());
        fragmentList.add((Fragment) ARouter.getInstance().build(RouterHub.NEWS_HOMEFRAGMENT).navigation());
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragmentList)
                .fragmentManager(getSupportFragmentManager())
                .smoothScroll(true)   //点击Tab,Viewpager切换是否有动画
                .anim(Anim.ZoomIn)    //点击Tab时的动画
                .hasPadding(true)     //viewpager在导航栏之上，防止重叠
                .onTabClickListener((view, position) -> {
                    if (position == 1 || position == 2) {
                        ArmsUtils.snackbarText("developing...");
                    }
                    return false;
                })
                .build();

    }

    @Override
    public void onBackPressedSupport() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        //比较两次按键时间差
        if ((mNowTime - mPressedTime) > 2000) {
            ArmsUtils.makeText(getApplicationContext(), ArmsUtils.getString(getApplicationContext(), R.string.press_again_exit));
            mPressedTime = mNowTime;
        } else {
            super.onBackPressedSupport();
        }
    }

}
