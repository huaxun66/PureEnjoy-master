package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagResponse;

import io.reactivex.Observable;


public interface MusicSheetTagContract {
    interface View extends IView {
        Context getContext();
    }

    interface Model extends IModel{
        Observable<SheetTagResponse> getSheetTag();
        Observable<SheetTagResponse> getSheetTagFromNet();
    }
}
