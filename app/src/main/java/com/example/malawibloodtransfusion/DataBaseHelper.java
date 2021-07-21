
package com.example.malawibloodtransfusion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.widget.Toast.makeText;

public class DataBaseHelper extends SQLiteOpenHelper {

    public  static  final String DATABASE_NAME = "mbts_db.db";
    public  static  final String TABLE_HOSPITAL = "hos_table";



    public  static  final String COL_1 = "ID";
    public  static  final String COL_2 = "HOS_ID";
    public  static  final String COL_3 = "HOSPITAL_NAME";
    public  static  final String COL_4 = "EMAIL";
    public  static  final String COL_5 = "DISTRICT";
    public  static  final String COL_6 = "LICENCE";
    public  static  final String COL_7 = "LATITUDE";
    public  static  final String COL_8 = "LONGITUDE";
    public  static  final String COL_9 = "A_POSITIVE";
    public  static  final String COL_10 = "A_NEGATIVE";
    public  static  final String COL_11 = "B_POSITIVE";
    public  static  final String COL_12 = "B_NEGATIVE";
    public  static  final String COL_13 = "O_POSITIVE";
    public  static  final String COL_14 = "O_NEGATIVE";
    public  static  final String COL_15 = "AB_POSITIVE";
    public  static  final String COL_16 = "AB_NEGATIVE";








    public Context context;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null, 1);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_HOSPITAL + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,HOS_ID TEXT,HOSPITAL_NAME TEXT,EMAIL TEXT,DISTRICT TEXT,LICENCE TEXT,LATITUDE TEXT,LONGITUDE TEXT,A_POSITIVE TEXT,A_NEGATIVE TEXT,B_POSITIVE TEXT,B_NEGATIVE TEXT,O_POSITIVE TEXT,O_NEGATIVE TEXT,AB_POSITIVE TEXT,AB_NEGATIVE TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_HOSPITAL);

    }






    public boolean insertHospitals(String hos_id, String name,String district,String email ,String licence, String latitude, String longitude, String a_positive, String a_negative, String b_positive, String b_negative,String o_negative, String o_positive, String ab_postive, String ab_negative){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2,hos_id);
            contentValues.put(COL_3,name);
            contentValues.put(COL_4,email);
            contentValues.put(COL_5,district);
            contentValues.put(COL_6,licence);
            contentValues.put(COL_7,latitude);
            contentValues.put(COL_8,longitude);
            contentValues.put(COL_9,a_positive);
            contentValues.put(COL_10,a_negative);
            contentValues.put(COL_11,b_positive);
            contentValues.put(COL_12,b_negative);
            contentValues.put(COL_13,o_positive);
            contentValues.put(COL_14,o_negative);
            contentValues.put(COL_15,ab_postive);
            contentValues.put(COL_16,ab_negative);
            long result = db.insert(TABLE_HOSPITAL,null,contentValues);
            db.close();
            //To Check Whether Data is Inserted in DataBase
        return result != -1;

    }

    public boolean insertDataProf(String name, String user, int at_a_time, int how_many_a_day, int total,String professional){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,user);
        contentValues.put(COL_4,at_a_time);
        contentValues.put(COL_5,how_many_a_day);
        contentValues.put(COL_6,total);
        contentValues.put(COL_7,professional);
        long result = db.insert(TABLE_HOSPITAL,null,contentValues);
        db.close();
        //To Check Whether Data is Inserted in DataBase
        return result != -1;


    }






    public Cursor getAll_hospital_DataSelf()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from  " + TABLE_HOSPITAL +" ORDER BY ID DESC" ,null );
        return res;


    }

    public void wipe()
    {
        SQLiteDatabase db = this.getWritableDatabase();
     //   db.execSQL("DROP TABLE "+ TABLE_HOSPITAL);
        db.execSQL("CREATE TABLE " + TABLE_HOSPITAL + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,HOS_ID TEXT,HOSPITAL_NAME TEXT,EMAIL TEXT,DISTRICT TEXT,LICENCE TEXT,LATITUDE TEXT,LONGITUDE TEXT,A_POSITIVE TEXT,A_NEGATIVE TEXT,B_POSITIVE TEXT,B_NEGATIVE TEXT,O_POSITIVE TEXT,O_NEGATIVE TEXT,AB_POSITIVE TEXT,AB_NEGATIVE TEXT)");
    }

    public Cursor getAllDataPro()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(String.format("Select * from  %s ", TABLE_HOSPITAL),null );
        return res;


    }




    public Integer deleteDataPro(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        int i = db.delete(TABLE_HOSPITAL,"ID=?",new String[]{id});
        return i;
    }






    public Integer deleteDataPro_clean(String name)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        int i = db.delete(TABLE_HOSPITAL,"NAME=?",new String[]{name});
        return i;
    }





}
