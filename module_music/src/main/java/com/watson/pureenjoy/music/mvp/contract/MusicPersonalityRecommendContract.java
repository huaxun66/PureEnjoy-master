package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResponse;

import io.reactivex.Observable;


public interface MusicPersonalityRecommendContract {
    interface View extends IView {
        void getRecommendResponseFinish();
        Context getContext();
    }

    interface Model extends IModel{
        Observable<RecommendResponse> getRecommendResponse();
        Observable<RecommendResponse> getRecommendResponseFromNet();
    }
}
