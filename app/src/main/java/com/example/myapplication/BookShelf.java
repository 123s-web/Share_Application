package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BookShelf extends AppCompatActivity {
    private static final int PICK_TXT_FILE = 1; // 文件选择请求代码
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // 边距设置
        setContentView(R.layout.activity_main);

        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.book_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 网格布局，3 列
        bookAdapter = new BookAdapter(this,bookList);
        recyclerView.setAdapter(bookAdapter);

        // 导入 TXT 文件按钮
        Button buttonImportTxt = findViewById(R.id.button_import_txt);
        buttonImportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 打开文件选择器
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("text/plain");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, PICK_TXT_FILE);
            }
        });

        // 历史按钮
        Button buttonHistory = findViewById(R.id.button_history);
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookShelf.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // 其他按钮逻辑
//        Button button3 = findViewById(R.id.button_3);
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 处理逻辑
//                Log.d("BookShelf", "Button 3 clicked");
//            }
//        });

//        Button button4 = findViewById(R.id.button_4);
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("BookShelf", "Search button clicked");
//            }
//        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_TXT_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                String bookName = getFileName(uri);
                String bookContent = readFileContent(uri);

                if (bookName != null && bookContent != null) {
                    // 添加书籍到列表
                    bookList.add(new Book(bookName, bookContent));
                    bookAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    // 获取文件名
    private String getFileName(Uri uri) {
        String fileName = null;
        if ("content".equals(uri.getScheme())) {
            try (android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        return fileName;
    }

    // 读取文件内容
    private String readFileContent(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (Exception e) {
            Log.e("BookShelf", "Error reading file", e);
        }
        return stringBuilder.toString();
    }
}
