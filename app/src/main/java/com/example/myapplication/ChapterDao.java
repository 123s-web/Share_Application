package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChapterDao {
    @Insert
    void insert(Chapter chapter);

    @Query("SELECT * FROM Chapter WHERE bookId = :bookId")
    List<Chapter> getChaptersByBookId(int bookId);
}
