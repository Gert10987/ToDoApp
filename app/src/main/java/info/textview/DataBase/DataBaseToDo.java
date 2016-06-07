package info.textview.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2016-02-16.
 */
public class DataBaseToDo extends SQLiteOpenHelper {

    /*********
     * DataBase
     ******************/

    private static final String DATABASE_NAME = "ToDoApp.db";

    private static final int DATABASE_VESRION = 1;

    protected static final String DATABASE_ID = "id";

    public static final String COLUMN_TITLE = "TitleToDo";

    public static final String COLUMN_TIME = "TimeToDo";

    public static final String COLUMN_IS_DONE = "IsDoneToDo";

    public static final String COLUMN_TIME_INTEGER = "TimeIntegerToDo";

    long indexPosition;

    String TABLE_TODO_APP;

    /******
     * Constructor
     *************/
    public DataBaseToDo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESRION);
    }

    /******
     * Setters and getters
     **************/

    public void setNameOfTable(String nameOfTable) {

        this.TABLE_TODO_APP = nameOfTable;

    }

    public void setIndexPosition(long indexPosition){

        this.indexPosition = indexPosition;
    }

    /********
     * getNameOfTables method
     ***********/

    public ArrayList getNameOfTables() {

        ArrayList<String> arrTblNames = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }

        return arrTblNames;
    }


    /******
     * Create DataBase
     ***********/

    @Override
    public void onCreate(SQLiteDatabase db) {

      /*  db.execSQL(
                "create table " + TABLE_TODO_APP + "(" +
                        DATABASE_ID + " integer primary key not null, " +
                        COLUMN_TITLE + " text, " +
                        COLUMN_TIME + " text, " +
                        COLUMN_TIME_INTEGER + " integer, " +
                        COLUMN_IS_DONE + " integer);");
*/

    }

    public void newTable(String name) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(
                "create table " + name + "(" +
                        DATABASE_ID + " integer primary key not null, " +
                        COLUMN_TITLE + " text, " +
                        COLUMN_TIME + " text, " +
                        COLUMN_TIME_INTEGER + " integer, " +
                        COLUMN_IS_DONE + " integer);");


    }

    /********
     * UpDate DataBase
     ************/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + DATABASE_NAME);
        onCreate(db);


    }


    /****
     * Add Values method
     ************/

    public void addTitle(String title){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);

        db.insert(TABLE_TODO_APP, null, values);



    }

    public void addTime(String time) {


        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, time);


        db.update(TABLE_TODO_APP, values, DATABASE_ID + " = "+ indexPosition, null);



    }

    public void addTimeInteger(int timeInteger){

        int x = 0;

        SQLiteDatabase db = getWritableDatabase();

       /* ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_INTEGER, timeInteger);

        db.update(TABLE_TODO_APP, values, DATABASE_ID + " = " + indexPosition, null);*/


        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TIME_INTEGER + " FROM " + TABLE_TODO_APP
                + " WHERE " + DATABASE_ID + " = " + indexPosition, null);
        if(cursor.moveToFirst())
        {
            x = cursor.getInt(0);
        }

        x = x + timeInteger;

        db.execSQL("UPDATE " + TABLE_TODO_APP + " SET " + COLUMN_TIME_INTEGER
                + " = " + x + " WHERE " + DATABASE_ID + " = " + indexPosition);


    }

    public void addIsDoneValue(int isDone){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_DONE, isDone);


        db.update(TABLE_TODO_APP, values, DATABASE_ID + " = "+ indexPosition, null);


    }


    /**********
     * GetID method
     ***************/

    public List getId() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id as _id " + COLUMN_TITLE + COLUMN_TIME + " from " + TABLE_TODO_APP, null);
        ArrayList<String> list = new ArrayList<String>();

        int i = 0;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(i, cursor.getString(cursor.getColumnIndex("_id")));
                // do what ever you want here
                cursor.moveToNext();

                i++;
            }
        }


        return list;
    }


    public void deleteTable(String table) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    /****
     * method display values
     ***/

    public Cursor display() {


        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery
                ("SELECT id as _id, " + COLUMN_TITLE + " , "+ COLUMN_TIME_INTEGER + " , " + COLUMN_IS_DONE + " from " + TABLE_TODO_APP, null);

        if (cursor != null) {

            cursor.moveToFirst();

        }

        return cursor;


    }

    /************
     * delete record
     *************/

    public long deleteRecord(long word) {

        try {

            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_TODO_APP, DATABASE_ID + " = " + word, null);

        } catch (Exception e) {
            e.printStackTrace();

        }


        return word;
    }

    public int sumColumn(){

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT sum(" + COLUMN_TIME_INTEGER + ") " + "FROM " + TABLE_TODO_APP, null);
        if(cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }

        return 0;
    }


}






