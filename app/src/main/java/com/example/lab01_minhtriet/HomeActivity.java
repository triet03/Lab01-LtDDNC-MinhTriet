package com.example.lab01_minhtriet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  // Liên kết với layout vừa tạo
        setTitle("Home");  // Đặt tiêu đề cho Activity là "Home"
    }
}
