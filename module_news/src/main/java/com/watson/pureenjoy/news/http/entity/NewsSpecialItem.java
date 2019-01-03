package com.watson.pureenjoy.news.http.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.watson.pureenjoy.news.app.NewsConstants;

public class NewsSpecialItem  implements MultiItemEntity {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_PHOTO_SET = 2;
    private int type;

    private NewsSpecial.TopicsEntity.DocsEntity entity;
    private String title;

    public NewsSpecialItem(NewsSpecial.TopicsEntity.DocsEntity entity) {
        this.entity = entity;
        if (NewsConstants.PHOTO_SET.equals(entity.getSkipType())) {
            type = TYPE_PHOTO_SET;
        } else {
            type = TYPE_NORMAL;
        }
    }


    public NewsSpecialItem(String title) {
        this.title = title;
        type = TYPE_HEADER;
    }

    public NewsSpecial.TopicsEntity.DocsEntity getEntity() {
        return entity;
    }

    public void setEntity(NewsSpecial.TopicsEntity.DocsEntity entity) {
        this.entity = entity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
