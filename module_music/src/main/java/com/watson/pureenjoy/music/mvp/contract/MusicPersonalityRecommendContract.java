package com.watson.pureenjoy.music.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResponse;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResult;

import io.reactivex.Observable;


public interface MusicPersonalityRecommendContract {
    interface View extends IView {
        void setRecommendResult(RecommendResult recommendResult);
    }

    interface Model extends IModel{
        Observable<RecommendResponse> getRecommendResponse();
    }
}
