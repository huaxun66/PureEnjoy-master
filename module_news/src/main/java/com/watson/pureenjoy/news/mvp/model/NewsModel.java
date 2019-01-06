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

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.watson.pureenjoy.news.http.entity.ChannelItem;
import com.watson.pureenjoy.news.mvp.contract.NewsContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

import static com.watson.pureenjoy.news.app.NewsConstants.CHANNEL_SELECTED;
import static com.watson.pureenjoy.news.app.NewsConstants.HEADLINE_NAME;
import static com.watson.pureenjoy.news.app.NewsConstants.HEADLINE_TYPE_ID;


@FragmentScope
public class NewsModel extends BaseModel implements NewsContract.Model {

    @Inject
    public NewsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<ChannelItem>> getSelectedChannels() {
        return Observable.create(emitter -> {
            List<ChannelItem> channels = SharedPreferenceUtil.getListData(CHANNEL_SELECTED, ChannelItem.class);
            if (channels == null || channels.size() == 0) {
                ChannelItem item = new ChannelItem();
                item.setName(HEADLINE_NAME);
                item.setTypeId(HEADLINE_TYPE_ID);
                item.setType(2);
                channels.add(item);
            }
            emitter.onNext(channels);
            emitter.onComplete();
        });
    }
}
