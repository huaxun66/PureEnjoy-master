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
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagItem;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicSheetTagContract;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicSheetTagAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MusicSheetTagPresenter extends BasePresenter<MusicSheetTagContract.Model, MusicSheetTagContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    MusicSheetTagAdapter mAdapter;
    @Inject
    List<SheetTagItem> allData;

    @Inject
    public MusicSheetTagPresenter(MusicSheetTagContract.Model model, MusicSheetTagContract.View rootView) {
        super(model, rootView);
    }

    public void requestSheetTagList(Context context, String selectedTag) {
        mModel.getSheetTag()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SheetTagResponse>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SheetTagResponse response) {
                        if (response.isSuccess()) {
                            setSheetTagData(response.getContent(), selectedTag);
                        } else {
                            mRootView.showMessage(ArmsUtils.getString(context, R.string.public_server_error));
                        }
                    }
                });
    }


    private void setSheetTagData(List<SheetTagInfo> content, String selectedTag) {
        allData.add(new SheetTagItem(SheetTagItem.TYPE_TAG_ALL));
        for(SheetTagInfo info : content) {
            allData.add(new SheetTagItem(SheetTagItem.TYPE_TAG_SPLIT));
            allData.add(new SheetTagItem(info));
        }
        mAdapter.setSelectedTag(selectedTag);
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
