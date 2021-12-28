package com.example.salary.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CompanyDataManager extends SQLiteOpenHelper {

    private static String TABLE_NAME = "company";
    private static String DB_NAME = "Salary.db";
    private static final int DB_VERSION = 1;

    private static final String[] companyName = {"한국수자원공사", "한국예탁결제원", "한국자산관리공사", "부산환경공단", "서울교통공사"};
    private static final String[] companyAddress = {"대전광역시", "부산광역시", "부산광역시", "부산광역시", "서울특별시"};

    private final String TAG_COMPANY_NAME = "name";
    private final String TAG_COMPANY_ADDRESS = "address";

    static SQLiteDatabase companyDB = null;
    private static Context mContext = null;

    private static CompanyDataManager companyDBmanager = null;

    public static CompanyDataManager getInstance(Context context) {
        if (companyDBmanager == null) {
            companyDBmanager = new CompanyDataManager(context, DB_NAME, null, DB_VERSION);
        }

        return companyDBmanager;
    }

    private CompanyDataManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        initdabase();
    }

    public void initdabase() {
//        for (int i=0; i<companyName.length; i++) {
//            companyDB.execSQL("INSERT INTO " + TABLE_NAME + " (companyName, companyAddress) Values ('" + companyName[i] + "' , '" + companyAddress[i] + "');");
//        }
    }

//    public static void addCompany() {
//        companyDB = SQLiteDatabase.openOrCreateDatabase("companyInfo", MODE_PRIVATE, null);
//
//
//        for (int i=0; i<companyName.length; i++) {
//            companyDB.execSQL("INSERT INTO " + TABLE_NAME + " (comapnyName, companyAddress) Values ('" + companyName[i] + "' , '" + companyAddress[i] + "');");
//        }
//
//        companyDB.close();
//    }

    public static String[] getCompanyList() {
        List<String> companys = new ArrayList<String>();

        SQLiteDatabase ReadDB = mContext.openOrCreateDatabase(DB_NAME, mContext.MODE_PRIVATE, null);

        Cursor c = ReadDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String companyName = c.getString(c.getColumnIndex("companyName"));
                    companys.add(companyName);

                } while (c.moveToNext());
            }
        }

        String[] companyList = new String[companys.size()];
        for (int i=0; i<companys.size(); i++) {
            companyList[i] = companys.get(i);
        }
        return companyList;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (companyName VARCHAR(20), companyAddress VARCHAR(30));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
