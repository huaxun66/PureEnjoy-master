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
package com.watson.pureenjoy.gold.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.watson.pureenjoy.gold.mvp.contract.GoldHomeContract;
import com.watson.pureenjoy.gold.mvp.ui.activity.GoldHomeActivity;

import dagger.BindsInstance;
import dagger.Component;
import com.watson.pureenjoy.gold.di.module.GoldHomeModule;

/**
 * ================================================
 * 展示 Component 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.6">Component wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GoldHomeModule.class, dependencies = AppComponent.class)
public interface GoldHomeComponent {
    void inject(GoldHomeActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        GoldHomeComponent.Builder view(GoldHomeContract.View view);
        GoldHomeComponent.Builder appComponent(AppComponent appComponent);
        GoldHomeComponent build();
    }
}
