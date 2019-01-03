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
import com.watson.pureenjoy.news.http.entity.NewsSpecial;
import com.watson.pureenjoy.news.http.entity.NewsSpecialItem;
import com.watson.pureenjoy.news.mvp.contract.NewsSpecialContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class NewsSpecialPresenter extends BasePresenter<NewsSpecialContract.Model, NewsSpecialContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public NewsSpecialPresenter(NewsSpecialContract.Model model, NewsSpecialContract.View rootView) {
        super(model, rootView);
    }

    public void requestNewsSpecial(String specialId) {
        mModel.getNewsSpecial(specialId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView)) //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Map<String, NewsSpecial>>(mErrorHandler) {
                    @Override
                    public void onNext(Map<String, NewsSpecial> data) {
                        NewsSpecial newsSpecial = data.get(specialId);
                        mRootView.setBanner(newsSpecial.getBanner());
                        List<NewsSpecialItem> specialItemList = new ArrayList<>();
                        for(NewsSpecial.TopicsEntity topic : newsSpecial.getTopics()) {
                            specialItemList.add(new NewsSpecialItem(topic.getShortname()));
                            for(NewsSpecial.TopicsEntity.DocsEntity doc : topic.getDocs()) {
                                specialItemList.add(new NewsSpecialItem(doc));
                            }
                        }
                        mRootView.setNewsSpecialList(specialItemList);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
