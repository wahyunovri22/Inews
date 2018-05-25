package com.can.creative.inews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cis on 21/05/2018.
 */

public class KomenVideo {

    @SerializedName("comvid_id")
    @Expose
    private String comvidId;
    @SerializedName("comvid_video_id")
    @Expose
    private String comvidVideoId;
    @SerializedName("comvid_user_id")
    @Expose
    private String comvidUserId;
    @SerializedName("comvid_isi")
    @Expose
    private String comvidIsi;
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
    @SerializedName("password_recovery")
    @Expose
    private String passwordRecovery;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("video_user_id")
    @Expose
    private String videoUserId;
    @SerializedName("video_kategori_id")
    @Expose
    private String videoKategoriId;
    @SerializedName("video_judul")
    @Expose
    private String videoJudul;
    @SerializedName("video_cover")
    @Expose
    private String videoCover;
    @SerializedName("video_deskripsi")
    @Expose
    private String videoDeskripsi;
    @SerializedName("approve")
    @Expose
    private String approve;
    @SerializedName("video_file")
    @Expose
    private String videoFile;
    @SerializedName("waktu_comment")
    @Expose
    private String waktuComment;

    public String getComvidId() {
        return comvidId;
    }

    public void setComvidId(String comvidId) {
        this.comvidId = comvidId;
    }

    public String getComvidVideoId() {
        return comvidVideoId;
    }

    public void setComvidVideoId(String comvidVideoId) {
        this.comvidVideoId = comvidVideoId;
    }

    public String getComvidUserId() {
        return comvidUserId;
    }

    public void setComvidUserId(String comvidUserId) {
        this.comvidUserId = comvidUserId;
    }

    public String getComvidIsi() {
        return comvidIsi;
    }

    public void setComvidIsi(String comvidIsi) {
        this.comvidIsi = comvidIsi;
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoUserId() {
        return videoUserId;
    }

    public void setVideoUserId(String videoUserId) {
        this.videoUserId = videoUserId;
    }

    public String getVideoKategoriId() {
        return videoKategoriId;
    }

    public void setVideoKategoriId(String videoKategoriId) {
        this.videoKategoriId = videoKategoriId;
    }

    public String getVideoJudul() {
        return videoJudul;
    }

    public void setVideoJudul(String videoJudul) {
        this.videoJudul = videoJudul;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getVideoDeskripsi() {
        return videoDeskripsi;
    }

    public void setVideoDeskripsi(String videoDeskripsi) {
        this.videoDeskripsi = videoDeskripsi;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public String getWaktuComment() {
        return waktuComment;
    }

    public void setWaktuComment(String waktuComment) {
        this.waktuComment = waktuComment;
    }

}
