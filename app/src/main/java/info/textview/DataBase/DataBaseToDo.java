package info.textview.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    String TABLE_TODO_APP = "ToDo";

    protected static final String DATABASE_ID = "id";

    public static final String COLUMN_TITLE = "TitleToDo";

    public static final String COLUMN_TIME = "TimeToDo";

    public static final String COLUMN_IS_DONE = "IsDoneToDo";

    public static final String COLUMN_TIME_INTEGER = "TimeIntegerToDo";

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

        db.execSQL(
                "create table " + TABLE_TODO_APP + "(" +
                        DATABASE_ID + " integer primary key not null, " +
                        COLUMN_TITLE + " text, " +
                        COLUMN_TIME + " text, " +
                        COLUMN_TIME_INTEGER + " text, " +
                        COLUMN_IS_DONE + " integer);");
    }

    public void newTable(String name) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(
                "create table " + TABLE_TODO_APP + "(" +
                        DATABASE_ID + " integer primary key not null, " +
                        COLUMN_TITLE + " text, " +
                        COLUMN_TIME + " text, " +
                        COLUMN_TIME_INTEGER + " text, " +
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

    public long addTime(String time, int timeInteger, int isDone) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_TIME_INTEGER, timeInteger);
        values.put(COLUMN_IS_DONE, isDone);


        long insertId = db.update(TABLE_TODO_APP, values, DATABASE_ID + " = "+ 2, null);

        return insertId;
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
        cursor.close();


        return list;
    }


    public void deleteTable(String table) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    /****
     * method display values
     ***/

    public Cursor display(String table) {


        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery
                ("SELECT id as _id, " + COLUMN_TITLE + " , "+ COLUMN_TIME + " , " + COLUMN_IS_DONE + " from " + TABLE_TODO_APP, null);

        if (cursor != null) {

            cursor.moveToFirst();

        }

        return cursor;


    }

    /************
     * delete record
     *************/

    public long DeleteRecord(long word) {

        try {

            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_TODO_APP, DATABASE_ID + " = " + word, null);

        } catch (Exception e) {
            e.printStackTrace();

        }


        return word;
    }

    public String getColumnTitle() {
        return COLUMN_TITLE;
    }

    public String getColumnTime() {
        return COLUMN_TIME;
    }

    public String getColumnIsDone() {
        return COLUMN_IS_DONE;
    }
    public String getColumnTimeInteger() {
        return COLUMN_TIME_INTEGER;
    }

}






