package com.watson.pureenjoy.music.http.entity.album;

import java.util.ArrayList;

public class AlbumDetailResponse {

    private AlbumInfo albumInfo;
    private ArrayList<AlbumSongInfo> songlist;
    private int is_collect;
    private String share_title;
    private String share_pic;

    public AlbumInfo getAlbumInfo() {
        return albumInfo;
    }

    public ArrayList<AlbumSongInfo> getSonglist() {
        return songlist;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public String getShare_title() {
        return share_title;
    }

    public String getShare_pic() {
        return share_pic;
    }
}
