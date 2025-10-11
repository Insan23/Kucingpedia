package com.insan.kucingpedia.activities

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.insan.kucingpedia.DB.KucingDB
import com.insan.kucingpedia.DB.KucingDB.KucingEntry
import com.insan.kucingpedia.DB.KucingDBHelper
import com.insan.kucingpedia.DB.KucingDBOperation
import com.insan.kucingpedia.DB.SP
import com.insan.kucingpedia.R
import java.io.ByteArrayOutputStream

class HalamanMulai : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_mulai)
        val ss = SP(getApplicationContext())
        if (!ss.getBoolean("inserted")) {
            ss.putBoolean("inserted", true)
            insert()
        }
        Handler().postDelayed(object : Runnable {
            override fun run() {
                startActivity(Intent(this@HalamanMulai, HalamanUtama::class.java))
            }
        }, 1000)
    }

    private fun insert() {
        val db = KucingDBHelper(getApplicationContext()).getWritableDatabase()
        val op = KucingDBOperation(db)
        val v = arrayOfNulls<ContentValues>(10)
        val imgRes = intArrayOf(
            R.drawable.kucing_persia, R.drawable.kucing_exotic_shorthair,
            R.drawable.kucing_abyssinian, R.drawable.kucing_burmese,
            R.drawable.kucing_maine_coon, R.drawable.kucing_russian_blue,
            R.drawable.kucing_siamese, R.drawable.kucing_ragdoll,
            R.drawable.kucing_sphynx, R.drawable.kucing_munchkin
        )
        for (i in v.indices) {
            v[i] = ContentValues()
            v[i]!!.put(KucingEntry.COL_RAS_KUCING, getResources().getStringArray(R.array.kucing)[i])
            v[i]!!.put(
                KucingEntry.COL_DESKRIPSI,
                getResources().getStringArray(R.array.kucing_detail)[i]
            )
            v[i]!!.put(KucingEntry.COL_IMG, getByteArray(imgRes[i]))
            v[i]!!.put(KucingEntry.COL_TIPE, KucingDB.TIPE_UTAMA)
            op.insertData(v[i])
        }
    }

    private fun getByteArray(drawable: Int): ByteArray {
        val d = getResources().getDrawable(drawable, null)
        val bitmap = (d as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }
}
