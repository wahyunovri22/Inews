package com.can.creative.inews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cis on 18/05/2018.
 */

public class VideoModel {
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("video_judul")
    @Expose
    private String videoJudul;
    @SerializedName("video_file")
    @Expose
    private String videoFile;
    @SerializedName("video_deskripsi")
    @Expose
    private String videoDeskripsi;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("user_gambar")
    @Expose
    private String userGambar;
    @SerializedName("user_nama")
    @Expose
    private String userNama;
    @SerializedName("kategori_id")
    @Expose
    private String kategoriId;
    @SerializedName("kategori_name")
    @Expose
    private String kategoriName;
    @SerializedName("video_cover")
    @Expose
    private String videoCover;
    @SerializedName("dislike")
    @Expose
    private String dislike;
    @SerializedName("like")
    @Expose
    private String like;
    @SerializedName("comvid")
    @Expose
    private String comvid;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoJudul() {
        return videoJudul;
    }

    public void setVideoJudul(String videoJudul) {
        this.videoJudul = videoJudul;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public String getVideoDeskripsi() {
        return videoDeskripsi;
    }

    public void setVideoDeskripsi(String videoDeskripsi) {
        this.videoDeskripsi = videoDeskripsi;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserGambar() {
        return userGambar;
    }

    public void setUserGambar(String userGambar) {
        this.userGambar = userGambar;
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

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
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

    public String getComvid() {
        return comvid;
    }

    public void setComvid(String comvid) {
        this.comvid = comvid;
    }
}
