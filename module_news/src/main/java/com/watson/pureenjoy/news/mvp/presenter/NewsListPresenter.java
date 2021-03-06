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

import android.content.Context;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.db.database.NewsDataBase;
import com.watson.pureenjoy.news.http.entity.NewsItem;
import com.watson.pureenjoy.news.http.entity.NewsPhotoSet;
import com.watson.pureenjoy.news.mvp.contract.NewsListContract;
import com.watson.pureenjoy.news.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.core.ErrorHandleSingleObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.watson.pureenjoy.news.app.NewsConstants.BIG_IMG;


@FragmentScope
public class NewsListPresenter extends BasePresenter<NewsListContract.Model, NewsListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public NewsListPresenter(NewsListContract.Model model, NewsListContract.View rootView) {
        super(model, rootView);
    }

    public void requestNewsList(Context context, String typeId, int offset, int limit, boolean showLoading) {
        //从数据库取
        Observable<Map<String, List<NewsItem>>> localDataObservable = Observable.create((ObservableOnSubscribe<Map<String, List<NewsItem>>>) emitter -> {
            if (offset == 0 && showLoading) {
                List<NewsItem> newsList = NewsDataBase.getInstance(context).getNewsDao().loadNewsListById(typeId);
                if (newsList != null && newsList.size() > 0 && !isDataExpired(newsList)) {
                    Map<String, List<NewsItem>> item = new HashMap<>();
                    item.put(typeId, newsList);
                    emitter.onNext(item);
                }
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
        //从网络取
        Observable<Map<String, List<NewsItem>>> remoteDataObservable = mModel.getNewsList(typeId, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(dataResponse -> {
                    if (offset == 0) {
                        List<NewsItem> list = dataResponse.get(typeId);
                        for (NewsItem item : list) {
                            item.setTypeId(typeId); //数据库查询用
                            item.setCurrentTime(System.currentTimeMillis());
                        }
                        NewsDataBase.getInstance(context).getNewsDao().deleteNewsList(typeId);
                        NewsDataBase.getInstance(context).getNewsDao().insertNewsList(list);
                    }
                    return dataResponse;
                });

        Observable.concat(localDataObservable, remoteDataObservable)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (showLoading) mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(stringListMap -> Observable.fromIterable(stringListMap.get(typeId)))
                .doOnNext(newsItem -> {
                    //多图,非Banner
                    if (NewsConstants.PHOTO_SET.equals(newsItem.getSkipType()) && newsItem.getHasAD() != 1) {
                        if (newsItem.getImgextra() == null || newsItem.getImgextra().size() < 3) {
                            getExtraPhotoSet(context, newsItem);
                        }
                    }
                    //generate Banner
                    if (newsItem.getHasAD() == 1 && newsItem.getAds() != null && newsItem.getAds().size() > 0) {
                        //Banner只加载首页
                        if (offset == 0) {
                            getBanner(context, newsItem);
                        } else {
                            newsItem.setHasAD(0);
                        }
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView)) //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSingleObserver<List<NewsItem>>(mErrorHandler) {
                    @Override
                    public void onSuccess(@NonNull List<NewsItem> newsList) {
                        mRootView.setNewsList(newsList);
                    }
                });
    }


    private void getExtraPhotoSet(Context context, final NewsItem newsItem) {
        mModel.getNewsPhotoSet(StringUtil.clipPhotoSetId(newsItem.getPhotosetID()))
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView)) //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NewsPhotoSet>(mErrorHandler) {
                    @Override
                    public void onNext(NewsPhotoSet newsPhotoSet) {
                        if (newsPhotoSet.getPhotos() != null && newsPhotoSet.getPhotos().size() > 0) {
                            List<NewsItem.ImgextraEntity> list = new ArrayList<>();
                            for (NewsPhotoSet.PhotosEntity entity : newsPhotoSet.getPhotos()) {
                                NewsItem.ImgextraEntity item = new NewsItem.ImgextraEntity();
                                item.setImgsrc(entity.getImgurl());
                                list.add(item);
                            }
                            newsItem.setImgextra(list);
                        }
                    }
                });
    }

    private void getBanner(Context context, NewsItem newsItem) {
        List<NewsItem.AdsEntity> ads = newsItem.getAds();
        for (NewsItem.AdsEntity entity : ads) {
            if (entity.getImgsrc().equals(BIG_IMG)) {
                mModel.getNewsPhotoSet(StringUtil.clipPhotoSetId(entity.getSkipID()))
                        .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                        .subscribe(new ErrorHandleSubscriber<NewsPhotoSet>(mErrorHandler) {
                            @Override
                            public void onNext(NewsPhotoSet newsPhotoSet) {
                                if (newsPhotoSet.getPhotos() != null && newsPhotoSet.getPhotos().size() > 0) {
                                    entity.setImgsrc(newsPhotoSet.getPhotos().get(new Random().nextInt(newsPhotoSet.getPhotos().size())).getImgurl());
                                }
                            }
                        });
            }

        }
    }

    private boolean isDataExpired(List<NewsItem> list) {
        return list.get(0).getCurrentTime() < System.currentTimeMillis() - NewsConstants.NEWS_EXPIRE_TIME;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
