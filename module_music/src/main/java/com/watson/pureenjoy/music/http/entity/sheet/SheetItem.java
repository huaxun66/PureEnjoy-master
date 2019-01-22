package com.watson.pureenjoy.music.http.entity.sheet;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendAlbumInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendFocus;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendListInfo;
import com.watson.pureenjoy.music.http.entity.recommend.RecommendRadioInfo;

public class SheetItem implements MultiItemEntity {
    public static final int TYPE_HOT_BANNER = 0; //热门歌单
    public static final int TYPE_HEADER = 1; //标题
    public static final int TYPE_SHEET_ITEM = 2; //歌单item


    private int type;

    private String imgUrl;
    private SheetInfo mSheetInfo;

    public SheetItem(String imgUrl) {
        this.imgUrl = imgUrl;
        type = TYPE_HOT_BANNER;
    }

    public SheetItem(SheetInfo mSheetInfo) {
        this.mSheetInfo = mSheetInfo;
        type = TYPE_SHEET_ITEM;
    }

    public SheetItem() {
        type = TYPE_HEADER;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public SheetInfo getmSheetInfo() {
        return mSheetInfo;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}

