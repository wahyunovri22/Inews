package com.can.creative.inews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cis on 09/05/2018.
 */

public class BeritaModel {
    @SerializedName("berita_id")
    @Expose
    private String beritaId;
    @SerializedName("berita_judul")
    @Expose
    private String beritaJudul;
    @SerializedName("berita_gambar")
    @Expose
    private String beritaGambar;
    @SerializedName("berita_isi")
    @Expose
    private String beritaIsi;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("user_nama")
    @Expose
    private String userNama;
    @SerializedName("kategori_id")
    @Expose
    private String kategoriId;
    @SerializedName("kategori_name")
    @Expose
    private String kategoriName;
    @SerializedName("user_gambar")
    @Expose
    private String userGambar;
    @SerializedName("dislike")
    @Expose
    private String dislike;
    @SerializedName("like")
    @Expose
    private String like;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getBeritaId() {
        return beritaId;
    }

    public void setBeritaId(String beritaId) {
        this.beritaId = beritaId;
    }

    public String getBeritaJudul() {
        return beritaJudul;
    }

    public void setBeritaJudul(String beritaJudul) {
        this.beritaJudul = beritaJudul;
    }

    public String getBeritaGambar() {
        return beritaGambar;
    }

    public void setBeritaGambar(String beritaGambar) {
        this.beritaGambar = beritaGambar;
    }

    public String getBeritaIsi() {
        return beritaIsi;
    }

    public void setBeritaIsi(String beritaIsi) {
        this.beritaIsi = beritaIsi;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserNama() {
        return userNama;
    }

    public void setUserNama(String userNama) {
        this.userNama = userNama;
    }

    public String getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(String kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriName() {
        return kategoriName;
    }

    public void setKategoriName(String kategoriName) {
        this.kategoriName = kategoriName;
    }

    public String getUserGambar() {
        return userGambar;
    }

    public void setUserGambar(String userGambar) {
        this.userGambar = userGambar;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
