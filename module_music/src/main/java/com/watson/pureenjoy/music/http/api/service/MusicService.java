package com.watson.pureenjoy.music.http.api.service;


import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.api.Api;
import com.watson.pureenjoy.music.http.entity.album.AlbumDetailResponse;
import com.watson.pureenjoy.music.http.entity.album.AlbumResponse;
import com.watson.pureenjoy.music.http.entity.rank.RankDetailResponse;
import com.watson.pureenjoy.music.http.entity.rank.RankResponse;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendResponse;
import com.watson.pureenjoy.music.http.entity.recommendSong.RecommendSongResponse;
import com.watson.pureenjoy.music.http.entity.sheet.SheetDetailResponse;
import com.watson.pureenjoy.music.http.entity.sheet.SheetHotResponse;
import com.watson.pureenjoy.music.http.entity.sheet.SheetResponse;
import com.watson.pureenjoy.music.http.entity.sheet.SheetTagResponse;
import com.watson.pureenjoy.music.http.entity.song.SongResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
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


    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<SheetResponse> getSongSheetResponseByTag(@Query("from") String from,
                                                        @Query("version") String version,
                                                        @Query("format") String format,
                                                        @Query("method") String method,
                                                        @Query("page_size") int page_size,
                                                        @Query("page_no") int page_no,
                                                        @Query("query") String tag);


    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<SheetTagResponse> getSheetTagResponse(@Query("from") String from,
                                                     @Query("version") String version,
                                                     @Query("format") String format,
                                                     @Query("method") String method);

    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<SheetHotResponse> getHotSheetResponse(@Query("from") String from,
                                                     @Query("version") String version,
                                                     @Query("format") String format,
                                                     @Query("method") String method,
                                                     @Query("num") int num);

    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<SheetDetailResponse> getSheetDetailResponse(@Query("from") String from,
                                                           @Query("version") String version,
                                                           @Query("format") String format,
                                                           @Query("method") String method,
                                                           @Query("listid") String listid);

    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<RankResponse> getMusicRank(@Query("from") String from,
                                          @Query("version") String version,
                                          @Query("format") String format,
                                          @Query("method") String method,
                                          @Query("kflag") int kflag);

    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<RankDetailResponse> getRankDetail(@Query("from") String from,
                                                 @Query("version") String version,
                                                 @Query("format") String format,
                                                 @Query("method") String method,
                                                 @Query("type") int type,
                                                 @Query("offset") int offset,
                                                 @Query("size") int size,
                                                 @Query("fields") String fields);


    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<AlbumResponse> getRecommendAlbum(@Query("from") String from,
                                                @Query("version") String version,
                                                @Query("format") String format,
                                                @Query("method") String method,
                                                @Query("offset") int offset,
                                                @Query("limit") int limit);

    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<AlbumDetailResponse> getAlbumDetailResponse(@Query("from") String from,
                                                           @Query("version") String version,
                                                           @Query("format") String format,
                                                           @Query("method") String method,
                                                           @Query("album_id") String albumid);


    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<RecommendSongResponse> getRecommendSong(@Query("from") String from,
                                                       @Query("version") String version,
                                                       @Query("format") String format,
                                                       @Query("method") String method,
                                                       @Query("num") int num);

    @Headers({DOMAIN_NAME_HEADER + Api.MUSIC_DOMAIN_NAME, MusicConstants.HEADER_AGENT})
    @GET("restserver/ting")
    Observable<SongResponse> getSongResponse(@Query("from") String from,
                                             @Query("version") String version,
                                             @Query("format") String format,
                                             @Query("method") String method,
                                             @Query("songid") String songid,
                                             @Query("ts") String ts,
                                             @Query("e") String e);

}
