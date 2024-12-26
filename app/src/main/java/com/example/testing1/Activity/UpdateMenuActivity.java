package com.example.testing1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testing1.Domain.ItemDomain;
import com.example.testing1.Helper.MenuDatabaseHelper;
import com.example.testing1.R;
import java.util.Arrays;

public class UpdateMenuActivity extends AppCompatActivity {

    private EditText inputTitle, inputDescription, inputFee;
    private Spinner spinnerCategory;
    private Switch switchAvailable;
    private Button buttonUpdate;
    private MenuDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        dbHelper = new MenuDatabaseHelper(this);

        // Inisialisasi elemen UI
        inputTitle = findViewById(R.id.inputUpdateTitle);
        inputDescription = findViewById(R.id.inputUpdateDescription);
        inputFee = findViewById(R.id.inputUpdateFee);
        spinnerCategory = findViewById(R.id.spinnerUpdateCategory);
        switchAvailable = findViewById(R.id.switchUpdateAvailable);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // Ambil data dari Intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        ItemDomain itemToEdit = (ItemDomain) intent.getSerializableExtra("itemToEdit");

        // Tampilkan data ke dalam form edit
        if (itemToEdit != null) {
            inputTitle.setText(itemToEdit.getTitle());
            inputDescription.setText(itemToEdit.getDescription());
            inputFee.setText(String.valueOf(itemToEdit.getFee()));

            String[] categoriesArray = getResources().getStringArray(R.array.category_array);
            int position = Arrays.asList(categoriesArray).indexOf(itemToEdit.getCategory());
            if (position >= 0) {
                spinnerCategory.setSelection(position);
            }
            switchAvailable.setChecked(itemToEdit.isAvailable());
        }

        // Tombol update untuk menyimpan perubahan
        buttonUpdate.setOnClickListener(v -> {
            if (id == null || id.isEmpty()) {
                Toast.makeText(this, "Invalid Menu ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ambil data baru dari form
            String updatedTitle = inputTitle.getText().toString();
            String updatedDescription = inputDescription.getText().toString();
            double updatedFee;
            try {
                updatedFee = Double.parseDouble(inputFee.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid fee input", Toast.LENGTH_SHORT).show();
                return;
            }
            String updatedCategory = spinnerCategory.getSelectedItem().toString();
            boolean updatedAvailable = switchAvailable.isChecked();

            if (updatedTitle.isEmpty() || updatedDescription.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Debug data yang diinput
            Log.d("UpdateMenuActivity", "Updated Title: " + updatedTitle);
            Log.d("UpdateMenuActivity", "Updated Description: " + updatedDescription);
            Log.d("UpdateMenuActivity", "Updated Fee: " + updatedFee);
            Log.d("UpdateMenuActivity", "Updated Category: " + updatedCategory);
            Log.d("UpdateMenuActivity", "Updated Available: " + updatedAvailable);

            itemToEdit.setTitle(updatedTitle);
            itemToEdit.setDescription(updatedDescription);
            itemToEdit.setFee(updatedFee);
            itemToEdit.setCategory(updatedCategory);
            itemToEdit.setAvailable(updatedAvailable);

            boolean isUpdated = dbHelper.updateMenu(id, itemToEdit);

            Log.d("UpdateMenuActivity", "Update result: " + isUpdated);

            if (isUpdated) {
                Toast.makeText(this, "Menu updated successfully", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedItem", itemToEdit);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update menu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
