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


public class Content extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content);

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
        // 获取按钮视图
        Button buttonNight = findViewById(R.id.button_night);

// 设置按钮的点击事件
// 初始化夜间模式状态
        final boolean[] isNightMode = {(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)};

// 设置按钮的点击事件
        buttonNight.setOnClickListener(v -> {
            if (isNightMode[0]) {
                // 切换到白天模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                buttonNight.setBackgroundResource(R.drawable.light);  // 设置白天图标
            } else {
                // 切换到黑夜模式
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




}