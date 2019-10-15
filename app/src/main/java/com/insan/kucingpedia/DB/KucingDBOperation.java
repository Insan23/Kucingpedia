package com.insan.kucingpedia.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_JENIS_KELAMIN;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_NAMA_KUCING;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_RAS_KUCING;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_TIPE;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.TB_NAME;

public class KucingDBOperation {

    private SQLiteDatabase db;

    public KucingDBOperation(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertData(ContentValues values) {
        return db.insert(TB_NAME, null, values);
    }

    public Cursor readAllData() {
        String[] kolom = {_ID, COL_NAMA_KUCING, COL_RAS_KUCING, COL_JENIS_KELAMIN, COL_TIPE};
        return db.query(TB_NAME, kolom, null, null, null, null, null);
    }

    public Cursor readById(long id) {
        String[] kolom = {_ID, COL_NAMA_KUCING, COL_RAS_KUCING, COL_JENIS_KELAMIN, COL_TIPE};
        String kolomWh = _ID + "=?";
        String[] whArgs = {String.valueOf(id)};
        return db.query(TB_NAME, kolom, kolomWh, whArgs, null, null, null);
    }

    public int deleteDataById(long id) {
        String kolomWh = _ID + "=?";
        String[] whArgs = {String.valueOf(id)};
        return db.delete(TB_NAME, kolomWh, whArgs);
    }

    public int updateDataById(long id, ContentValues values) {
        String kolomWh = _ID + "=?";
        String[] whArgs = {String.valueOf(id)};
        return db.update(TB_NAME, values, kolomWh, whArgs);
    }
}
