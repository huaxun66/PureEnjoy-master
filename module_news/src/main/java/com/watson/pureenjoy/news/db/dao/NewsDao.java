package com.watson.pureenjoy.news.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.watson.pureenjoy.news.http.entity.NewsItem;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inertNews(NewsItem item);

    @Insert
    void insertNewsList(List<NewsItem> list);

    @Update
    void updateNews(NewsItem item);

    @Delete
    void deleteNews(NewsItem item);

    @Query("Delete from news Where type_id=:typeId")
    void deleteNewsList(String typeId);

    @Query("Select * from news Where type_id=:typeId")
    List<NewsItem> loadNewsListById(String typeId);



}
