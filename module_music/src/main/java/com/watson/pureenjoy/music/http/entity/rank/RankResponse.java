package com.watson.pureenjoy.music.http.entity.rank;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class RankResponse extends ResponseCode {

    private List<RankInfo> content;

    public List<RankInfo> getContent() {
        return content;
    }
}
