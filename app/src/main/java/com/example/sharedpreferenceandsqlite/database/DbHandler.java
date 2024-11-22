package com.example.sharedpreferenceandsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.sharedpreferenceandsqlite.model.UserModel;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "employee";
    private static final String TABLE_NAME = "user";
    private static final int DATABASE_VERSION = 1;
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "password";

    public DbHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + EMAIL_COL + " TEXT, "
                + PASSWORD_COL + " TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    /** Method to add user to database */
    public boolean addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, name);
        contentValues.put(EMAIL_COL, email);
        contentValues.put(PASSWORD_COL, password);

        long l = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        return l > 0;
    }

    /** Method to validate user */
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " =? AND " + PASSWORD_COL + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;
    }

    /** Method to get logged-in user details */
    public ArrayList<UserModel> getLoggedInUserDetails(String profile_email) {
        ArrayList<UserModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{profile_email});

        if (cursor.moveToFirst()) {
            UserModel userModel = new UserModel();
            userModel.setName(cursor.getString(1));  // Index 1 is 'name'
            userModel.setEmail(cursor.getString(2)); // Index 2 is 'email'
            userModel.setPassword(cursor.getString(3)); // Index 3 is 'password'
            arrayList.add(userModel);
        }
        cursor.close();
        return arrayList;
    }

    /** Method to update user details */
    public boolean updateUser(String email, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, name);
        contentValues.put(PASSWORD_COL, password);

        int rowsUpdated = db.update(TABLE_NAME, contentValues, EMAIL_COL + " = ?", new String[]{email});
        db.close();
        return rowsUpdated > 0;
    }

    /** Method to get all user details */
    public ArrayList<UserModel> getAllUserDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserModel> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setName(cursor.getString(1));  // Index 1 is 'name'
                userModel.setEmail(cursor.getString(2)); // Index 2 is 'email'
                userModel.setPassword(cursor.getString(3)); // Index 3 is 'password'

                arrayList.add(userModel);
            } while (cursor.moveToNext()); // Correct iteration method
        }
        cursor.close();
        return arrayList;
    }

    /** Method to delete user */
    public boolean deleteUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, EMAIL_COL + " = ?", new String[]{email});
        db.close();
        return rowsDeleted > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Correct table reference
        onCreate(db);
    }
}
