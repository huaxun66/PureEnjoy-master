package com.watson.pureenjoy.news.http.api.service;


import com.watson.pureenjoy.news.app.NewsConstants;
import com.watson.pureenjoy.news.http.api.Api;
import com.watson.pureenjoy.news.http.entity.NewsDetail;
import com.watson.pureenjoy.news.http.entity.NewsItem;
import com.watson.pureenjoy.news.http.entity.NewsPhotoSet;
import com.watson.pureenjoy.news.http.entity.NewsSpecial;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface NewsService {

    @Headers({DOMAIN_NAME_HEADER + Api.NEWS_LIST_DOMAIN_NAME, NewsConstants.HEADER_AGENT, NewsConstants.CACHE_CONTROL})
    @GET("nc/article/{type}/{id}/{offset}-{limit}.html")
    Observable<Map<String, List<NewsItem>>> getNewsList(@Path("type") String type, @Path("id") String id,
                                                        @Path("offset") int offset, @Path("limit") int limit);

    @Headers({DOMAIN_NAME_HEADER + Api.NEWS_LIST_DOMAIN_NAME, NewsConstants.HEADER_AGENT, NewsConstants.CACHE_CONTROL})
    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewsDetail(@Path("postId") String postId);


    @Headers({DOMAIN_NAME_HEADER + Api.NEWS_LIST_DOMAIN_NAME, NewsConstants.HEADER_AGENT, NewsConstants.CACHE_CONTROL})
    @GET("nc/special/{specialId}.html")
    Observable<Map<String, NewsSpecial>> getNewsSpecial(@Path("specialId") String specialId);


    @Headers({DOMAIN_NAME_HEADER + Api.NEWS_LIST_DOMAIN_NAME, NewsConstants.HEADER_AGENT, NewsConstants.CACHE_CONTROL})
    @GET("photo/api/set/{photoId}.json")
    Observable<Map<String, NewsPhotoSet>> getNewsPhotoSet(@Path("photoId") String photoId);
}
