package com.example.testing1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testing1.Helper.MenuDatabaseHelper;
import com.example.testing1.Helper.MenuDatabaseHelper;
import com.example.testing1.R;
import com.example.testing1.Activity.RegisterActivity;

public class HomeLoginActivity extends AppCompatActivity {

    Button loginButton, registerButton;
    MenuDatabaseHelper dbHelper; // Deklarasi helper database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);

        // Inisialisasi database helper
        dbHelper = new MenuDatabaseHelper(this);

        // Inisialisasi Tombol
        loginButton = findViewById(R.id.loginActionButton);
        registerButton = findViewById(R.id.registerActionButton);

        // Navigasi ke halaman login
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeLoginActivity.this, LoginActivity.class); // Pastikan path LoginActivity benar
            startActivity(intent);
        });

        // Navigasi ke halaman register
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeLoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
