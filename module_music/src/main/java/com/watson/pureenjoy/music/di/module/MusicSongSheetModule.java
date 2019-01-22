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
import com.watson.pureenjoy.music.http.entity.sheet.SheetItem;
import com.watson.pureenjoy.music.mvp.contract.MusicSongSheetContract;
import com.watson.pureenjoy.music.mvp.model.MusicSongSheetModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSongSheetAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.view.CustomLoadingMoreView;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/05/2019
 * ================================================
 */
@Module
public class MusicSongSheetModule {

    @ActivityScope
    @Provides
    public MusicSongSheetContract.Model provideMusicSongSheetModel(IRepositoryManager iRepositoryManager) {
        return new MusicSongSheetModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public GridLayoutManager provideLayoutManager(MusicSongSheetContract.View view, MusicSongSheetAdapter mAdapter) {
        GridLayoutManager mGridLayoutManager =  new GridLayoutManager(view.getContext(), 2);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = mAdapter.getItemViewType(position);
                switch (type) {
                    case SheetItem.TYPE_SHEET_ITEM:
                        return 1;
                    default:
                        return 2;
                }
            }
        });
        return mGridLayoutManager;
    }

    @ActivityScope
    @Provides
    public List<SheetItem> provideSheetList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    public MusicSongSheetAdapter provideMusicSongSheetAdapter(MusicSongSheetContract.View view, List<SheetItem> list) {
        return new MusicSongSheetAdapter(view.getContext(), list);
    }

    @ActivityScope
    @Provides
    public CustomLoadingMoreView provideLoadingMoreView() {
        return new CustomLoadingMoreView();
    }

}
