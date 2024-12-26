package com.example.testing1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testing1.Activity.MainActivity;
import com.example.testing1.Helper.MenuDatabaseHelper;
import com.example.testing1.R;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginActionButton;

    private MenuDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi View
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginActionButton = findViewById(R.id.loginActionButton);

        // Inisialisasi DatabaseHelper
        databaseHelper = new MenuDatabaseHelper(this);

        // Set OnClickListener untuk Login Button
        loginActionButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                // Cek user di database
                boolean isValidUser = databaseHelper.checkUser(email, password);

                if (isValidUser) {
                    // Jika login berhasil
                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    // Pindah ke MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Jika login gagal
                    Toast.makeText(LoginActivity.this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Jika input kosong
                Toast.makeText(LoginActivity.this, "Harap isi Email dan Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
