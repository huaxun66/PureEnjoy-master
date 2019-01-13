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
package com.watson.pureenjoy.music.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.watson.pureenjoy.music.http.api.service.MusicService;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicPersonalityRecommendContract;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.watson.pureenjoy.music.http.api.Api.MUSIC_RECOMMEND_URL;

@FragmentScope
public class MusicPersonalityRecommendModel extends BaseModel implements MusicPersonalityRecommendContract.Model {

    @Inject
    public MusicPersonalityRecommendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<RecommendResponse> getRecommendResponse() {
        return mRepositoryManager
                .obtainRetrofitService(MusicService.class)
                .getRecommendResponse(MUSIC_RECOMMEND_URL);
    }
}
