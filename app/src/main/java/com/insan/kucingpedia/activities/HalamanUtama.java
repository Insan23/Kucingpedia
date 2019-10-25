package com.insan.kucingpedia.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.insan.kucingpedia.DB.KucingDBHelper;
import com.insan.kucingpedia.DB.KucingDBOperation;
import com.insan.kucingpedia.R;
import com.insan.kucingpedia.adapter.KucingAdapter;
import com.insan.kucingpedia.model.Kucing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_DESKRIPSI;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_IMG;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_RAS_KUCING;
import static com.insan.kucingpedia.DB.KucingDB.KucingEntry.COL_TIPE;

public class HalamanUtama extends AppCompatActivity {

    private SQLiteDatabase db;
    private RecyclerView rvKucing;
    private List<Kucing> listKucing;
    private String[] options = {
            "Ubah", "Hapus"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kucing Pedia");
        rvKucing = findViewById(R.id.list_kucing);
        listKucing = new ArrayList<>();
        db = new KucingDBHelper(getApplicationContext()).getReadableDatabase();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.toast_kecil).setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.toast_kecil).setVisibility(View.GONE);
                    }
                }, 2000);
            }
        }, 2000);
        refresh();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HalamanUtama.this, HalamanTambah.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(HalamanUtama.this, HalamanAbout.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        findViewById(R.id.pro_circular).setVisibility(View.VISIBLE);
        rvKucing.setVisibility(View.GONE);
        listKucing.clear();
        Cursor c = new KucingDBOperation(db).readAllData();
        while (c.moveToNext()) {
            listKucing.add(new Kucing(
                    c.getLong(c.getColumnIndex(_ID)),
                    c.getString(c.getColumnIndex(COL_RAS_KUCING)),
                    c.getString(c.getColumnIndex(COL_DESKRIPSI)),
                    c.getBlob(c.getColumnIndex(COL_IMG)),
                    c.getString(c.getColumnIndex(COL_TIPE))
            ));
        }
        c.close();

        KucingAdapter adapter = new KucingAdapter(listKucing, new KucingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Kucing model) {
                Intent i = new Intent(HalamanUtama.this, HalamanDetail.class);
//                i.putExtra("_id", model.getId());
                i.putExtra("ras", model.getRas());
                i.putExtra("deskripsi", model.getDeskripsi());
                i.putExtra("foto", model.getFoto());
                startActivity(i);
            }
        }, new KucingAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Kucing model) {
                dialog(model);
            }
        });
        rvKucing.setAdapter(adapter);
        rvKucing.setHasFixedSize(true);
        rvKucing.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        findViewById(R.id.pro_circular).setVisibility(View.GONE);
        rvKucing.setVisibility(View.VISIBLE);
    }

    private void dialog(final Kucing model) {
        new AlertDialog.Builder(HalamanUtama.this).setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) { //ubah
                    Intent i = new Intent(HalamanUtama.this, HalamanTambah.class);
                    i.putExtra("_id", model.getId());
                    i.putExtra("ras", model.getRas());
                    i.putExtra("deskripsi", model.getDeskripsi());
                    startActivity(i);
                } else if (which == 1) { //hapus
                    dialog.dismiss();
                    new AlertDialog.Builder(HalamanUtama.this)
                            .setTitle("Hapus")
                            .setMessage("Hapus Informasi Tentang " + model.getRas() + "?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int rowDeleted = new KucingDBOperation(db).deleteDataById(model.getId());
                                    if (rowDeleted > 0) {
                                        refresh();
                                    }
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface di, int i) {
                                    di.dismiss();
                                }
                            }).show();
                }
            }
        }).show();
    }
}
