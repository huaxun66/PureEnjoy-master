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
import com.watson.pureenjoy.music.http.api.service.MusicService;
import com.watson.pureenjoy.music.http.entity.sheet.SheetDetailResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicSheetDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class MusicSheetDetailModel extends BaseModel implements MusicSheetDetailContract.Model {

    @Inject
    public MusicSheetDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<SheetDetailResponse> getSheetDetail(String listid) {
        return mRepositoryManager
                .obtainRetrofitService(MusicService.class)
                .getSheetDetailResponse(MusicConstants.ANDROID,
                        MusicConstants.VERSION,
                        MusicConstants.JSON,
                        MusicConstants.METHOD_SHEET_DETAIL,
                        listid);
    }
}