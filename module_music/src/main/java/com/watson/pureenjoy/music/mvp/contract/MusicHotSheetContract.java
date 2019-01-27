package com.watson.pureenjoy.music.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.sheet.SheetHotResponse;

import io.reactivex.Observable;


public interface MusicHotSheetContract {
    interface View extends IView {
        Context getContext();
    }

    interface Model extends IModel{
        Observable<SheetHotResponse> getHotSheetList(int num);
    }
}
