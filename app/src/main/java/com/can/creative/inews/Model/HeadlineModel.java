package com.can.creative.inews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cis on 15/05/2018.
 */

public class HeadlineModel {
    @SerializedName("berita_id")
    @Expose
    private String beritaId;
    @SerializedName("berita_kategori_id")
    @Expose
    private String beritaKategoriId;
    @SerializedName("berita_user_id")
    @Expose
    private String beritaUserId;
    @SerializedName("berita_judul")
    @Expose
    private String beritaJudul;
    @SerializedName("berita_gambar")
    @Expose
    private String beritaGambar;
    @SerializedName("berita_isi")
    @Expose
    private String beritaIsi;
    @SerializedName("approve")
    @Expose
    private String approve;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_kategori_id")
    @Expose
    private String userKategoriId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_password")
    @Expose
    private String userPassword;
    @SerializedName("user_role")
    @Expose
    private String userRole;
    @SerializedName("user_nama")
    @Expose
    private String userNama;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_nip")
    @Expose
    private String userNip;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("user_gambar")
    @Expose
    private String userGambar;
    @SerializedName("password_recovery")
    @Expose
    private String passwordRecovery;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("kategori_id")
    @Expose
    private String kategoriId;
    @SerializedName("kategori_name")
    @Expose
    private String kategoriName;

    public String getBeritaId() {
        return beritaId;
    }

    public void setBeritaId(String beritaId) {
        this.beritaId = beritaId;
    }

    public String getBeritaKategoriId() {
        return beritaKategoriId;
    }

    public void setBeritaKategoriId(String beritaKategoriId) {
        this.beritaKategoriId = beritaKategoriId;
    }

    public String getBeritaUserId() {
        return beritaUserId;
    }

    public void setBeritaUserId(String beritaUserId) {
        this.beritaUserId = beritaUserId;
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

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserKategoriId() {
        return userKategoriId;
    }

    public void setUserKategoriId(String userKategoriId) {
        this.userKategoriId = userKategoriId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserNama() {
        return userNama;
    }

    public void setUserNama(String userNama) {
        this.userNama = userNama;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNip() {
        return userNip;
    }

    public void setUserNip(String userNip) {
        this.userNip = userNip;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserGambar() {
        return userGambar;
    }

    public void setUserGambar(String userGambar) {
        this.userGambar = userGambar;
    }

    public String getPasswordRecovery() {
        return passwordRecovery;
    }

    public void setPasswordRecovery(String passwordRecovery) {
        this.passwordRecovery = passwordRecovery;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
}
