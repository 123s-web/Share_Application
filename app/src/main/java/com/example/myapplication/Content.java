package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Content extends AppCompatActivity {

    private String bookContent = "书籍内容"; // 模拟书籍内容，可以从 Intent 或其他来源获取。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content);

        // 获取书籍内容（从 Intent 中获取传递的数据）
        Intent intent = getIntent();
        bookContent = intent.getStringExtra("bookContent");  // 只需要获取书籍内容

        // 显示书籍内容
        TextView contentTextView = findViewById(R.id.Content);
        contentTextView.setText(bookContent);

        // 添加历史记录（如果需要记录每本书的阅读历史）
        addHistoryRecord();

        // 返回书架按钮
        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            Intent intentBack = new Intent(Content.this, BookShelf.class);
            startActivity(intentBack);
        });

        // 目录按钮
        Button buttonDirectory = findViewById(R.id.button_directory);
        buttonDirectory.setOnClickListener(v -> showDirectoryDialog());

        // 夜间模式切换
        Button buttonNight = findViewById(R.id.button_night);
        final boolean[] isNightMode = {AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES};
        buttonNight.setOnClickListener(v -> {
            if (isNightMode[0]) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                buttonNight.setBackgroundResource(R.drawable.light); // 白天图标
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                buttonNight.setBackgroundResource(R.drawable.night); // 夜晚图标
            }
            isNightMode[0] = !isNightMode[0]; // 切换模式状态
        });

        // 适配系统边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 添加历史记录的方法
    private void addHistoryRecord() {
        // 获取当前时间
        String timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // 创建一个新线程，插入记录
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(Content.this);
            // 不再需要传递书名，只记录书籍内容和时间戳
            HistoryRecord record = new HistoryRecord(bookContent, timestamp);
            db.historyDao().insertHistory(record);
        }).start();
    }

    private void showDirectoryDialog() {
        final Dialog dialog = new Dialog(Content.this);
        dialog.setContentView(R.layout.activity_dialog);

        // 设置对话框的宽度和高度
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.START; // 对话框靠左显示

        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }
}
