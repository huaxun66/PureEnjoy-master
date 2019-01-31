package com.watson.pureenjoy.music.http.entity.album;


import java.util.List;

public class AlbumListResponse {

    private int total;
    private int havemore;
    private List<AlbumInfo> list;

    public int getTotal() {
        return total;
    }

    public int getHavemore() {
        return havemore;
    }

    public List<AlbumInfo> getList() {
        return list;
    }
}
