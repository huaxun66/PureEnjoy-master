package com.watson.pureenjoy.music.http.api.service;


import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.api.Api;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResponse;
import com.watson.pureenjoy.music.http.entity.sheet.SheetResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface MusicService {

    @Headers(MusicConstants.HEADER_AGENT)
    @GET
    Observable<RecommendResponse> getRecommendResponse(@Url String url);

    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<SheetResponse> getSongSheetResponse(@Query("from") String from,
                                               @Query("version") String version,
                                               @Query("format") String format,
                                               @Query("method") String method,
                                               @Query("page_size") int page_size,
                                               @Query("page_no") int page_no);


}
