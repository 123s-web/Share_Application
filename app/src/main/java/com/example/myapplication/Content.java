package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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

    private String bookName = "书籍名称"; // 模拟书籍名称，可以从 Intent 或其他来源获取。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content);

        // 添加历史记录
        addHistoryRecord(bookName);

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content.this, BookShelf.class);
                startActivity(intent);
            }
        });

        Button buttonDirectory = findViewById(R.id.button_directory);
        buttonDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDirectoryDialog();
            }

            private void showDirectoryDialog() {
                final Dialog dialog = new Dialog(Content.this);
                dialog.setContentView(R.layout.activity_dialog);
                // 设置对话框的宽度和高度
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT; // 宽度设置为屏幕宽度
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度设置为包裹内容
                layoutParams.gravity = Gravity.START; // 对话框靠左显示

                dialog.getWindow().setAttributes(layoutParams);

                dialog.show();
            }
        });

        // 夜间模式切换
        Button buttonNight = findViewById(R.id.button_night);
        final boolean[] isNightMode = {(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)};
        buttonNight.setOnClickListener(v -> {
            if (isNightMode[0]) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                buttonNight.setBackgroundResource(R.drawable.light);  // 设置白天图标
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                buttonNight.setBackgroundResource(R.drawable.night);  // 设置夜晚图标
            }
            isNightMode[0] = !isNightMode[0];  // 切换模式状态
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 添加历史记录的方法
    private void addHistoryRecord(String bookName) {
        // 获取当前时间
        String timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // 创建一个新线程，插入记录
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(Content.this);
            HistoryRecord record = new HistoryRecord(bookName, timestamp);
            db.historyDao().insertHistory(record);
        }).start();
    }
}
