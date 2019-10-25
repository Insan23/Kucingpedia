package com.insan.kucingpedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.insan.kucingpedia.DB.KucingDB;
import com.insan.kucingpedia.DB.KucingDBHelper;
import com.insan.kucingpedia.DB.KucingDBOperation;
import com.insan.kucingpedia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HalamanTambah extends AppCompatActivity {

    private EditText ras, deskripsi;
    private TextView error_foto;
    private ImageView foto;

    private Button simpan;
    private byte[] fotoTerpilih = null;
    private long id = -1;

    private final int KODE_AMBIL_FOTO = 001;
    private final int BACA_MEMORI = 010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_tambah);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setTitle("");


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == KODE_AMBIL_FOTO) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                ByteArrayOutputStream b = new ByteArrayOutputStream();
                try {
                    FileInputStream f = new FileInputStream(new File(imgDecodableString));
                    byte[] bfr = new byte[1024];
                    int o;
                    while (-1 != (o = f.read(bfr))) b.write(bfr, 0, o);
                } catch (IOException e) {
                    e.getMessage();
                    Log.w(HalamanTambah.class.getSimpleName(), e.getMessage());
                }
                fotoTerpilih = b.toByteArray();
                foto.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            }
    }



}
