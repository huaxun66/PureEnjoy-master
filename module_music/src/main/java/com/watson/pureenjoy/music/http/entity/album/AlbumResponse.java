package com.watson.pureenjoy.music.http.entity.album;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

public class AlbumResponse extends ResponseCode {

    private AlbumPlazeResponse plaze_album_list;

    public AlbumPlazeResponse getPlaze_album_list() {
        return plaze_album_list;
    }
}
