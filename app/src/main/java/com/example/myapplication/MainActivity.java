package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private int currentChapterIndex = 0; // 当前章节索引
    private List<Chapter> chapters; // 用于存储当前书的所有章节

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取数据库实例
        db = AppDatabase.getInstance(this);

        // 初始化UI组件
        TextView chapterTitle = findViewById(R.id.chapterTitle);
        TextView chapterContent = findViewById(R.id.chapterContent);
        Button nextChapterButton = findViewById(R.id.nextChapterButton);
        Button prevChapterButton = findViewById(R.id.prevChapterButton);
        Button insertDataButton = findViewById(R.id.insertDataButton);

        // 插入数据按钮点击事件
        insertDataButton.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                insertSampleData();

                // 插入完成后更新UI
                runOnUiThread(() -> {
                    chapterTitle.setText("数据插入完成");
                    chapterContent.setText("可以浏览章节内容了");
                });
            });
        });

        // 在后台线程中加载章节数据
        Executors.newSingleThreadExecutor().execute(() -> {
            // 获取第一本书（假设只有一本书）
            List<Book> books = db.bookDao().getAllBooks();
            if (!books.isEmpty()) {
                int bookId = books.get(0).id; // 获取第一本书的ID
                chapters = db.chapterDao().getChaptersByBookId(bookId); // 加载该书的章节列表

                // 如果有章节数据，在主线程中更新UI
                if (!chapters.isEmpty()) {
                    runOnUiThread(() -> {
                        displayChapter(chapters.get(currentChapterIndex), chapterTitle, chapterContent);
                    });
                }
            }
        });

        // 下一章节按钮
        nextChapterButton.setOnClickListener(v -> {
            if (chapters != null && currentChapterIndex < chapters.size() - 1) {
                currentChapterIndex++;
                displayChapter(chapters.get(currentChapterIndex), chapterTitle, chapterContent);
            }
        });

        // 上一章节按钮
        prevChapterButton.setOnClickListener(v -> {
            if (chapters != null && currentChapterIndex > 0) {
                currentChapterIndex--;
                displayChapter(chapters.get(currentChapterIndex), chapterTitle, chapterContent);
            }
        });
    }

    /**
     * 插入示例数据
     */
    private void insertSampleData() {
        // 插入示例书籍
        Book book = new Book();
        book.title = "示例小说";
        book.author = "作者A";
        long bookId =  db.bookDao().insert(book);

        // 插入章节 1
        Chapter chapter1 = new Chapter();
        chapter1.bookId = (int)bookId;
        chapter1.title = "第一章";
        chapter1.content = "这是第一章的内容";
        db.chapterDao().insert(chapter1);

        // 插入章节 2
        Chapter chapter2 = new Chapter();
        chapter2.bookId = (int)bookId;
        chapter2.title = "第二章";
        chapter2.content = "这是第二章的内容";
        db.chapterDao().insert(chapter2);
    }

    /**
     * 显示指定章节的内容
     *
     * @param chapter       当前章节
     * @param chapterTitle  章节标题的 TextView
     * @param chapterContent 章节内容的 TextView
     */
    private void displayChapter(Chapter chapter, TextView chapterTitle, TextView chapterContent) {
        chapterTitle.setText(chapter.title); // 设置章节标题
        chapterContent.setText(chapter.content); // 设置章节内容
    }
}
