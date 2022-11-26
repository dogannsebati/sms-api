package com.example.mytriggersms.DBConnection;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.mytriggersms.entity.Sms;

import java.util.ArrayList;
import java.util.List;

public class DAOsms extends SQLiteOpenHelper {
    private static final String dbconnection_name = "sms_pull";
    private static final String sms_table_name = "tbl_sms";
    private static final int dbconnection_version = 1;
    private static final String LOG = "DatabaseHelper";

    public DAOsms(@Nullable Context context) {
        super(context, dbconnection_name, null, dbconnection_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table
        String sql_smsTableCreate = "CREATE TABLE " + sms_table_name + " (ID integer PRIMARY KEY, status INTEGER, password TEXT, smsTitle TEXT)";
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

    public long InsertSms(Sms sms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("smsTitle", sms.getSmsTitle());
        cv.put("password", sms.getPassword());
        cv.put("status", sms.getStatus());
        long id = db.insert(sms_table_name, null, cv);
        return id;
    }

    public List<Sms> getsmsInfo(long smsId) {
        List<Sms> smsList = new ArrayList<Sms>();
        String selectQuery = "SELECT  * FROM " + sms_table_name + " WHERE " + "ID" + " = " + smsId;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Sms sms = new Sms();
                sms.setID(c.getInt(c.getColumnIndex("ID")));
                sms.setSmsTitle((c.getString(c.getColumnIndex("smsTitle"))));
                sms.setPassword(c.getString(c.getColumnIndex("password")));
                sms.setStatus(c.getString(c.getColumnIndex("status")));

                smsList.add(sms);
            } while (c.moveToNext());
        }

        return smsList;

    }

    public int updateSms(Sms sms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("smsTitle", sms.getSmsTitle());
        values.put("password", sms.getPassword());
        values.put("status", sms.getStatus());

        Log.d("sms.getID() ", sms.getID() + "");

        // updating row
        return db.update(sms_table_name, values, "ID = 1",
                new String[]{/*String.valueOf(sms.getID())*/});
    }

    public void deleteSms(Integer smsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(sms_table_name, "ID" + " = ?",
                new String[]{String.valueOf(smsId)});
    }
}
