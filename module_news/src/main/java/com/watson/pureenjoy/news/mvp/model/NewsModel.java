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

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import me.jessyan.armscomponent.commonsdk.utils.FileUtil;
import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.http.entity.ChannelItem;
import com.watson.pureenjoy.news.mvp.contract.NewsContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class NewsModel extends BaseModel implements NewsContract.Model {

    @Inject
    public NewsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<ChannelItem>> getChannels() {
        return Observable.create(emitter -> {
            try {
                if (!FileUtil.isAssetsFileExist(mRepositoryManager.getContext(), NewsConstants.CHANNEL_FILE)) {
                    emitter.onError(new IllegalStateException("channel file is null"));
                    return;
                }
                String json = FileUtil.getStringFromAssetFile(mRepositoryManager.getContext(), NewsConstants.CHANNEL_FILE);
                List<ChannelItem> channels = JSON.parseArray(json, ChannelItem.class);
                emitter.onNext(channels);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
