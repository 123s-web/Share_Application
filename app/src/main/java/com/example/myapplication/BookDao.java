package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    long insert(Book book);

    @Query("SELECT * FROM Book WHERE id = :id LIMIT 1")
    Book getBook(int id);

    @Query("SELECT * FROM Book")
    List<Book> getAllBooks();
}
