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

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagItem;
import com.watson.pureenjoy.music.mvp.contract.MusicSheetTagContract;
import com.watson.pureenjoy.music.mvp.model.MusicSheetTagModel;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSheetTagAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.view.DividerGridItemDecoration;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/05/2019
 * ================================================
 */
@Module
public class MusicSheetTagModule {

    @ActivityScope
    @Provides
    public MusicSheetTagContract.Model provideMusicSheetTagModel(IRepositoryManager iRepositoryManager) {
        return new MusicSheetTagModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public LinearLayoutManager provideLayoutManager(MusicSheetTagContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    public List<SheetTagItem> provideSheetTagList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    public MusicSheetTagAdapter provideMusicSheetTagAdapter(MusicSheetTagContract.View view, List<SheetTagItem> list) {
        return new MusicSheetTagAdapter(view.getContext(), list);
    }

    @ActivityScope
    @Provides
    public DividerGridItemDecoration provideGridItemDecoration(MusicSheetTagContract.View view) {
        return new DividerGridItemDecoration(view.getContext());
    }

}
