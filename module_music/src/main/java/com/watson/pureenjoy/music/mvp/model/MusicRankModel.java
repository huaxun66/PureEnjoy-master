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

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.api.cache.MusicCache;
import com.watson.pureenjoy.music.http.api.service.MusicService;
import com.watson.pureenjoy.music.http.entity.rank.RankResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicRankContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;

@ActivityScope
public class MusicRankModel extends BaseModel implements MusicRankContract.Model {

    @Inject
    public MusicRankModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<RankResponse> getMusicRank() {
        return mRepositoryManager
                .obtainCacheService(MusicCache.class)
                .getMusicRank(getMusicRankFromNet(), new EvictProvider(false));
    }

    @Override
    public Observable<RankResponse> getMusicRankFromNet() {
        return mRepositoryManager
                .obtainRetrofitService(MusicService.class)
                .getMusicRank(MusicConstants.ANDROID,
                        MusicConstants.VERSION,
                        MusicConstants.JSON,
                        MusicConstants.METHOD_RANK,
                        1);
    }

}
