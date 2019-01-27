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
import com.watson.pureenjoy.music.mvp.contract.MusicHotSheetContract;
import com.watson.pureenjoy.music.mvp.model.MusicHotSheetModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicHotSheetAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.view.DividerItemDecoration;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/25/2019
 * ================================================
 */
@Module
public class MusicHotSheetModule {

    @ActivityScope
    @Provides
    public MusicHotSheetContract.Model provideMusicHotSheetModel(IRepositoryManager iRepositoryManager) {
        return new MusicHotSheetModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public RecyclerView.LayoutManager provideLayoutManager(MusicHotSheetContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    public MusicHotSheetAdapter provideHotSongSheetAdapter(MusicHotSheetContract.View view) {
        return new MusicHotSheetAdapter(view.getContext(), R.layout.music_sheet_hot_item, new ArrayList<>());
    }

    @ActivityScope
    @Provides
    public DividerItemDecoration provideItemDecoration(MusicHotSheetContract.View view) {
        return new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL_LIST);
    }

}
