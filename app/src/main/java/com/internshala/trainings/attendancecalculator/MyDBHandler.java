package com.internshala.trainings.attendancecalculator;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.widget.Toast;

import java.sql.Date;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDB.db";
    public static final String TABLE_MEMBERS = "members";
    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String USER_ID = "user_id";
    public static final String PRESENT_TOTAL= "presentTotal";
    public static final String ABSENT_TOTAL = "absentTotal";
    public static final String DATE = "Date";


    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE " + TABLE_MEMBERS +"( user_id INT PRIMARY KEY , " +
                " user_name VARCHAR(30), email VARCHAR(50), flag INT(2) DEFAULT 0);";
        db.execSQL(query);
        String query1 = "CREATE TABLE " + TABLE_ATTENDANCE + "(" +
               USER_ID + " INT NOT NULL ," +
                PRESENT_TOTAL + " int ," + ABSENT_TOTAL + " INT ,"+ DATE +" DATE "+ ");";
        db.execSQL(query1);
    }
    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    //Add a new row to the database
    public void addMember(Model model){
        SQLiteDatabase db = getWritableDatabase();
        String query3="INSERT INTO "+TABLE_MEMBERS+" VALUES( "+model.getUserID()+" , "+"'"+model.getUserName()+"'"+" , "
                +"'"+model.getEmail()+"'"+" , 1 );";
        db.execSQL(query3);
        db.close();
    }
    public int checkMember(){
       int flag;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
        recordSet.moveToLast();
        flag=recordSet.getInt(recordSet.getColumnIndex("flag"));}
        else
        flag=0;
        db.close();
        return flag;

    }
    public int getUserId(){
        int user_id;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            user_id=recordSet.getInt(recordSet.getColumnIndex("user_id"));}
        else
            user_id=0;
        db.close();
        return user_id;
    }
    public String getUserName(){
        String user_name;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            user_name=recordSet.getString(recordSet.getColumnIndex("user_name"));}
        else
            user_name=null;
        db.close();
        return user_name;
    }
    public void addSubjectdb(String subNme){
        SQLiteDatabase db = getWritableDatabase();
        String query ="ALTER TABLE "+TABLE_ATTENDANCE+" ADD "+"'"+subNme+"'"+" int ;";
        db.execSQL(query);
        db.close();
    }
    public void presentcounting(Date date ,int count,String sub){
        SQLiteDatabase db = getWritableDatabase();
        String query ="UPDATE "+TABLE_ATTENDANCE+" SET presentTotal ="+count+"whe"+"'"+" int ;"; 
        db.execSQL(query);
        db.close();
    }
    /*//Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + productName + "\";");
    }

    // this is goint in record_TextView in the Main activity.*/
   /* public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("productname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("productname"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }*/

}