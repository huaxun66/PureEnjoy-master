package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.recommendSong.RecommendSongResponse;

import io.reactivex.Observable;


public interface MusicRecommendSongContract {
    interface View extends IView {
        void setRecommendSong(RecommendSongResponse response);
        Context getContext();
    }

    interface Model extends IModel{
        Observable<RecommendSongResponse> getRecommendSong(int num);
    }
}
