package com.watson.pureenjoy.music.http.entity.sheet;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class SheetHotInfo extends ResponseCode {

    private String title;
    private List<SheetInfo> list;

    public String getTitle() {
        return title;
    }

    public List<SheetInfo> getList() {
        return list;
    }
}
