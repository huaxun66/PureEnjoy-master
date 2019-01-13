package com.watson.pureenjoy.music.http.entity.recommend;

public class RecommendResult {

    private RecommendList diy;
    private RecommendAlbum mix_1;
    private RecommendRadio radio;
    private RecommendFocus focus;

    public RecommendList getRecommendList() {
        return diy;
    }

    public RecommendAlbum getRecommendAlbum() {
        return mix_1;
    }

    public RecommendRadio getRecommendRadio() {
        return radio;
    }

    public RecommendFocus getRecommendFocus() {
        return focus;
    }
}
