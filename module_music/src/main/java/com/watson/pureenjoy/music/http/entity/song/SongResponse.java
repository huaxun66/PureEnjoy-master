package com.watson.pureenjoy.music.http.entity.song;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

public class SongResponse extends ResponseCode {

    private String songurl;

    public String getSongurl() {
        return songurl;
    }

    public void setSongurl(String songurl) {
        this.songurl = songurl;
    }
}
