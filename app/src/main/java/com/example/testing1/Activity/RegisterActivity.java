package com.example.testing1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testing1.Helper.MenuDatabaseHelper; // Pastikan menggunakan nama kelas yang benar
import com.example.testing1.R;

public class RegisterActivity extends AppCompatActivity {

    EditText registerEmail, registerPassword;
    Button registerActionButton;
    MenuDatabaseHelper dbHelper; // Deklarasi helper database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi database helper
        dbHelper = new MenuDatabaseHelper(this);

        // Inisialisasi view
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.passwordInput);
        registerActionButton = findViewById(R.id.registerActionButton);

        registerActionButton.setOnClickListener(v -> {
            String email = registerEmail.getText().toString().trim();
            String password = registerPassword.getText().toString().trim();

            // Validasi input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan data ke database
            boolean isRegistered = dbHelper.addUser(email, password);

            if (isRegistered) {
                Toast.makeText(RegisterActivity.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class); // Pastikan LoginActivity tersedia
                startActivity(intent);
                finish(); // Menutup aktivitas saat ini
            } else {
                Toast.makeText(RegisterActivity.this, "Email sudah terdaftar atau terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

