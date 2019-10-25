package com.insan.kucingpedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.insan.kucingpedia.DB.KucingDB;
import com.insan.kucingpedia.DB.KucingDBHelper;
import com.insan.kucingpedia.DB.KucingDBOperation;
import com.insan.kucingpedia.DB.SP;
import com.insan.kucingpedia.R;

import java.io.ByteArrayOutputStream;

public class HalamanMulai extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_mulai);
        SP ss = new SP(getApplicationContext());
        if (!ss.getBoolean("inserted")) {
            ss.putBoolean("inserted", true);
            insert();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HalamanMulai.this, HalamanUtama.class));
            }
        }, 1000);
    }

    private void insert() {
        SQLiteDatabase db = new KucingDBHelper(getApplicationContext()).getWritableDatabase();
        KucingDBOperation op = new KucingDBOperation(db);
        ContentValues[] v = new ContentValues[10];
        int[] imgRes = {
                R.drawable.kucing_persia, R.drawable.kucing_exotic_shorthair,
                R.drawable.kucing_abyssinian, R.drawable.kucing_burmese,
                R.drawable.kucing_maine_coon, R.drawable.kucing_russian_blue,
                R.drawable.kucing_siamese, R.drawable.kucing_ragdoll,
                R.drawable.kucing_sphynx, R.drawable.kucing_munchkin
        };
        for (int i = 0; i < v.length; i++) {
            v[i] = new ContentValues();
            v[i].put(KucingDB.KucingEntry.COL_RAS_KUCING, getResources().getStringArray(R.array.kucing)[i]);
            v[i].put(KucingDB.KucingEntry.COL_DESKRIPSI, getResources().getStringArray(R.array.kucing_detail)[i]);
            v[i].put(KucingDB.KucingEntry.COL_IMG, getByteArray(imgRes[i]));
            v[i].put(KucingDB.KucingEntry.COL_TIPE, KucingDB.TIPE_UTAMA);
            op.insertData(v[i]);
        }

    }

    private byte[] getByteArray(int drawable) {
        Drawable d = getResources().getDrawable(drawable, null);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
