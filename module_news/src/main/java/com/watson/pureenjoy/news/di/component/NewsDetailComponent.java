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
import com.watson.pureenjoy.news.di.module.NewsDetailModule;
import com.watson.pureenjoy.news.mvp.contract.NewsDetailContract;
import com.watson.pureenjoy.news.mvp.ui.activity.NewsDetailActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = NewsDetailModule.class, dependencies = AppComponent.class)
public interface NewsDetailComponent {
    void inject(NewsDetailActivity activity);
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder view(NewsDetailContract.View view);
        Builder appComponent(AppComponent appComponent);
        NewsDetailComponent build();
    }
}
