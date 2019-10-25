package com.insan.kucingpedia.DB;

import android.provider.BaseColumns;

public final class KucingDB {

    public static final String TIPE_UTAMA = "utama";
    public static final String TIPE_KOLEKSI = "koleksi";

    private KucingDB() {
    }

    public static class KucingEntry implements BaseColumns {
        public static final String TB_NAME = "kucing";
        public static final String COL_RAS_KUCING = "ras";
        public static final String COL_DESKRIPSI = "deskripsi";
        public static final String COL_IMG = "foto";
        public static final String COL_TIPE = "tipe";
    }
}
