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
package com.watson.pureenjoy.news.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.watson.pureenjoy.news.mvp.contract.NewsPhotoSetContract;
import com.watson.pureenjoy.news.mvp.model.NewsPhotoSetModel;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsPhotoSetAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/05/2019
 * ================================================
 */
@Module
public class NewsPhotoSetModule {

    @ActivityScope
    @Provides
    public NewsPhotoSetAdapter provideNewsPhotoSetAdapter(NewsPhotoSetContract.View view) {
        return new NewsPhotoSetAdapter(view.getContext());
    }

    @ActivityScope
    @Provides
    public NewsPhotoSetContract.Model provideNewsPhotoSetModel(IRepositoryManager iRepositoryManager) {
        return new NewsPhotoSetModel(iRepositoryManager);
    }


}
