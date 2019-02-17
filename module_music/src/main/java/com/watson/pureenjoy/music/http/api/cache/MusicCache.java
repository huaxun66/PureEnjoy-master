/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.watson.pureenjoy.music.http.api.cache;


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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.Encrypt;
import io.rx_cache2.EncryptKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.ProviderKey;

/**
 * ================================================
 * 展示 {@link RxCache#using(Class)} 中需要传入的 Providers 的使用方式
 * <p>
 * Created by JessYan on 08/30/2016 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@EncryptKey("watson")
public interface MusicCache {

    @Encrypt
    @ProviderKey("music-recommend-response")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<RecommendResponse> getRecommendResponse(Observable<RecommendResponse> response, EvictProvider evictProvider);


    @Encrypt
    @ProviderKey("music-song-sheet")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<SheetResponse> getSongSheetResponse(Observable<SheetResponse> response, DynamicKey pageNo, EvictDynamicKey evictPageNo);


    @Encrypt
    @ProviderKey("music-song-sheet-by-tag")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<SheetResponse> getSongSheetResponseByTag(Observable<SheetResponse> response, DynamicKeyGroup keyGroup, EvictDynamicKey evictKey);


    @Encrypt
    @ProviderKey("music-sheet-tag")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<SheetTagResponse> getSheetTagResponse(Observable<SheetTagResponse> response, EvictProvider evictProvider);

    @Encrypt
    @ProviderKey("music-hot-sheet")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<SheetHotResponse> getHotSheetResponse(Observable<SheetHotResponse> response, EvictProvider evictProvider);

    @Encrypt
    @ProviderKey("music-sheet-detail")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<SheetDetailResponse> getSheetDetailResponse(Observable<SheetDetailResponse> response, DynamicKey listId, EvictDynamicKey evictListId);

    @Encrypt
    @ProviderKey("music-rank")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<RankResponse> getMusicRank(Observable<RankResponse> response, EvictProvider evictProvider);

    @Encrypt
    @ProviderKey("music-rank-detail")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<RankDetailResponse> getRankDetail(Observable<RankDetailResponse> response, DynamicKeyGroup keyGroup, EvictDynamicKey evictKey);


    @Encrypt
    @ProviderKey("music-recommend-album")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<AlbumResponse> getRecommendAlbum(Observable<AlbumResponse> response, DynamicKey offset, EvictDynamicKey evictOffset);

    @Encrypt
    @ProviderKey("music-album_detail")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<AlbumDetailResponse> getAlbumDetailResponse(Observable<AlbumDetailResponse> response, DynamicKey albumId, EvictDynamicKey evictAlbumId);


    @Encrypt
    @ProviderKey("music-recommend-song")
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<RecommendSongResponse> getRecommendSong(Observable<RecommendSongResponse> response, EvictProvider evictProvider);



}
