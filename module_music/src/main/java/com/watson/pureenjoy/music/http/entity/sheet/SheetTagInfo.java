package com.watson.pureenjoy.music.http.entity.sheet;


import java.util.List;

public class SheetTagInfo {

    private String title;
    private int num;
    private List<TagInfo> tags;

    public String getTitle() {
        return title;
    }

    public int getNum() {
        return num;
    }

    public List<TagInfo> getTags() {
        return tags;
    }
}
