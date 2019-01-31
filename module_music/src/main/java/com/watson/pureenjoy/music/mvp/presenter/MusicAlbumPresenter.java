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
import com.watson.pureenjoy.music.http.entity.album.AlbumInfo;
import com.watson.pureenjoy.music.http.entity.album.AlbumResponse;
import com.watson.pureenjoy.music.mvp.contract.MusicAlbumContract;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicAlbumAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MusicAlbumPresenter extends BasePresenter<MusicAlbumContract.Model, MusicAlbumContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    MusicAlbumAdapter mAdapter;
    @Inject
    List<AlbumInfo> allData;

    @Inject
    public MusicAlbumPresenter(MusicAlbumContract.Model model, MusicAlbumContract.View rootView) {
        super(model, rootView);
    }

    public void requestRecommendAlbum(Context context, int offset, int limit, boolean showLoading) {
        mModel.getRecommendAlbum(offset, limit)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (showLoading) mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<AlbumResponse>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AlbumResponse response) {
                        mRootView.getRecommendAlbumSuccess(response.getPlaze_album_list().getRM().getAlbum_list().getHavemore() == 1);
                        if (response.isSuccess()) {
                            setSheetData(offset, response.getPlaze_album_list().getRM().getAlbum_list().getList());
                        } else {
                            mRootView.showMessage(ArmsUtils.getString(context, R.string.public_server_error));
                        }
                    }
                });
    }

    private void setSheetData(int offset, List<AlbumInfo> list) {
        if (offset == 0) {
            allData.clear();
            //取第一条数据渲染Header
            mRootView.setHeaderData(list.get(0));
        }
        allData.addAll(list);
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
