package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.album.AlbumDetailResponse;

import io.reactivex.Observable;


public interface MusicAlbumDetailContract {
    interface View extends IView {
        void setAlbumDetailInfo(AlbumDetailResponse response);
        Context getContext();
    }

    interface Model extends IModel{
        Observable<AlbumDetailResponse> getAlbumDetail(String albumid);
        Observable<AlbumDetailResponse> getAlbumDetailFromNet(String albumid);
    }
}
