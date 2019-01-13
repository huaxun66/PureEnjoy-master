/*
 * Copyright 2017 JessYan
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
package com.watson.pureenjoy.music.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.watson.pureenjoy.music.mvp.contract.MusicPersonalityRecommendContract;
import com.watson.pureenjoy.music.mvp.model.MusicPersonalityRecommendModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRecommendAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.adapter.ImageViewPagerAdapter;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/05/2019
 * ================================================
 */
@Module
public class MusicPersonalityRecommendModule {

    @FragmentScope
    @Provides
    public MusicPersonalityRecommendContract.Model provideMusicPersonalityRecommendModel(IRepositoryManager iRepositoryManager) {
        return new MusicPersonalityRecommendModel(iRepositoryManager);
    }

    @FragmentScope
    @Provides
    public GridLayoutManager provideLayoutManager(IRepositoryManager iRepositoryManager) {
        return new GridLayoutManager(iRepositoryManager.getContext(), 3);
    }

    @FragmentScope
    @Provides
    public MusicRecommendAdapter provideNewsListAdapter(IRepositoryManager iRepositoryManager) {
        return new MusicRecommendAdapter(iRepositoryManager.getContext(), new ArrayList<>());
    }

}
