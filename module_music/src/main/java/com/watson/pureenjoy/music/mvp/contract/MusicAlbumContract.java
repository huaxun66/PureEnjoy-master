package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.album.AlbumInfo;
import com.watson.pureenjoy.music.http.entity.album.AlbumResponse;

import io.reactivex.Observable;


public interface MusicAlbumContract {
    interface View extends IView {
        void setHeaderData(AlbumInfo info);
        void getRecommendAlbumSuccess(boolean haveMore);
        Context getContext();
    }

    interface Model extends IModel{
        Observable<AlbumResponse> getRecommendAlbum(int offset, int limit);
    }
}
