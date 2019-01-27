package com.watson.pureenjoy.music.http.entity.sheet;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class SheetDetailResponse extends ResponseCode {

    private String listid;
    private String title;
    private String pic_300;
    private String pic_500;
    private String pic_w700;
    private String width;
    private String height;
    private String listenum;
    private String collectnum;
    private String tag;
    private String desc;
    private String url;
    private List<SheetDetailInfo> content;

    public String getListid() {
        return listid;
    }

    public String getTitle() {
        return title;
    }

    public String getPic_300() {
        return pic_300;
    }

    public String getPic_500() {
        return pic_500;
    }

    public String getPic_w700() {
        return pic_w700;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getListenum() {
        return listenum;
    }

    public String getCollectnum() {
        return collectnum;
    }

    public String getTag() {
        return tag;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }

    public List<SheetDetailInfo> getContent() {
        return content;
    }
}
