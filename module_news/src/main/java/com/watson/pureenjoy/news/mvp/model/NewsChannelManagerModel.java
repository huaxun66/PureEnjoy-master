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
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.watson.pureenjoy.news.http.entity.ChannelItem;
import com.watson.pureenjoy.news.mvp.contract.NewsChannelManagerContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.utils.FileUtil;
import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

import static com.watson.pureenjoy.news.app.NewsConstants.CHANNEL_FILE;
import static com.watson.pureenjoy.news.app.NewsConstants.CHANNEL_SELECTED;


@ActivityScope
public class NewsChannelManagerModel extends BaseModel implements NewsChannelManagerContract.Model {

    @Inject
    public NewsChannelManagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<ChannelItem>> getAllChannels() {
        return Observable.create(emitter -> {
            try {
                if (!FileUtil.isAssetsFileExist(mRepositoryManager.getContext(), CHANNEL_FILE)) {
                    emitter.onError(new IllegalStateException("channel file is null"));
                    return;
                }
                String json = FileUtil.getStringFromAssetFile(mRepositoryManager.getContext(), CHANNEL_FILE);
                List<ChannelItem> channels = JSON.parseArray(json, ChannelItem.class);
                emitter.onNext(channels);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public void updateSelectedChannels(List<ChannelItem> selectedChannels) {
        if (selectedChannels != null && selectedChannels.size() > 0) {
            SharedPreferenceUtil.putListData(CHANNEL_SELECTED, selectedChannels);
        }
    }
}
