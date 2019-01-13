package com.watson.pureenjoy.music.http.api.service;


import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface MusicService {

    @Headers(MusicConstants.HEADER_AGENT)
    @GET
    Observable<RecommendResponse> getRecommendResponse(@Url String url);
}
