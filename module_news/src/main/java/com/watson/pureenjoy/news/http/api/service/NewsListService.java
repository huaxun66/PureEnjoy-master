package com.watson.pureenjoy.news.http.api.service;


import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.http.entity.NewsItem;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface NewsListService {

    @Headers({NewsConstants.HEADER_AGENT, NewsConstants.CACHE_CONTROL})
    @GET("nc/article/{type}/{id}/{offset}-{limit}.html")
    Observable<Map<String, List<NewsItem>>> getNewsList(@Path("type") String type, @Path("id") String id,
                                                        @Path("offset") int offset, @Path("limit") int limit);
}
