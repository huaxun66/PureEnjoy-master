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
import com.watson.pureenjoy.music.mvp.contract.MusicAlbumDetailContract;
import com.watson.pureenjoy.music.mvp.contract.MusicSheetDetailContract;
import com.watson.pureenjoy.music.mvp.model.MusicAlbumDetailModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicAlbumDetailAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/31/2019
 * ================================================
 */
@Module
public class MusicAlbumDetailModule {

    @ActivityScope
    @Provides
    public MusicAlbumDetailContract.Model provideMusicAlbumDetailModel(IRepositoryManager iRepositoryManager) {
        return new MusicAlbumDetailModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public RecyclerView.LayoutManager provideLayoutManager(MusicAlbumDetailContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    public MusicAlbumDetailAdapter provideAlbumDetailAdapter(MusicAlbumDetailContract.View view) {
        return new MusicAlbumDetailAdapter(view.getContext(), R.layout.music_album_detail_item, new ArrayList<>());
    }
}
