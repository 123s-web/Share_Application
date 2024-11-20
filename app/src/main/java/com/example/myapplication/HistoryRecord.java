package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryRecord {

    @PrimaryKey(autoGenerate = true)
    public int id;  // 主键ID

    public String bookName;  // 书籍名称

    public String timestamp;  // 时间戳

    // 构造函数
    public HistoryRecord(String bookName, String timestamp) {
        this.bookName = bookName;
        this.timestamp = timestamp;
    }
}
