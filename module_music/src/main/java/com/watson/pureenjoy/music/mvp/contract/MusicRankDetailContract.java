package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.rank.RankDetailResponse;
import com.watson.pureenjoy.music.http.entity.rank.RankInfo;

import io.reactivex.Observable;


public interface MusicRankDetailContract {
    interface View extends IView {
        void getRankDetailSuccess(RankInfo rankInfo);
        Context getContext();
    }

    interface Model extends IModel{
        Observable<RankDetailResponse> getRankDetail(int type, int offset, int size);
        Observable<RankDetailResponse> getRankDetailFromNet(int type, int offset, int size);
    }
}
