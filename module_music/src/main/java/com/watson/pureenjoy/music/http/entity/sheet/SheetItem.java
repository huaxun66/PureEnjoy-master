package com.watson.pureenjoy.music.http.entity.sheet;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SheetItem implements MultiItemEntity {
    public static final int TYPE_HOT_BANNER = 0; //热门歌单
    public static final int TYPE_HEADER = 1; //标题
    public static final int TYPE_SHEET_ITEM = 2; //歌单item


    private int type;

    private SheetInfo mSheetInfo;

    public SheetItem(int type, SheetInfo mSheetInfo) {
        this.mSheetInfo = mSheetInfo;
        this.type = type;
    }

    public SheetItem() {
        type = TYPE_HEADER;
    }

    public SheetInfo getSheetInfo() {
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

