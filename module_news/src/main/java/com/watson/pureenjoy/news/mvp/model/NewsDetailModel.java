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
package com.watson.pureenjoy.news.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.watson.pureenjoy.news.http.api.service.NewsService;
import com.watson.pureenjoy.news.http.entity.NewsDetail;
import com.watson.pureenjoy.news.mvp.contract.NewsDetailContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class NewsDetailModel extends BaseModel implements NewsDetailContract.Model {

    @Inject
    public NewsDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Map<String, NewsDetail>> getNewsDetail(String postId) {
        return mRepositoryManager
                .obtainRetrofitService(NewsService.class)
                .getNewsDetail(postId);
    }
}
