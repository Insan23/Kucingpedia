package com.insan.kucingpedia.activities

import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.insan.kucingpedia.DB.KucingDB.KucingEntry
import com.insan.kucingpedia.DB.KucingDBHelper
import com.insan.kucingpedia.DB.KucingDBOperation
import com.insan.kucingpedia.R
import com.insan.kucingpedia.adapter.KucingAdapter
import com.insan.kucingpedia.model.Kucing

class HalamanUtama : AppCompatActivity() {
    private var db: SQLiteDatabase? = null
    private var rvKucing: RecyclerView? = null
    private var listKucing: MutableList<Kucing?>? = null
    private val options = arrayOf<String?>(
        "Ubah", "Hapus"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_utama)

        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setTitle("Kucing Pedia")
        rvKucing = findViewById<RecyclerView>(R.id.list_kucing)
        listKucing = ArrayList<Kucing?>()
        db = KucingDBHelper(getApplicationContext()).getReadableDatabase()
        Handler().postDelayed(object : Runnable {
            override fun run() {
                findViewById<View?>(R.id.toast_kecil).setVisibility(View.VISIBLE)
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        findViewById<View?>(R.id.toast_kecil).setVisibility(View.GONE)
                    }
                }, 2000)
            }
        }, 2000)
        refresh()

        findViewById<View?>(R.id.fab).setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                startActivity(Intent(this@HalamanUtama, HalamanTambah::class.java))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_utama, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.about) {
            startActivity(Intent(this@HalamanUtama, HalamanAbout::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        db!!.close()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        findViewById<View?>(R.id.pro_circular).setVisibility(View.VISIBLE)
        rvKucing!!.setVisibility(View.GONE)
        listKucing!!.clear()
        val c = KucingDBOperation(db).readAllData()
        while (c.moveToNext()) {
            listKucing!!.add(
                Kucing(
                    c.getLong(c.getColumnIndex(BaseColumns._ID)),
                    c.getString(c.getColumnIndex(KucingEntry.COL_RAS_KUCING)),
                    c.getString(c.getColumnIndex(KucingEntry.COL_DESKRIPSI)),
                    c.getBlob(c.getColumnIndex(KucingEntry.COL_IMG)),
                    c.getString(c.getColumnIndex(KucingEntry.COL_TIPE))
                )
            )
        }
        c.close()

        val adapter = KucingAdapter(listKucing, object : KucingAdapter.OnItemClickListener {
            override fun onItemClick(model: Kucing) {
                val i = Intent(this@HalamanUtama, HalamanDetail::class.java)
                //                i.putExtra("_id", model.getId());
                i.putExtra("ras", model.getRas())
                i.putExtra("deskripsi", model.getDeskripsi())
                i.putExtra("foto", model.getFoto())
                startActivity(i)
            }
        }, object : KucingAdapter.OnItemLongClickListener {
            override fun onItemLongClick(model: Kucing) {
                dialog(model)
            }
        })
        rvKucing!!.setAdapter(adapter)
        rvKucing!!.setHasFixedSize(true)
        rvKucing!!.setLayoutManager(LinearLayoutManager(getApplicationContext()))

        findViewById<View?>(R.id.pro_circular).setVisibility(View.GONE)
        rvKucing!!.setVisibility(View.VISIBLE)
    }

    private fun dialog(model: Kucing) {
        AlertDialog.Builder(this@HalamanUtama)
            .setItems(options, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    if (which == 0) { //ubah
                        val i = Intent(this@HalamanUtama, HalamanTambah::class.java)
                        i.putExtra("_id", model.getId())
                        i.putExtra("ras", model.getRas())
                        i.putExtra("deskripsi", model.getDeskripsi())
                        startActivity(i)
                    } else if (which == 1) { //hapus
                        dialog.dismiss()
                        AlertDialog.Builder(this@HalamanUtama)
                            .setTitle("Hapus")
                            .setMessage("Hapus Informasi Tentang " + model.getRas() + "?")
                            .setPositiveButton("Ya", object : DialogInterface.OnClickListener {
                                override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                                    val rowDeleted =
                                        KucingDBOperation(db).deleteDataById(model.getId())
                                    if (rowDeleted > 0) {
                                        refresh()
                                    }
                                }
                            })
                            .setNegativeButton("Tidak", object : DialogInterface.OnClickListener {
                                override fun onClick(di: DialogInterface, i: Int) {
                                    di.dismiss()
                                }
                            }).show()
                    }
                }
            }).show()
    }
}
