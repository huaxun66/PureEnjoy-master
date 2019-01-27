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
package com.watson.pureenjoy.music.mvp.presenter;

import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetItem;
import com.watson.pureenjoy.music.http.entity.sheet.SheetResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicSongSheetContract;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSongSheetAdapter;

import java.util.List;

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
    MusicSongSheetAdapter mAdapter;
    @Inject
    List<SheetItem> allData;

    @Inject
    public MusicSongSheetPresenter(MusicSongSheetContract.Model model, MusicSongSheetContract.View rootView) {
        super(model, rootView);
    }

    public void requestSongSheetList(Context context, int pageNo, int pageSize, boolean showLoading) {
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
                        mRootView.getSongSheetListSuccess(response.getHavemore().equals("1"));
                        if (response.isSuccess()) {
                            setSheetData(pageNo, response.getContent());
                        } else {
                            mRootView.showMessage(ArmsUtils.getString(context, R.string.public_server_error));
                        }
                    }
                });
    }

    public void requestSongSheetListByTag(Context context, String tag, int pageNo, int pageSize, boolean showLoading) {
        mModel.getSongSheetListByTag(tag, pageNo, pageSize)
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
                        mRootView.getSongSheetListSuccess(response.getHavemore().equals("1"));
                        if (response.isSuccess()) {
                            setSheetData(pageNo, response.getContent());
                        } else {
                            mRootView.showMessage(ArmsUtils.getString(context, R.string.public_server_error));
                        }
                    }
                });
    }


    private void setSheetData(int pageNo, List<SheetInfo> content) {
        if (pageNo == 0) {
            allData.clear();
            allData.add(new SheetItem(SheetItem.TYPE_HOT_BANNER, content.get(0)));
            allData.add(new SheetItem());
        }
        for(SheetInfo info : content) {
            allData.add(new SheetItem(SheetItem.TYPE_SHEET_ITEM, info));
        }
        mAdapter.setNewData(allData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAdapter = null;
        this.allData = null;
    }
}
