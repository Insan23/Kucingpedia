package com.insan.kucingpedia.activities

import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.insan.kucingpedia.R
import java.util.Objects

class HalamanDetail : AppCompatActivity() {
    private var rasDetail: TextView? = null
    private var deskripsiDetail: TextView? = null
    private var fotoDetail: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar?>(getSupportActionBar()).setDisplayHomeAsUpEnabled(true)
        toolbar.getNavigationIcon()!!
            .setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)

        rasDetail = findViewById<TextView>(R.id.ras)
        deskripsiDetail = findViewById<TextView>(R.id.deskripsi)
        fotoDetail = findViewById<ImageView>(R.id.foto)

        val ras = getIntent().getStringExtra("ras")
        val deskripsi = getIntent().getStringExtra("deskripsi")
        val foto = getIntent().getByteArrayExtra("foto")

        rasDetail!!.setText(ras)
        deskripsiDetail!!.setText(deskripsi)
        fotoDetail!!.setImageBitmap(BitmapFactory.decodeByteArray(foto, 0, foto!!.size))
    }
}
