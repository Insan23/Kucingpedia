package com.insan.kucingpedia.model;

import android.graphics.Bitmap;

public class Kucing {

    private long id;
    private String ras;
    private String deskripsi;
    private byte[] foto;
    private String tipe;

    public Kucing(long id, String ras, String deskripsi, byte[] foto, String tipe) {
        this.id = id;
        this.ras = ras;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.tipe = tipe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRas() {
        return ras;
    }

    public void setRas(String ras) {
        this.ras = ras;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
