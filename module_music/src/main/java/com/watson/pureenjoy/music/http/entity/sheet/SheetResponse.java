package com.watson.pureenjoy.music.http.entity.sheet;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class SheetResponse extends ResponseCode {

    private String total;
    private String havemore;
    private List<SheetInfo> content;

    public String getTotal() {
        return total;
    }

    public String getHavemore() {
        return havemore;
    }

    public List<SheetInfo> getContent() {
        return content;
    }
}
