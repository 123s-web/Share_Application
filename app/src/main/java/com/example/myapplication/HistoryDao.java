package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    // 插入历史记录
    @Insert
    void insertHistory(HistoryRecord record);

    // 查询所有历史记录，按时间倒序排列
    @Query("SELECT * FROM HistoryRecord ORDER BY id DESC")
    List<HistoryRecord> getAllHistory();
}
