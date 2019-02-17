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
import com.watson.pureenjoy.music.http.entity.sheet.SheetResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicSongSheetContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.EvictDynamicKey;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

@ActivityScope
public class MusicSongSheetModel extends BaseModel implements MusicSongSheetContract.Model {

    @Inject
    public MusicSongSheetModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<SheetResponse> getSongSheetList(int pageNo, int pageSize) {
        return mRepositoryManager
                .obtainCacheService(MusicCache.class)
                .getSongSheetResponse(getSongSheetListFromNet(pageNo, pageSize), new DynamicKey(pageNo), new EvictDynamicKey(false));
    }

    @Override
    public Observable<SheetResponse> getSongSheetListFromNet(int pageNo, int pageSize) {
        return mRepositoryManager
                .obtainRetrofitService(MusicService.class)
                .getSongSheetResponse(MusicConstants.ANDROID,
                        MusicConstants.VERSION,
                        MusicConstants.JSON,
                        MusicConstants.METHOD_SHEET,
                        pageSize,
                        ++pageNo); //页码从1开始
    }

    @Override
    public Observable<SheetResponse> getSongSheetListByTag(String tag, int pageNo, int pageSize) {
        return mRepositoryManager
                .obtainCacheService(MusicCache.class)
                .getSongSheetResponseByTag(getSongSheetListByTagFromNet(tag, pageNo, pageSize), new DynamicKeyGroup(tag, pageNo),  new EvictDynamicKey(false));
    }

    @Override
    public Observable<SheetResponse> getSongSheetListByTagFromNet(String tag, int pageNo, int pageSize) {
        return mRepositoryManager
                .obtainRetrofitService(MusicService.class)
                .getSongSheetResponseByTag(MusicConstants.ANDROID,
                        MusicConstants.VERSION,
                        MusicConstants.JSON,
                        MusicConstants.METHOD_SHEET_SEARCH,
                        pageSize,
                        ++pageNo, //页码从1开始
                        StringUtil.encode(tag));
    }

}
