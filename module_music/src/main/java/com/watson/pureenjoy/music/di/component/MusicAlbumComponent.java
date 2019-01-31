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
import com.jess.arms.di.scope.ActivityScope;
import com.watson.pureenjoy.music.di.module.MusicAlbumModule;
import com.watson.pureenjoy.music.mvp.contract.MusicAlbumContract;
import com.watson.pureenjoy.music.mvp.ui.activity.MusicAlbumActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * 展示 Component 的用法
 *
 * Created by Watson on 01/31/2019
 * ================================================
 */

@ActivityScope
@Component(modules = MusicAlbumModule.class, dependencies = AppComponent.class)
public interface MusicAlbumComponent {
    void inject(MusicAlbumActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        MusicAlbumComponent.Builder view(MusicAlbumContract.View view);
        MusicAlbumComponent.Builder appComponent(AppComponent appComponent);
        MusicAlbumComponent build();
    }
}
