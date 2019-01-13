package com.watson.pureenjoy.music.http.entity.recommend;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class RecommendAlbum extends ResponseCode {
    private List<RecommendAlbumInfo> result;

    public List<RecommendAlbumInfo> getResult() {
        return result;
    }
}
