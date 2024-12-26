package com.example.testing1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testing1.Domain.ItemDomain;
import com.example.testing1.R;

public class AddMenuActivity extends AppCompatActivity {

    private EditText inputTitle, inputDescription, inputFee;
    private Spinner spinnerCategory;
    private Switch switchAvailable;
    private Button buttonAddPic, buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        // Inisialisasi elemen UI
        inputTitle = findViewById(R.id.inputUpdateTitle);
        inputDescription = findViewById(R.id.inputUpdateDescription);
        inputFee = findViewById(R.id.inputUpdateFee);
        spinnerCategory = findViewById(R.id.spinnerUpdateCategory);
        switchAvailable = findViewById(R.id.switchUpdateAvailable);
        buttonAddPic = findViewById(R.id.buttonAddPic);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set listener untuk tombol "Add Picture"
        buttonAddPic.setOnClickListener(v -> {
            // Logika untuk menambahkan gambar (bisa membuka file picker untuk memilih gambar)
            Toast.makeText(AddMenuActivity.this, "Add Picture clicked", Toast.LENGTH_SHORT).show();
        });

        // Set listener untuk tombol "Add Menu"
        buttonSubmit.setOnClickListener(v -> {
            // Ambil data dari input
            String title = inputTitle.getText().toString();
            String description = inputDescription.getText().toString();
            String feeString = inputFee.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();
            boolean isAvailable = switchAvailable.isChecked();

            // Validasi input
            if (title.isEmpty() || description.isEmpty() || feeString.isEmpty()) {
                Toast.makeText(AddMenuActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Mengkonversi fee menjadi Double
            double fee = Double.parseDouble(feeString);

            // Buat objek ItemDomain baru
            ItemDomain newItem = new ItemDomain(title, description, description, fee, category, isAvailable);

            // Kirim objek ke MainActivity menggunakan Intent
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newItem", newItem);  // Mengirimkan ItemDomain ke MainActivity
            setResult(RESULT_OK, resultIntent);  // Mengatur hasil Activity
            finish();  // Menutup AddMenuActivity dan kembali ke MainActivity
        });
    }
}
