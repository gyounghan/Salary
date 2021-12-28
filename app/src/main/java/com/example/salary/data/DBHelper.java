package com.example.salary.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "salary.db";
    private static final String TABLE_NAME = "Company";
    private static final String[] company_name = {"한국수자원공사", "한국예탁결제원", "한국자산관리공사", "부산환경공단", "서울교통공사"};
    private static final String[] company_address = {"대전광역시", "부산광역시", "부산광역시", "부산광역시", "서울특별시"};

//    private static DBHelper dbHelper;
//    private static Context mContext = null;

    public DBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

//
//    public static DBHelper getInstance(Context context) {
//        if (dbHelper == null) {
//            dbHelper = new DBHelper(context, 1);
//            mContext = context;
//        }
//
//        return dbHelper;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (companyName VARCHAR(20), companyAddress VARCHAR(30));");
        Log.e("[DBHelper]" , "onCreate");
        initDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void initDB(SQLiteDatabase db) {
        for (int i=0; i<company_name.length; i++) {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (companyName, companyAddress) Values ('" + company_name[i] + "' , '" + company_address[i] + "');");
        }
    }

    public void insert(String companyName, String companyAddress) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " (companyName, companyAddress) Values ('" + companyName + "' , '" + companyAddress + "');");
    }

    public void update(String companyName, String companyAddress) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Company SET companyName = " + companyName + ", companyAddress = '" + companyAddress + "'" + " WHERE companyName = '" + companyName + "'");
        db.close();
    }

    public void delete(String companyName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete Company WHERE companyName = '" + companyName + "'");
        db.close();
    }

    public String getCompanyInfo() {
        SQLiteDatabase db = getReadableDatabase();
        String companyInfo = "";

        Cursor cursor = db.rawQuery("SELECT companyName FROM Company", null);
        while (cursor.moveToNext()) {
            companyInfo += cursor.getString(0) + " ";
        }

        return companyInfo;
    }
}
