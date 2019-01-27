package com.watson.pureenjoy.music.http.entity.sheet;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

public class SheetHotResponse extends ResponseCode {

    private SheetHotInfo content;

    public SheetHotInfo getContent() {
        return content;
    }
}
