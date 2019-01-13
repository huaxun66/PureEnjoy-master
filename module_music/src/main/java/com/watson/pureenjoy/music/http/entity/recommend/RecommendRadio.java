package com.watson.pureenjoy.music.http.entity.recommend;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class RecommendRadio extends ResponseCode {
    private List<RecommendRadioInfo> result;

    public List<RecommendRadioInfo> getResult() {
        return result;
    }
}
