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

        ras = findViewById(R.id.ras);
        deskripsi = findViewById(R.id.deskripsi);
        foto = findViewById(R.id.foto);
        error_foto = findViewById(R.id.error_foto);
        simpan = findViewById(R.id.simpan);

        id = getIntent().getLongExtra("_id", -1);
        if (id == -1) {
            getSupportActionBar().setTitle("Tambah Informasi Kucing Baru");
        } else {
            getSupportActionBar().setTitle("Ubah Informasi Kucing");
            ras.setText(getIntent().getStringExtra("ras"));
            deskripsi.setText(getIntent().getStringExtra("deskripsi"));
        }

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HalamanTambah.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, BACA_MEMORI);
                }
                pickFromGallery();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanInformasi();
            }
        });
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

    private void simpanInformasi() {
        enable(false);
        error_foto.setText("");
        SQLiteDatabase db = new KucingDBHelper(getApplicationContext()).getReadableDatabase();
        boolean cek = true;
        String teksRas = ras.getText().toString();
        String teksDeskripsi = deskripsi.getText().toString();

        if (TextUtils.isEmpty(teksRas)) {
            cek = false;
            enable(true);
            ras.setError("Harus Diisi");
        }

        if (TextUtils.isEmpty(teksDeskripsi)) {
            cek = false;
            enable(true);
            deskripsi.setError("Harus Diisi");
        }

        if (fotoTerpilih == null) {
            cek = false;
            enable(true);
            error_foto.setText("Pilih Foto Terlebih Dahulu!");
        }

        if (cek) {
            if (id == -1) {
                ContentValues v = new ContentValues();
                v.put(KucingDB.KucingEntry.COL_RAS_KUCING, teksRas);
                v.put(KucingDB.KucingEntry.COL_DESKRIPSI, teksDeskripsi);
                v.put(KucingDB.KucingEntry.COL_IMG, fotoTerpilih);
                v.put(KucingDB.KucingEntry.COL_TIPE, KucingDB.TIPE_UTAMA);
                new KucingDBOperation(db).insertData(v);
            } else {
                ContentValues v = new ContentValues();
                v.put(KucingDB.KucingEntry.COL_RAS_KUCING, teksRas);
                v.put(KucingDB.KucingEntry.COL_DESKRIPSI, teksDeskripsi);
                v.put(KucingDB.KucingEntry.COL_IMG, fotoTerpilih);
                v.put(KucingDB.KucingEntry.COL_TIPE, KucingDB.TIPE_UTAMA);
                new KucingDBOperation(db).updateDataById(id, v);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 3000);
            finish();
        }
        db.close();
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, KODE_AMBIL_FOTO);
    }

    private void enable(boolean st) {
        ras.setEnabled(st);
        deskripsi.setEnabled(st);
        simpan.setEnabled(st);
        if (st) {
            findViewById(R.id.overlay).setVisibility(View.GONE);
            findViewById(R.id.pro_circular).setVisibility(View.GONE);
        } else {
            findViewById(R.id.overlay).setVisibility(View.VISIBLE);
            findViewById(R.id.pro_circular).setVisibility(View.VISIBLE);
        }
    }

}
