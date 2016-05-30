package info.textview.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.sql.Blob;
import java.sql.SQLException;
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

    String TABLE_KREBS = "Table";

    protected static final String DATABASE_ID = "id";

    protected static final String COLUMN_POLISHWORD = "Title";

    protected static final String COLUMN_ENGLISHWORD = "Time";

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

        this.TABLE_KREBS = nameOfTable;

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
                "create table " + TABLE_KREBS + "(" +
                        DATABASE_ID + " integer primary key not null, " +
                        COLUMN_POLISHWORD + " text, " +
                        COLUMN_ENGLISHWORD + " text);");
    }

    public void newTable(String name) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("create table " + name + "(" +
                DATABASE_ID + " integer primary key not null, " +
                COLUMN_POLISHWORD + " text, " +
                COLUMN_ENGLISHWORD + " text);");

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

    public long addWord(String polishWord, String englishWord) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POLISHWORD, polishWord);
        values.put(COLUMN_ENGLISHWORD, englishWord);


        long insertId = db.insert(TABLE_KREBS, null, values);

        return insertId;
    }

    /**********
     * GetID method
     ***************/

    public List getId() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id as _id , PolishWord, EnglishWord from " + TABLE_KREBS + " order by EnglishWord ASC", null);
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

    public Cursor wyswietl(String table) {


        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery
                ("SELECT id as _id , PolishWord, EnglishWord from " + TABLE_KREBS + " order by EnglishWord ASC", null);

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
            db.delete(TABLE_KREBS, DATABASE_ID + " = " + word, null);

        } catch (Exception e) {
            e.printStackTrace();

        }


        return word;
    }

    public String getColumnPolishword() {
        return COLUMN_POLISHWORD;
    }

    public String getColumnEnglishword() {
        return COLUMN_ENGLISHWORD;
    }
}






