package com.example.sanyagavrsam;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){ dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_YEAR};
        return	database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Workers> getUsers(){
        ArrayList<Workers> workers = new ArrayList<>();
        Cursor cursor = getAllEntries();

        while (cursor.moveToNext()){ @SuppressLint("Range") int ID =
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") String Name =
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") int Year =
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
            workers.add(new Workers(ID, Name, Year));
        }
        cursor.close();
        return	workers;
    }

    public long getCount(){

        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public Workers getUser(long ID){ Workers workers = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE,
                DatabaseHelper.COLUMN_ID);;
        Cursor cursor = database.rawQuery(query, new String[]{
                String.valueOf(ID)});
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String Name =
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") int Year =
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
            workers = new Workers(ID, Name, Year);
        }
        cursor.close();
        return	workers;
    }
    public long insert(Workers workers){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, workers.getName()); cv.put(DatabaseHelper.COLUMN_YEAR,
                workers.getYear());
        return	database.insert(DatabaseHelper.TABLE, null, cv);
    }
    public long delete(long userId){
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }
    public long update(Workers workers){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + workers.getId();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME,
                workers.getName());
        cv.put(DatabaseHelper.COLUMN_YEAR, workers.getYear());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}