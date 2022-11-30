package com.example.myproject.EnteringApp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="baza.db";
    public static final String TABLE_NAME ="baza";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String COL_4 = "age";
    public static final String COL_5 = "email";
    public static final String COL_6 = "doctor";
    public static final String COL_7 = "medicine";
    public static final String COL_8 = "nextVisit";
    public static final String COL_9 = "Hospital";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       /* sqLiteDatabase.execSQL("CREATE TABLE userRegister (COL_1 INTEGER PRIMARY  KEY AUTOINCREMENT, COL_2 TEXT, COL_3 TEXT, COL_4 TEXT, COL_5 TEXT , COL_6 TEXT, COL_7 TEXT, COL_8 TEXT, COL_9 TEXT)");*/


        String sqlstatement = "CREATE TABLE " + TABLE_NAME + "( " +
                COL_1 + " integer primary key autoincrement, " +
                COL_2 + " text, " +
                COL_3 + " text, " +
                COL_4 + " text, " +
                COL_5 +  " text, " +
                COL_6 + " text, " +
                COL_7 + " text, " +
                COL_8 + " text, " +
                COL_9 + " text ) ";

        sqLiteDatabase.execSQL(sqlstatement);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password, String age, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        contentValues.put("age",age);
        contentValues.put("email",email);
        contentValues.put("doctor","0");
        contentValues.put("medicine","0");
        contentValues.put("nextVisit","0");
        contentValues.put("Hospital","0");
        long res = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return  res;
    }
    public long addUser2(String id, String doctor, String medicine, String nextVisit, String Hospital){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doctor",doctor);
        contentValues.put("medicine",medicine);
        contentValues.put("nextVisit",nextVisit);
        contentValues.put("Hospital",Hospital);
        long res = db.update(TABLE_NAME,contentValues,COL_1 +" = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return  res;
    }

    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        int count = cursor.getCount();

        cursor.close();

        db.close();

        if(count>0) {
            Log.e("Count true:", String.valueOf(cursor.getCount()));
            return true;
        }else {
            Log.e("Count false:", String.valueOf(cursor.getCount()));
            return false;
        }
    }
    public boolean checkUser2(String id){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_1 + "=?" ;
        String[] selectionArgs = { id };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public String[] getUser(String name) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_1, COL_2, COL_4, COL_5},
                COL_2+ "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        Log.d("Count",String.valueOf(cursor.getCount()));
        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        String[] results ={
                cursor.getString(cursor.getColumnIndex(COL_1)),
                cursor.getString(cursor.getColumnIndex(COL_2)),
                cursor.getString(cursor.getColumnIndex(COL_4)),
                cursor.getString(cursor.getColumnIndex(COL_5))};

        // close the db connection
        cursor.close();

        return results;
    }
    public String[] getUser2(String name) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_6, COL_7, COL_8, COL_9},
                COL_2+ "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        String[] results ={
                cursor.getString(cursor.getColumnIndex(COL_6)),
                cursor.getString(cursor.getColumnIndex(COL_7)),
                cursor.getString(cursor.getColumnIndex(COL_8)),
                cursor.getString(cursor.getColumnIndex(COL_9))};

        // close the db connection
        cursor.close();

        return results;
    }

}
