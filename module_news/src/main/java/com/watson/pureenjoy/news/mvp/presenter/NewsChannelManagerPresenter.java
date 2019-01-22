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
package com.watson.pureenjoy.news.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.watson.pureenjoy.news.http.entity.ChannelItem;
import com.watson.pureenjoy.news.mvp.contract.NewsChannelManagerContract;
import com.watson.pureenjoy.news.mvp.ui.adapter.NewsChannelManagerAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.watson.pureenjoy.news.app.NewsConstants.RECOMMEND_TYPE_ID;
import static com.watson.pureenjoy.news.http.entity.ChannelItem.TYPE_TITLE;

@ActivityScope
public class NewsChannelManagerPresenter extends BasePresenter<NewsChannelManagerContract.Model, NewsChannelManagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    List<ChannelItem> mDatas;
    @Inject
    NewsChannelManagerAdapter mAdapter;

    @Inject
    public NewsChannelManagerPresenter(NewsChannelManagerContract.Model model, NewsChannelManagerContract.View rootView) {
        super(model, rootView);
    }

    public void getRecommendChannels(List<ChannelItem> selectedChannels) {
        mModel.getAllChannels()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView)) //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<List<ChannelItem>>(mErrorHandler) {
                    @Override
                    public void onNext(List<ChannelItem> allChannels) {
                        mDatas.clear();
                        mDatas.addAll(selectedChannels);
                        mDatas.add(generateRecommendTitleItem());
                        allChannels.removeAll(selectedChannels);
                        mDatas.addAll(allChannels);
                        mAdapter.setNewData(mDatas);
                    }
                });
    }

    private ChannelItem generateRecommendTitleItem() {
        ChannelItem titleItem = new ChannelItem();
        titleItem.setType(TYPE_TITLE);
        titleItem.setTypeId(RECOMMEND_TYPE_ID);
        return titleItem;
    }

    public void updateSelectedChannels(List<ChannelItem> selectedChannels) {
         mModel.updateSelectedChannels(selectedChannels);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAdapter = null;
        this.mDatas = null;
    }
}
