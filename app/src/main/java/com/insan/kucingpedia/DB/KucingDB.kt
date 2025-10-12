package com.insan.kucingpedia.DB

import android.provider.BaseColumns

object KucingDB {
    const val TIPE_UTAMA: String = "utama"
    const val TIPE_KOLEKSI: String = "koleksi"

    object KucingEntry : BaseColumns {
        const val TB_NAME: String = "kucing"
        const val COL_RAS_KUCING: String = "ras"
        const val COL_DESKRIPSI: String = "deskripsi"
        const val COL_IMG: String = "foto"
        const val COL_TIPE: String = "tipe"
    }
}
