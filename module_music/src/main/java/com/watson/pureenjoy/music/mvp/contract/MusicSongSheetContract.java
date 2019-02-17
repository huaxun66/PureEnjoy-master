package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.sheet.SheetResponse;

import io.reactivex.Observable;


public interface MusicSongSheetContract {
    interface View extends IView {
        void getSongSheetListSuccess(boolean haveMore);
        Context getContext();
    }

    interface Model extends IModel{
        Observable<SheetResponse> getSongSheetList(int pageNo, int pageSize);
        Observable<SheetResponse> getSongSheetListFromNet(int pageNo, int pageSize);
        Observable<SheetResponse> getSongSheetListByTag(String tag, int pageNo, int pageSize);
        Observable<SheetResponse> getSongSheetListByTagFromNet(String tag, int pageNo, int pageSize);
    }
}
