package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 应用 Edge-to-Edge 模式
        EdgeToEdge.enable(this);

        // 设置窗口插图的监听器
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 获取 ImageView 控件
        ImageView splashLogo = findViewById(R.id.splash_logo);

        // 加载动画
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // 启动动画
        splashLogo.startAnimation(fadeIn);

        // 设置延时，动画结束后跳转到主界面
        new Handler().postDelayed(() -> {
            // 启动主界面
            Intent intent = new Intent(SplashActivity.this, BookShelf.class);
            startActivity(intent);
            finish();
        }, 2000);  // 等待 2 秒钟，动画结束后跳转
    }
}
