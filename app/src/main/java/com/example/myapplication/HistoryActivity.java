package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // 返回书架界面
        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, BookShelf.class);
                startActivity(intent);
            }
        });


        ListView historyList = findViewById(R.id.history_list);

        // 加载历史记录数据
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<HistoryRecord> historyRecords = db.historyDao().getAllHistory();

            // 将数据转换为字符串列表
            List<String> displayData = new ArrayList<>();
            for (HistoryRecord record : historyRecords) {
                displayData.add(record.bookName + " - " + record.timestamp);
            }

            // 更新UI
            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayData);
                historyList.setAdapter(adapter);
            });
        }).start();
    }
}
