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

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.album.AlbumInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicAlbumContract;
import com.watson.pureenjoy.music.mvp.model.MusicAlbumModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicAlbumAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.view.CustomLoadingMoreView;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/31/2019
 * ================================================
 */
@Module
public class MusicAlbumModule {

    @ActivityScope
    @Provides
    public MusicAlbumContract.Model provideMusicAlbumModel(IRepositoryManager iRepositoryManager) {
        return new MusicAlbumModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public GridLayoutManager provideLayoutManager(MusicAlbumContract.View view) {
        GridLayoutManager mGridLayoutManager =  new GridLayoutManager(view.getContext(), 2);
        return mGridLayoutManager;
    }

    @ActivityScope
    @Provides
    public List<AlbumInfo> provideAlbumList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    public MusicAlbumAdapter provideAlbumAdapter(MusicAlbumContract.View view) {
        return new MusicAlbumAdapter(view.getContext(), R.layout.music_album_item, new ArrayList<>());
    }

    @ActivityScope
    @Provides
    public CustomLoadingMoreView provideLoadingMoreView() {
        return new CustomLoadingMoreView();
    }

}
