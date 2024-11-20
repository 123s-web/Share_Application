package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookProgressDao {
    @Insert
    void insert(BookProgress progress);

    @Query("SELECT * FROM BookProgress WHERE bookId = :bookId LIMIT 1")
    BookProgress getProgress(String bookId);
    @Query("SELECT * FROM BookProgress")
    List<BookProgress> getAllProgress(); // 获取所有历史记录
}
