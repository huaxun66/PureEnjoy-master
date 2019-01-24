package com.watson.pureenjoy.music.http.entity.sheet;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SheetTagItem implements MultiItemEntity {
    public static final int TYPE_TAG_ALL = 0; //所有歌单item
    public static final int TYPE_TAG_CATEGORY = 1; //按种类划分item
    public static final int TYPE_TAG_SPLIT = 2; //分割线

    private int type;

    private SheetTagInfo info;

    public SheetTagItem(SheetTagInfo info) {
        this.info = info;
        this.type = TYPE_TAG_CATEGORY;
    }

    public SheetTagItem(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public SheetTagInfo getInfo() {
        return info;
    }

    @Override
    public int getItemType() {
        return type;
    }
}

