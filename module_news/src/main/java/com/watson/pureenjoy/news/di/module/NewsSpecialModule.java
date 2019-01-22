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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.watson.pureenjoy.news.mvp.contract.NewsSpecialContract;
import com.watson.pureenjoy.news.mvp.model.NewsSpecialModel;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsSpecialAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.view.DividerItemDecoration;

/**
 * ================================================
 * 展示 Module 的用法
 * Created by Watson on 01/05/2019
 * ================================================
 */
@Module
public class NewsSpecialModule {

    @ActivityScope
    @Provides
    public RecyclerView.LayoutManager provideLayoutManager(NewsSpecialContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    public NewsSpecialAdapter provideNewsSpecialAdapter(NewsSpecialContract.View view) {
        return new NewsSpecialAdapter(view.getContext(), new ArrayList<>());
    }

    @ActivityScope
    @Provides
    public NewsSpecialContract.Model provideNewsSpecialModel(IRepositoryManager iRepositoryManager) {
        return new NewsSpecialModel(iRepositoryManager);
    }

    @ActivityScope
    @Provides
    public DividerItemDecoration provideItemDecoration(NewsSpecialContract.View view) {
        return new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL_LIST);
    }


}
