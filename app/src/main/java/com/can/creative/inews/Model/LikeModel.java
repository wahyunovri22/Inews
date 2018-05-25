package com.can.creative.inews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cis on 14/05/2018.
 */

public class LikeModel {
    @SerializedName("like_dislike_id")
    @Expose
    private String likeDislikeId;
    @SerializedName("like_dislike_berita_id")
    @Expose
    private String likeDislikeBeritaId;
    @SerializedName("like_dislike_user_id")
    @Expose
    private String likeDislikeUserId;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("dislike")
    @Expose
    private String dislike;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;

    public String getLikeDislikeId() {
        return likeDislikeId;
    }

    public void setLikeDislikeId(String likeDislikeId) {
        this.likeDislikeId = likeDislikeId;
    }

    public String getLikeDislikeBeritaId() {
        return likeDislikeBeritaId;
    }

    public void setLikeDislikeBeritaId(String likeDislikeBeritaId) {
        this.likeDislikeBeritaId = likeDislikeBeritaId;
    }

    public String getLikeDislikeUserId() {
        return likeDislikeUserId;
    }

    public void setLikeDislikeUserId(String likeDislikeUserId) {
        this.likeDislikeUserId = likeDislikeUserId;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
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
}
