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

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendAlbumInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendItem;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendListInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendRadioInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResponse;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResult;
import com.watson.pureenjoy.music.mvp.contract.MusicPersonalityRecommendContract;
import com.watson.pureenjoy.music.mvp.ui.adapter.MusicRecommendAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@FragmentScope
public class MusicPersonalityRecommendPresenter extends BasePresenter<MusicPersonalityRecommendContract.Model, MusicPersonalityRecommendContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    MusicRecommendAdapter mAdapter;
    @Inject
    List<RecommendItem> allData;

    @Inject
    public MusicPersonalityRecommendPresenter(MusicPersonalityRecommendContract.Model model, MusicPersonalityRecommendContract.View rootView) {
        super(model, rootView);
    }

    public void requestRecommendResponse(Context context) {
        mModel.getRecommendResponse()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView)) //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<RecommendResponse>(mErrorHandler) {
                    @Override
                    public void onNext(RecommendResponse response) {
                        mRootView.getRecommendResponseFinish();
                        if (response.isSuccess()) {
                            setRecommendData(context, response.getResult());
                        } else {
                            mRootView.showMessage(ArmsUtils.getString(context, R.string.public_server_error));
                        }
                    }
                });
    }


    private void setRecommendData(Context context, RecommendResult recommendResult) {
        allData.clear();
        if (recommendResult.getRecommendFocus().isSuccess()) {
            allData.add(new RecommendItem(recommendResult.getRecommendFocus()));
        }
        if (recommendResult.getRecommendList().isSuccess()) {
            allData.add(new RecommendItem(context.getString(R.string.music_recommend_list)));
            for(RecommendListInfo info : recommendResult.getRecommendList().getResult()) {
                allData.add(new RecommendItem(info));
            }
        }
        if (recommendResult.getRecommendAlbum().isSuccess()) {
            allData.add(new RecommendItem(context.getString(R.string.music_recommend_album)));
            for(RecommendAlbumInfo info : recommendResult.getRecommendAlbum().getResult()) {
                allData.add(new RecommendItem(info));
            }
        }
        if (recommendResult.getRecommendRadio().isSuccess()) {
            allData.add(new RecommendItem(context.getString(R.string.music_recommend_radio)));
            for(RecommendRadioInfo info : recommendResult.getRecommendRadio().getResult()) {
                allData.add(new RecommendItem(info));
            }
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
