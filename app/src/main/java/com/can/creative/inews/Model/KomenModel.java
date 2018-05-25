package com.can.creative.inews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cis on 14/05/2018.
 */

public class KomenModel {
    @SerializedName("comment_id")
    @Expose
    private String commentId;
    @SerializedName("comment_berita_id")
    @Expose
    private String commentBeritaId;
    @SerializedName("comment_user_id")
    @Expose
    private String commentUserId;
    @SerializedName("comment_isi")
    @Expose
    private String commentIsi;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentBeritaId() {
        return commentBeritaId;
    }

    public void setCommentBeritaId(String commentBeritaId) {
        this.commentBeritaId = commentBeritaId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentIsi() {
        return commentIsi;
    }

    public void setCommentIsi(String commentIsi) {
        this.commentIsi = commentIsi;
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
}
