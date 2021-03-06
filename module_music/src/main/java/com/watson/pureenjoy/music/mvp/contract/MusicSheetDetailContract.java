package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.sheet.SheetDetailResponse;
import com.watson.pureenjoy.music.http.entity.song.SongResponse;

import io.reactivex.Observable;


public interface MusicSheetDetailContract {
    interface View extends IView {
        void setSheetDetailInfo(SheetDetailResponse response);
        void setSongInfo(SongResponse response);
        Context getContext();
    }

    interface Model extends IModel{
        Observable<SheetDetailResponse> getSheetDetail(String listid);
        Observable<SheetDetailResponse> getSheetDetailFromNet(String listid);
        Observable<SongResponse> getSongInfo(String songId);
    }
}
