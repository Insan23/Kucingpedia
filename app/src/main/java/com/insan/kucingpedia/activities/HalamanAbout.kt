package com.insan.kucingpedia.activities

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.insan.kucingpedia.R

class HalamanAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_about)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        toolbar.getNavigationIcon()!!
            .setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
    }
}
