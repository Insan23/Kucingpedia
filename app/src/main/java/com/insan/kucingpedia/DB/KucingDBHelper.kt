package com.insan.kucingpedia.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.insan.kucingpedia.DB.KucingDB.KucingEntry

class KucingDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        db.execSQL(DELETE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        onUpgrade(db, oldVer, newVer)
    }

    companion object {
        private val CREATE = "CREATE TABLE " + KucingEntry.TB_NAME + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY," +
                KucingEntry.COL_RAS_KUCING + " TEXT," +
                KucingEntry.COL_DESKRIPSI + " TEXT," +
                KucingEntry.COL_IMG + " BLOB," +
                KucingEntry.COL_TIPE + " TEXT)"

        private val DELETE = "DROP TABLE IF EXISTS " + KucingEntry.TB_NAME

        private const val DB_VER = 1
        private const val DB_NAME = "kucingpedia.db"
    }
}
