package com.watson.pureenjoy.music.http.entity.recommendSong;

import com.watson.pureenjoy.music.http.entity.ResponseCode;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendModuleInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResult;

import java.util.List;

public class RecommendSongResponse extends ResponseCode {

    private List<RecommendInfo> content;

    public List<RecommendInfo> getContent() {
        return content;
    }
}
