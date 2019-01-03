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
package com.watson.pureenjoy.news.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.news.http.entity.NewsPhotoSet;

import java.util.Map;

import io.reactivex.Observable;

public interface NewsPhotoSetContract {
    interface View extends IView {
        void setNewsPhotoSet(NewsPhotoSet newsPhotoSet);
    }

    interface Model extends IModel{
        Observable<Map<String, NewsPhotoSet>> getNewsPhotoSet(String photoId);
    }
}
