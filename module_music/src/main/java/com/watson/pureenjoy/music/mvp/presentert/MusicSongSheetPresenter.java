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
package com.watson.pureenjoy.music.mvp.presentert;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.watson.pureenjoy.music.http.entity.sheet.SheetResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicSongSheetContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MusicSongSheetPresenter extends BasePresenter<MusicSongSheetContract.Model, MusicSongSheetContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MusicSongSheetPresenter(MusicSongSheetContract.Model model, MusicSongSheetContract.View rootView) {
        super(model, rootView);
    }

    public void requestSongSheetList(int pageNo, int pageSize, boolean showLoading) {
        mModel.getSongSheetList(pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (showLoading) mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SheetResponse>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SheetResponse response) {
                        if (response.isSuccess()) {
                            mRootView.setSongSheetList(response.getContent(), response.getHavemore().equals("1"));
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
