package com.insan.kucingpedia.activities

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.insan.kucingpedia.DB.KucingDB
import com.insan.kucingpedia.DB.KucingDB.KucingEntry
import com.insan.kucingpedia.DB.KucingDBHelper
import com.insan.kucingpedia.DB.KucingDBOperation
import com.insan.kucingpedia.R
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class HalamanTambah : AppCompatActivity() {
    private var ras: EditText? = null
    private var deskripsi: EditText? = null
    private var error_foto: TextView? = null
    private var foto: ImageView? = null

    private var simpan: Button? = null
    private var fotoTerpilih: ByteArray? = null
    private var id: Long = -1

    private val KODE_AMBIL_FOTO = 1
    private val BACA_MEMORI = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_tambah)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        toolbar.getNavigationIcon()!!
            .setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        getSupportActionBar()!!.setTitle("")

        ras = findViewById<EditText>(R.id.ras)
        deskripsi = findViewById<EditText>(R.id.deskripsi)
        foto = findViewById<ImageView>(R.id.foto)
        error_foto = findViewById<TextView>(R.id.error_foto)
        simpan = findViewById<Button>(R.id.simpan)

        id = getIntent().getLongExtra("_id", -1)
        if (id == -1L) {
            getSupportActionBar()!!.setTitle("Tambah Informasi Kucing Baru")
        } else {
            getSupportActionBar()!!.setTitle("Ubah Informasi Kucing")
            ras!!.setText(getIntent().getStringExtra("ras"))
            deskripsi!!.setText(getIntent().getStringExtra("deskripsi"))
        }

        foto!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@HalamanTambah,
                        arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                        BACA_MEMORI
                    )
                }
                pickFromGallery()
            }
        })

        simpan!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                simpanInformasi()
            }
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == RESULT_OK) if (requestCode == KODE_AMBIL_FOTO) {
            val selectedImage = data.getData()
            val filePathColumn = arrayOf<String?>(MediaStore.Images.Media.DATA)
            var resized: Bitmap? = null
            try {
                val inputStream = getContentResolver().openInputStream(selectedImage!!)
                val buff: InputStream = BufferedInputStream(inputStream)
                resized = decodeSampledBitmapFromStream(buff, 700, 700)
                //                    inputStream.close();
                buff.close()
            } catch (e: FileNotFoundException) {
                Log.w(HalamanTambah::class.java.getSimpleName(), e.message!!)
            } catch (e: IOException) {
                Log.w(HalamanTambah::class.java.getSimpleName(), e.message!!)
            }
            //                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//
//                ByteArrayOutputStream b = new ByteArrayOutputStream();
//                try {
//                    FileInputStream f = new FileInputStream(imgDecodableString);
//                    byte[] bfr = new byte[1024];
//                    int o;
//                    while (-1 != (o = f.read(bfr))) b.write(bfr, 0, o);
//                } catch (IOException e) {
//                    e.getMessage();
//                    Log.w(HalamanTambah.class.getSimpleName(), e.getMessage());
//                }
            fotoTerpilih = toByteArray(resized!!)
            foto!!.setImageBitmap(resized)
            foto!!.setImageTintList(null)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun simpanInformasi() {
        enable(false)
        error_foto!!.setText("")
        val db = KucingDBHelper(getApplicationContext()).getReadableDatabase()
        var cek = true
        val teksRas = ras!!.getText().toString()
        val teksDeskripsi = deskripsi!!.getText().toString()

        if (TextUtils.isEmpty(teksRas)) {
            cek = false
            enable(true)
            ras!!.setError("Harus Diisi")
        }

        if (TextUtils.isEmpty(teksDeskripsi)) {
            cek = false
            enable(true)
            deskripsi!!.setError("Harus Diisi")
        }

        if (fotoTerpilih == null) {
            cek = false
            enable(true)
            error_foto!!.setText("Pilih Foto Terlebih Dahulu!")
        }

        if (cek) {
            if (id == -1L) {
                val v = ContentValues()
                v.put(KucingEntry.COL_RAS_KUCING, teksRas)
                v.put(KucingEntry.COL_DESKRIPSI, teksDeskripsi)
                v.put(KucingEntry.COL_IMG, fotoTerpilih)
                v.put(KucingEntry.COL_TIPE, KucingDB.TIPE_UTAMA)
                KucingDBOperation(db).insertData(v)
            } else {
                val v = ContentValues()
                v.put(KucingEntry.COL_RAS_KUCING, teksRas)
                v.put(KucingEntry.COL_DESKRIPSI, teksDeskripsi)
                v.put(KucingEntry.COL_IMG, fotoTerpilih)
                v.put(KucingEntry.COL_TIPE, KucingDB.TIPE_UTAMA)
                KucingDBOperation(db).updateDataById(id, v)
            }
            Handler().postDelayed(object : Runnable {
                override fun run() {
                }
            }, 3000)
            finish()
        }
        db.close()
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        val mimeTypes = arrayOf<String?>("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, KODE_AMBIL_FOTO)
    }

    private fun enable(st: Boolean) {
        ras!!.setEnabled(st)
        deskripsi!!.setEnabled(st)
        simpan!!.setEnabled(st)
        if (st) {
            findViewById<View?>(R.id.overlay).setVisibility(View.GONE)
            findViewById<View?>(R.id.pro_circular).setVisibility(View.GONE)
        } else {
            findViewById<View?>(R.id.overlay).setVisibility(View.VISIBLE)
            findViewById<View?>(R.id.pro_circular).setVisibility(View.VISIBLE)
        }
    }


    private fun toByteArray(bp: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bp.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        return stream.toByteArray()
    }

    companion object {
        @Throws(IOException::class)
        private fun decodeSampledBitmapFromStream(
            inputStream: InputStream,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap? {
            // First, decode with inJustDecodeBounds=true to check dimensions
            inputStream.mark(inputStream.available()) // Mark the current position
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream.reset() // Reset to the beginning for actual decode

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(inputStream, null, options)
        }

        // Helper function to compute sample size
        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2

                while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }
    }
}
