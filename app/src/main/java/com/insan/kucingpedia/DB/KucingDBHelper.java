package com.insan.kucingpedia.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KucingDBHelper extends SQLiteOpenHelper {

    private static final String CREATE =
            "CREATE TABLE " + KucingDB.KucingEntry.TB_NAME + " (" +
                    KucingDB.KucingEntry._ID + " INTEGER PRIMARY KEY," +
                    KucingDB.KucingEntry.COL_NAMA_KUCING + " TEXT," +
                    KucingDB.KucingEntry.COL_RAS_KUCING + " TEXT," +
                    KucingDB.KucingEntry.COL_JENIS_KELAMIN + " TEXT," +
                    KucingDB.KucingEntry.COL_TIPE + " TEXT)";

    private static final String DELETE = "DROP TABLE IF EXISTS " + KucingDB.KucingEntry.TB_NAME;

    private static final int DB_VER = 1;
    private static final String DB_NAME = "kucingpedia.db";

    public KucingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(DELETE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        onUpgrade(db, oldVer, newVer);
    }
}
