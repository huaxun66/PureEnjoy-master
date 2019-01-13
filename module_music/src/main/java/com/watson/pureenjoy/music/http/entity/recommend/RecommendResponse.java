package com.watson.pureenjoy.music.http.entity.recommend;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class RecommendResponse extends ResponseCode {

    private RecommendResult result;
    private List<RecommendModuleInfo> module;

    public RecommendResult getResult() {
        return result;
    }

    public List<RecommendModuleInfo> getModule() {
        return module;
    }
}
