package com.example.lab01_minhtriet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        // Kiểm tra các trường có bị bỏ trống không
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp không
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đăng ký người dùng với email và mật khẩu
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Gửi email xác nhận cho người dùng
                        mAuth.getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(verifyTask -> {
                                    if (verifyTask.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Verification email sent. Please verify your email before logging in.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
