package com.example.testing1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testing1.Adaptor.ItemAdapter;
import com.example.testing1.Domain.ItemDomain;
import com.example.testing1.Helper.MenuDatabaseHelper;
import com.example.testing1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ItemDomain> itemList;
    private ArrayList<ItemDomain> filteredList;
    private ItemAdapter adapter;
    private MenuDatabaseHelper dbHelper;

    // ActivityResultLauncher untuk menerima data dari AddMenuActivity
    private ActivityResultLauncher<Intent> addMenuActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Ambil item baru dari Intent
                    ItemDomain newItem = (ItemDomain) result.getData().getSerializableExtra("newItem");
                    if (newItem != null) {
                        // Simpan item ke database
                        long newId = dbHelper.insertMenuItem(newItem, newItem.isAvailable());
                        newItem.setId((int) newId); // Tetapkan ID yang baru dibuat ke item

                        // Tambahkan item ke daftar
                        itemList.add(newItem);
                        filteredList.add(newItem);

                        // Perbarui RecyclerView
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Menu added!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi database
        dbHelper = new MenuDatabaseHelper(this);

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi data
        initializeData();

        // Mengatur adapter ke RecyclerView
        filteredList = new ArrayList<>(itemList); // Awalnya, tampilkan semua item
        adapter = new ItemAdapter(this, filteredList, new ItemAdapter.OnItemActionListener() {
            @Override
            public void onViewClicked(ItemDomain item) {
                Toast.makeText(MainActivity.this, "View: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdateClicked(ItemDomain item) {
                updateItem(item);
            }

            @Override
            public void onDeleteClicked(ItemDomain item) {
                deleteItem(item);
            }
        });
        recyclerView.setAdapter(adapter);

        // Inisialisasi spinner untuk filter kategori
        Spinner spinnerCategory = findViewById(R.id.spinnerUpdateCategory);
        setupCategorySpinner(spinnerCategory);

        // Menambahkan event untuk FAB
        FloatingActionButton fabAddMenu = findViewById(R.id.fabAddMenu);
        fabAddMenu.setOnClickListener(v -> {
            // Membuka AddMenuActivity untuk menambah menu baru
            Intent intent = new Intent(MainActivity.this, AddMenuActivity.class);
            addMenuActivityResultLauncher.launch(intent);
        });
    }

    // Inisialisasi data dari database
    private void initializeData() {
        itemList = dbHelper.getAllMenuItems();
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
    }

    // Menyiapkan Spinner untuk filter kategori
    private void setupCategorySpinner(Spinner spinner) {
        String[] categories = {"All", "Food", "Drink", "Cakes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedCategory = categories[position];
                filterItemsByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tidak ada tindakan yang diperlukan
            }
        });
    }

    // Filter item berdasarkan kategori
    private void filterItemsByCategory(String category) {
        if (category.equals("All")) {
            filteredList.clear();
            filteredList.addAll(itemList);
        } else {
            filteredList.clear();
            filteredList.addAll(itemList.stream()
                    .filter(item -> item.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList()));
        }
        adapter.notifyDataSetChanged();
    }

    // Hapus item dari database dan daftar
    private void deleteItem(ItemDomain item) {
        // Hapus item dari database
        dbHelper.deleteMenuItem(item.getId());

        // Hapus item dari daftar sementara
        itemList.remove(item);
        filteredList.remove(item);

        // Perbarui RecyclerView
        adapter.notifyDataSetChanged();

        // Tampilkan pesan sukses
        Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private void updateItem(ItemDomain item) {
        // Buat Intent untuk membuka UpdateMenuActivity
        Intent intent = new Intent(this, UpdateMenuActivity.class);

        // Masukkan data item ke dalam Intent
        intent.putExtra("ID", String.valueOf(item.getId())); // Mengirimkan ID item
        intent.putExtra("itemToEdit", item); // Mengirimkan objek ItemDomain

        // Buka UpdateMenuActivity
        startActivity(intent);
    }
}
