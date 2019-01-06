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
import com.watson.pureenjoy.news.http.entity.NewsPhotoSet;
import com.watson.pureenjoy.news.mvp.contract.NewsPhotoSetContract;
import com.watson.pureenjoy.news.utils.StringUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class NewsPhotoSetPresenter extends BasePresenter<NewsPhotoSetContract.Model, NewsPhotoSetContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public NewsPhotoSetPresenter(NewsPhotoSetContract.Model model, NewsPhotoSetContract.View rootView) {
        super(model, rootView);
    }

    public void requestNewsPhotoSet(String photoId) {
        mModel.getNewsPhotoSet(StringUtil.clipPhotoSetId(photoId))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView)) //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NewsPhotoSet>(mErrorHandler) {
                    @Override
                    public void onNext(NewsPhotoSet newsPhotoSet) {
                        mRootView.setNewsPhotoSet(newsPhotoSet);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

}
