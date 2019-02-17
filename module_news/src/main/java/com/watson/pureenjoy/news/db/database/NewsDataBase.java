package com.watson.pureenjoy.news.db.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.watson.pureenjoy.news.db.dao.NewsDao;
import com.watson.pureenjoy.news.http.entity.NewsItem;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsDataBase extends RoomDatabase {
    private static final String DB_NAME = "NewsDatabase.db";
    private static volatile NewsDataBase instance;

    public static synchronized NewsDataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static NewsDataBase create(final Context context) {
        return Room.databaseBuilder(context, NewsDataBase.class, DB_NAME).build();
    }

    public abstract NewsDao getNewsDao();
}
