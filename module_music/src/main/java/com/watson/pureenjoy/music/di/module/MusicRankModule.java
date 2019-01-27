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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.mvp.contract.MusicRankContract;
import com.watson.pureenjoy.music.mvp.model.MusicRankModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRankAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/26/2019
 * ================================================
 */
@Module
public class MusicRankModule {

    @ActivityScope
    @Provides
    public MusicRankContract.Model provideMusicRankModel(IRepositoryManager iRepositoryManager) {
        return new MusicRankModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public RecyclerView.LayoutManager provideLayoutManager(MusicRankContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    public MusicRankAdapter provideMusicRankAdapter(MusicRankContract.View view) {
        return new MusicRankAdapter(view.getContext(), R.layout.music_rank_item, new ArrayList<>());
    }

}
