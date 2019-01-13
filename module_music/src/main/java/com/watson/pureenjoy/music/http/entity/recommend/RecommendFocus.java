package com.watson.pureenjoy.music.http.entity.recommend;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class RecommendFocus extends ResponseCode {
    private List<RecommendFocusInfo> result;

    public List<RecommendFocusInfo> getResult() {
        return result;
    }
}
