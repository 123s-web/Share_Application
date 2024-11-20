package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chapter {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int bookId;       // 关联的书籍 ID
    public int chapterNumber; // 章节号
    public String title;      // 章节标题
    public String content;    // 章节内容
}
