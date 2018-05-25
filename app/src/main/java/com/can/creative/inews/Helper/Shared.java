package com.can.creative.inews.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cis on 09/05/2018.
 */

public class Shared {
    public static final String SP_MAHASISWA_APP = "spInews";

    public static final String SP_JUDUL_BERITA = "spJudul";
    public static final String SP_ISI_BERITA = "spIsi";
    public static final String SP_GAMBAR_BERITA = "spGambar";
    public static final String SP_TANGGAL_BERITA = "spTanggal";
    public static final String SP_PEMBUAT_BERITA = "spPembuat";
    public static final String SP_FOTO_PEMBUAT_BERITA = "spFoto";
    public static final String SP_USER_KATEGORI = "sKategori";
    public static final String SP_BERITA_ID = "sBeritaId";
    public static final String SP_ID = "sId";
    public static final String SP_NAMA = "sNama";
    public static final String SP_USERNAME = "sUsername";
    public static final String SP_RULE = "sRule";
    public static final String SP_EMAIL = "sEmail";
    public static final String SP_NIP = "sNip";
    public static final String SP_GAMBAR = "sGambar";
    public static final String SP_USER_KATEGORI_ID = "sIdKategori";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public Shared (Context context){
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpJudulBerita() {
        return sp.getString(SP_JUDUL_BERITA, "");
    }

    public String getSpIsiBerita() {
        return sp.getString(SP_ISI_BERITA, "");
    }

    public String getSpGambarBerita() {
        return sp.getString(SP_GAMBAR_BERITA, "");
    }

    public String getSpTanggalBerita() {
        return sp.getString(SP_TANGGAL_BERITA, "");
    }

    public String getSpPembuatBerita() {
        return sp.getString(SP_PEMBUAT_BERITA, "");
    }

    public String getSpBeritaId() {
        return sp.getString(SP_BERITA_ID, "");
    }

    public String getSpId() {
        return sp.getString(SP_ID, "");
    }

    public String getSpNama() {
        return sp.getString(SP_NAMA, "");
    }

    public String getSpUsername() {
        return sp.getString(SP_USERNAME, "");
    }

    public String getSpRule() {
        return sp.getString(SP_RULE, "");
    }

    public String getSpEmail() {
        return sp.getString(SP_EMAIL,"");
    }

    public String getSpNip() {
        return sp.getString(SP_NIP, "");
    }

    public String getSpUserKategori() {
        return sp.getString(SP_USER_KATEGORI, "");
    }

    public String getSpGambar() {
        return sp.getString(SP_GAMBAR, "");
    }

    public String getSpFotoPembuatBerita() {
        return sp.getString(SP_FOTO_PEMBUAT_BERITA, "");
    }

    public String getSpUserKategoriId() {
        return sp.getString(SP_USER_KATEGORI_ID, "");
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
