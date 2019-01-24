package com.watson.pureenjoy.music.http.entity.sheet;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class SheetTagResponse extends ResponseCode {

    private List<SheetTagInfo> content;

    public List<SheetTagInfo> getContent() {
        return content;
    }
}
