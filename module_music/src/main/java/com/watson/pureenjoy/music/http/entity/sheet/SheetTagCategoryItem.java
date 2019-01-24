package com.watson.pureenjoy.music.http.entity.sheet;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SheetTagCategoryItem implements MultiItemEntity {
    public static final int TYPE_TAG_CATEGORY_ITEM = 0; //种类
    public static final int TYPE_TAG_ITEM = 1; //歌单item

    private int type;

    private String selectedTag; //选中的标签

    private String name;
    private int icon;

    public SheetTagCategoryItem(String name) {
        this.name = name;
        this.type = TYPE_TAG_ITEM;
    }

    public SheetTagCategoryItem(String name, int icon) {
        this.name = name;
        this.icon = icon;
        this.type = TYPE_TAG_CATEGORY_ITEM;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public int getItemType() {
        return type;
    }
}

