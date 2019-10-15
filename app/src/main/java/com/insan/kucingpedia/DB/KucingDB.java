package com.insan.kucingpedia.DB;

import android.provider.BaseColumns;

public final class KucingDB {

    public static final String JK_JANTAN = "jantan";
    public static final String JK_BETINA = "betina";

    public static final String TIPE_UTAMA = "utama";
    public static final String TIPE_KOLEKSI = "koleksi";

    private KucingDB() {
    }

    public static class KucingEntry implements BaseColumns {
        public static final String TB_NAME = "kucing";
        public static final String COL_NAMA_KUCING = "id";
        public static final String COL_RAS_KUCING = "ras";
        public static final String COL_JENIS_KELAMIN = "jk";
        public static final String COL_TIPE = "jk";
    }
}
