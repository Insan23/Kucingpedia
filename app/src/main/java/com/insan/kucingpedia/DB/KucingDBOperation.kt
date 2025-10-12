package com.insan.kucingpedia.DB

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.insan.kucingpedia.DB.KucingDB.KucingEntry

class KucingDBOperation(private val db: SQLiteDatabase) {
    fun insertData(values: ContentValues?): Long {
        return db.insert(KucingEntry.TB_NAME, null, values)
    }

    fun readAllData(): Cursor {
        val kolom = arrayOf<String?>(
            BaseColumns._ID,
            KucingEntry.COL_RAS_KUCING,
            KucingEntry.COL_DESKRIPSI,
            KucingEntry.COL_IMG,
            KucingEntry.COL_TIPE
        )
        return db.query(KucingEntry.TB_NAME, kolom, null, null, null, null, null)
    }

    fun readById(id: Long): Cursor {
        val kolom = arrayOf<String?>(
            BaseColumns._ID,
            KucingEntry.COL_RAS_KUCING,
            KucingEntry.COL_DESKRIPSI,
            KucingEntry.COL_TIPE
        )
        val kolomWh = BaseColumns._ID + "=?"
        val whArgs = arrayOf<String?>(id.toString())
        return db.query(KucingEntry.TB_NAME, kolom, kolomWh, whArgs, null, null, null)
    }

    fun deleteDataById(id: Long): Int {
        val kolomWh = BaseColumns._ID + "=?"
        val whArgs = arrayOf<String?>(id.toString())
        return db.delete(KucingEntry.TB_NAME, kolomWh, whArgs)
    }

    fun updateDataById(id: Long, values: ContentValues?): Int {
        val kolomWh = BaseColumns._ID + "=?"
        val whArgs = arrayOf<String?>(id.toString())
        return db.update(KucingEntry.TB_NAME, values, kolomWh, whArgs)
    }
}
