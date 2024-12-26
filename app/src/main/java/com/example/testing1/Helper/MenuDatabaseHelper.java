package com.example.testing1.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.testing1.Domain.ItemDomain;

import java.util.ArrayList;

public class MenuDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AppDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Table: Menu
    private static final String TABLE_MENU = "menu";
    private static final String COLUMN_MENU_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PIC = "pic";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FEE = "fee";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_AVAILABLE = "available";

    public MenuDatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // Create Menu table
        String createMenuTable = "CREATE TABLE " + TABLE_MENU + " (" +
                COLUMN_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_PIC + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_FEE + " REAL NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COLUMN_AVAILABLE + " INTEGER DEFAULT 1)";
        db.execSQL(createMenuTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        onCreate(db);
    }

    // Methods for Users Table
    public boolean addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        Log.d("DatabaseHelper", "Checking user: " + email + ", exists: " + exists);
        cursor.close();
        return exists;
    }

    // Methods for Menu Table
    public long insertMenuItem(ItemDomain item, boolean isAvailable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, item.getTitle());
        values.put(COLUMN_PIC, item.getPic());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_FEE, item.getFee());
        values.put(COLUMN_CATEGORY, item.getCategory());
        values.put(COLUMN_AVAILABLE, item.isAvailable() ? 1 : 0);

        long result = db.insert(TABLE_MENU, null, values);
        Log.d("DatabaseHelper", "Inserted new menu item: " + result);
        return result;
    }

    public ArrayList<ItemDomain> getAllMenuItems() {
        ArrayList<ItemDomain> menuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENU, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ItemDomain item = new ItemDomain(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_FEE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVAILABLE)) == 1
                );
                menuList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return menuList;
    }

    public void deleteMenuItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_MENU, COLUMN_MENU_ID + " = ?", new String[]{String.valueOf(id)});
        Log.d("DatabaseHelper", "Deleted menu item with ID " + id + ": " + rowsDeleted + " rows deleted.");
    }

    public boolean updateMenu(String id, ItemDomain item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, item.getTitle());
        values.put(COLUMN_PIC, item.getPic());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_FEE, item.getFee());
        values.put(COLUMN_CATEGORY, item.getCategory());
        values.put(COLUMN_AVAILABLE, item.isAvailable() ? 1 : 0);

        int rowsUpdated = db.update(TABLE_MENU, values, COLUMN_MENU_ID + " = ?", new String[]{String.valueOf(id)});
        Log.d("DatabaseHelper", "Updated menu item with ID " + id + ": " + rowsUpdated + " rows updated.");
        return rowsUpdated > 0;
    }
}
