/** *****************************************************************/
/** DatabaseHelper
 /**
 /** Database actions, CREATE, INSERT, UPDATE, DELETE
 /*******************************************************************/
package com.example.crazyalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String Database = "AlarmSystem.db";
    public static final String Table = "alarm_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "TONE";
    public static final String COL_5 = "COUNT";
    public static final String COL_6 = "STATUS";

    public DatabaseHelper(Context context) {
        super(context,Database,null,1);
    }

    /** onCreate method which create new table **/
    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+Table+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TIME TEXT,TONE TEXT,COUNT TEXT,STATUS TEXT)");
    }

    /** onUpgrade method which check weather table is existing or not, if exist it drop exist table **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table);
        onCreate(db);
    }

    /** insert method to insert new alarm details **/
    public boolean insertData(String name, String time, String tone, String count, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, tone);
        contentValues.put(COL_5, count);
        contentValues.put(COL_6,status);
        long result = db.insert(Table,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }
        else {
            return true;
        }

    }

    /** getting all alarm details from the database **/
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table, null);
        return res;
    }

    /** getting all alarm details which are in on state **/
    public Cursor getAllOnAlarms(String status){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from alarm_table where STATUS = ? ", new String[] {status});
        return res;
    }

    /** deleting alarm details from the database **/
    public Integer deleteData(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table, "ID = ?", new String[] {id});
    }

    /** update alarm details only for status update **/
    public boolean updateAlarmStatus(String id , String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6,status);
        int result = db.update(Table , contentValues,"ID = ?",new String[] {id});
        if(result > 0){
            return true;
        }
        else {
            return false;
        }
    }

    /** update alarm details **/
    public boolean updateAlarm(String id , String name, String time, String tone, String count,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, tone);
        contentValues.put(COL_5, count);
        contentValues.put(COL_6,status);
        int result = db.update(Table , contentValues,"ID = ?",new String[] {id});
        if(result > 0){
            return true;
        }
        else {
            return false;
        }
    }
}
