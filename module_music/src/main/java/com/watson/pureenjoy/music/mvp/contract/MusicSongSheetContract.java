package com.watson.pureenjoy.music.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.watson.pureenjoy.music.http.entity.sheet.SheetInfo;
import com.watson.pureenjoy.music.http.entity.sheet.SheetResponse;

import java.util.List;

import io.reactivex.Observable;


public interface MusicSongSheetContract {
    interface View extends IView {
        void setSongSheetList(List<SheetInfo> sheetList, boolean haveMore);
    }

    interface Model extends IModel{
        Observable<SheetResponse> getSongSheetList(int pageNo, int pageSize);
    }
}
