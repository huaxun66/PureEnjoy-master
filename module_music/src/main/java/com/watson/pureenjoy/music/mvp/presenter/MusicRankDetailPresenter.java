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
import com.watson.pureenjoy.music.http.entity.rank.RankDetailResponse;
import com.watson.pureenjoy.music.http.entity.rank.RankSongInfo;
import com.watson.pureenjoy.music.mvp.contract.MusicRankDetailContract;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRankDetailAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MusicRankDetailPresenter extends BasePresenter<MusicRankDetailContract.Model, MusicRankDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    MusicRankDetailAdapter mAdapter;
    @Inject
    List<RankSongInfo> allData;

    @Inject
    public MusicRankDetailPresenter(MusicRankDetailContract.Model model, MusicRankDetailContract.View rootView) {
        super(model, rootView);
    }

    public void requestRankDetail(Context context, int type, int offset, int size, boolean showLoading) {
        mModel.getRankDetail(type, offset, size)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (showLoading) mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RankDetailResponse>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull RankDetailResponse response) {
                        if (response.isSuccess()) {
                            mRootView.getRankDetailSuccess(response.getBillboard());
                            if (response.getSong_list()!=null) {
                                setSheetData(offset, response.getSong_list());
                            } else {
                                mRootView.showMessage(ArmsUtils.getString(context, R.string.music_no_more));
                            }
                        } else {
                            mRootView.showMessage(ArmsUtils.getString(context, R.string.public_server_error));
                        }
                    }
                });
    }


    private void setSheetData(int offset, List<RankSongInfo> content) {
        if (offset == 0) {
            allData.clear();
        }
        allData.addAll(content);
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
