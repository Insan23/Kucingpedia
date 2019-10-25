package com.insan.kucingpedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.insan.kucingpedia.R;

public class HalamanDetail extends AppCompatActivity {

    private TextView rasDetail, deskripsiDetail;
    private ImageView fotoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        rasDetail = findViewById(R.id.ras);
        deskripsiDetail = findViewById(R.id.deskripsi);
        fotoDetail = findViewById(R.id.foto);

        String ras = getIntent().getStringExtra("ras");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        byte[] foto = getIntent().getByteArrayExtra("foto");

        rasDetail.setText(ras);
        deskripsiDetail.setText(deskripsi);
        fotoDetail.setImageBitmap(BitmapFactory.decodeByteArray(foto, 0, foto.length));
    }
}
