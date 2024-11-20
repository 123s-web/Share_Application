package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookProgress {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int bookId;  // 书籍ID
    public int chapter;    // 当前章节
    public int page;       // 当前页码
}
