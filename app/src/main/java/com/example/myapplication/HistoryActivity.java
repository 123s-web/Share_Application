package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        // 初始化历史记录列表
        ListView historyList = findViewById(R.id.history_list);

        // 模拟一些历史数据
        List<String> historyData = new ArrayList<>();
        historyData.add("书籍1 - 2024/11/01");
        historyData.add("书籍2 - 2024/11/05");
        historyData.add("书籍3 - 2024/11/10");

        // 设置适配器将数据绑定到 ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyData);
        historyList.setAdapter(adapter);

        // 返回按钮功能
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> {
            // 跳转回书架界面
            Intent intent = new Intent(HistoryActivity.this, BookShelf.class);
            startActivity(intent);
        });

        // 适配系统栏
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.history_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
