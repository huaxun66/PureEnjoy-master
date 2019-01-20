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
package com.watson.pureenjoy.music.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.watson.pureenjoy.music.di.module.MusicPersonalityRecommendModule;
import com.watson.pureenjoy.music.mvp.contract.MusicPersonalityRecommendContract;
import com.watson.pureenjoy.music.mvp.ui.fragment.MusicPersonalityRecommendFragment;

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
@Component(modules = MusicPersonalityRecommendModule.class, dependencies = AppComponent.class)
public interface MusicPersonalityRecommendComponent {
    void inject(MusicPersonalityRecommendFragment fragment);
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder view(MusicPersonalityRecommendContract.View view);
        Builder appComponent(AppComponent appComponent);
        MusicPersonalityRecommendComponent build();
    }
}
