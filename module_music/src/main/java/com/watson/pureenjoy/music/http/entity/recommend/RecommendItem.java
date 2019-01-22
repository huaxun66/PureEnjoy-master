package com.watson.pureenjoy.music.http.entity.recommend;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class RecommendItem  implements MultiItemEntity {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_RECOMMEND_SONG = 1; //推荐歌单
    public static final int TYPE_NEW_ALBUM = 2;  //最新专辑
    public static final int TYPE_ANCHOR_RADIO = 3; //主播电台
    public static final int TYPE_BANNER = 4; //banner+四个按钮
    private int type;

    private String title;
    private RecommendFocus mRecommendFocus;
    private RecommendAlbumInfo mRecommendAlbumInfo;
    private RecommendListInfo mRecommendListInfo;
    private RecommendRadioInfo mRecommendRadioInfo;

    public RecommendItem(RecommendFocus mRecommendFocus) {
        this.mRecommendFocus = mRecommendFocus;
        type = TYPE_BANNER;
    }

    public RecommendItem(RecommendListInfo mRecommendListInfo) {
        this.mRecommendListInfo = mRecommendListInfo;
        type = TYPE_RECOMMEND_SONG;
    }

    public RecommendItem(RecommendAlbumInfo mRecommendAlbumInfo) {
        this.mRecommendAlbumInfo = mRecommendAlbumInfo;
        type = TYPE_NEW_ALBUM;
    }

    public RecommendItem(RecommendRadioInfo mRecommendRadioInfo) {
        this.mRecommendRadioInfo = mRecommendRadioInfo;
        type = TYPE_ANCHOR_RADIO;
    }

    public RecommendItem(String title) {
        this.title = title;
        type = TYPE_HEADER;
    }

    public RecommendFocus getRecommendFocus() {
        return mRecommendFocus;
    }

    public RecommendAlbumInfo getRecommendAlbumInfo() {
        return mRecommendAlbumInfo;
    }

    public RecommendListInfo getRecommendListInfo() {
        return mRecommendListInfo;
    }

    public RecommendRadioInfo getRecommendRadioInfo() {
        return mRecommendRadioInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}

