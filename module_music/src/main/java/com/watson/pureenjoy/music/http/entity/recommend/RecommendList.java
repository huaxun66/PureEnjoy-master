package com.watson.pureenjoy.music.http.entity.recommend;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class RecommendList extends ResponseCode {
    private List<RecommendListInfo> result;

    public List<RecommendListInfo> getResult() {
        return result;
    }
}
