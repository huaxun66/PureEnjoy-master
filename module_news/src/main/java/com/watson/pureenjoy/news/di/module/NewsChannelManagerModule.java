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

import android.support.v7.widget.GridLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.watson.pureenjoy.news.mvp.contract.NewsChannelManagerContract;
import com.watson.pureenjoy.news.mvp.model.NewsChannelManagerModel;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsChannelManagerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/05/2019
 * ================================================
 */
@Module
public class NewsChannelManagerModule {

    @ActivityScope
    @Provides
    public GridLayoutManager provideLayoutManager(IRepositoryManager iRepositoryManager) {
        return new GridLayoutManager(iRepositoryManager.getContext(), 4);
    }

    @ActivityScope
    @Provides
    public NewsChannelManagerAdapter provideNewsChannelManagerAdapter() {
        return new NewsChannelManagerAdapter(new ArrayList<>(),false);
    }

    @ActivityScope
    @Provides
    public NewsChannelManagerContract.Model provideNewsChannelManagerModel(IRepositoryManager iRepositoryManager) {
        return new NewsChannelManagerModel(iRepositoryManager);
    }

}
