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
import com.watson.pureenjoy.music.mvp.contract.MusicRecommendSongContract;
import com.watson.pureenjoy.music.mvp.model.MusicRecommendSongModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRecommendSongAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/25/2019
 * ================================================
 */
@Module
public class MusicRecommendSongModule {

    @ActivityScope
    @Provides
    public MusicRecommendSongContract.Model provideMusicRecommendSongModel(IRepositoryManager iRepositoryManager) {
        return new MusicRecommendSongModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public RecyclerView.LayoutManager provideLayoutManager(MusicRecommendSongContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    public MusicRecommendSongAdapter provideRecommendSongAdapter(MusicRecommendSongContract.View view) {
        return new MusicRecommendSongAdapter(view.getContext(), R.layout.music_recommend_song_list_item, new ArrayList<>());
    }
}
