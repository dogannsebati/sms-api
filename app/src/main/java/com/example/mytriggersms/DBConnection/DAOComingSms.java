package com.example.mytriggersms.DBConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mytriggersms.entity.ComingSms;

import java.util.ArrayList;
import java.util.List;

public class DAOComingSms extends SQLiteOpenHelper {
    private static final String dbconnection_name = "coming_sms_pull";
    private static final String sms_table_name = "tbl_comign_sms";
    private static final int dbconnection_version = 1;
    private static final String LOG = "DatabaseHelper";

    public DAOComingSms(@Nullable Context context) {
        super(context, dbconnection_name, null, dbconnection_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table
        String sql_smsTableCreate = "CREATE TABLE " + sms_table_name + " (ID integer PRIMARY KEY, sender TEXT, content TEXT,time TEXT)";
        db.execSQL(sql_smsTableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + sms_table_name);
        onCreate(db);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public long dabatableCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, sms_table_name);
        return count;
    }

    public long InsertComingSms(ComingSms sms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("sender", sms.getSender());
        cv.put("content", sms.getContent());
        long id = db.insert(sms_table_name, null, cv);
        return id;
    }


    public List<ComingSms> getsms() {
        List<ComingSms> smsList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + sms_table_name;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ComingSms sms = new ComingSms();
                sms.setID(c.getInt(c.getColumnIndex("ID")));
                sms.setSender((c.getString(c.getColumnIndex("sender"))));
                sms.setContent(c.getString(c.getColumnIndex("content")));
                // adding to user list
                smsList.add(sms);
            } while (c.moveToNext());
        }
        return smsList;
    }

    public void deleteSms() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(sms_table_name, "",
                new String[]{});
    }

}
