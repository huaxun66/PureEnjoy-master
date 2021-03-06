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
package com.watson.pureenjoy.news.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.watson.pureenjoy.news.di.module.NewsModule;
import com.watson.pureenjoy.news.mvp.contract.NewsContract;
import com.watson.pureenjoy.news.mvp.ui.fragment.NewsFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * 展示 Component 的用法
 *
 * Created by Watson on 01/05/2019
 * ================================================
 */

@FragmentScope
@Component(modules = NewsModule.class, dependencies = AppComponent.class)
public interface NewsComponent {
    void inject(NewsFragment fragment);
    @Component.Builder
    interface Builder{
        @BindsInstance
        NewsComponent.Builder view(NewsContract.View view);
        NewsComponent.Builder appComponent(AppComponent appComponent);
        NewsComponent build();
    }
}
