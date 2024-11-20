package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private final String bookId = "book123";  // 示例书籍ID，可以根据实际需要更改
    private int currentChapter = 0;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // 正确引用 R 类

        db = AppDatabase.getInstance(this);

        // 在后台线程中加载进度
        Executors.newSingleThreadExecutor().execute(() -> {
            BookProgress progress = db.bookProgressDao().getProgress(bookId);
            if (progress != null) {
                currentChapter = progress.chapter;
                currentPage = progress.page;
                // 在主线程中更新阅读器界面（如显示章节和页码）
                runOnUiThread(() -> {
                    // 更新UI逻辑，比如设置TextView显示章节和页码
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在后台线程中保存进度
        Executors.newSingleThreadExecutor().execute(() -> {
            BookProgress progress = new BookProgress();
            progress.bookId = bookId;
            progress.chapter = currentChapter;
            progress.page = currentPage;
            db.bookProgressDao().insert(progress);
        });
    }
}
