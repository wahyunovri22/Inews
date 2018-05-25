package com.can.creative.inews.Retrofit;

import com.can.creative.inews.Model.BeritaModel;
import com.can.creative.inews.Model.KomenModel;
import com.can.creative.inews.Model.KomenVideo;
import com.can.creative.inews.Model.UserModel;
import com.can.creative.inews.Model.VideoModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by cis on 09/05/2018.
 */

public interface ApiService {

    @GET("get_all_beritareplace.php")
    Call<ArrayList<BeritaModel>> getSemuaBerita();

    @GET("get_all_beritareplace.php")
    Call<ResponseBody> beritasSaya();

    @GET("get_berita_bykategori.php")
    Call<ArrayList<BeritaModel>> getBeritaKategori(@Query("berita_kategori_id") String id);


    @GET(" get_comment_byid.php")
    Call<ArrayList<KomenModel>> getKomen(@Query("berita_id") String id);

    @GET("get_user_approve0.php")
    Call<ArrayList<UserModel>> getUserApprove();

    @FormUrlEncoded
    @POST("inews_login.php")
    Call<ResponseBody> login(@Field("user_name") String email,
                             @Field("user_password") String password);

    @FormUrlEncoded
    @POST("add_reg.php")
    Call<ResponseBody> Register(@Field("user_name") String username,
                                @Field("user_kategori_id") String user_kategori_id,
                                @Field("user_nip") String user_nip,
                                @Field("user_nama") String usernama,
                                @Field("user_password") String password,
                                @Field("user_email") String email);

    @GET("get_all_headlineUsr.php")
    Call<ResponseBody> getHeadline();

    @GET("get_approve_berita0.php")
    Call<ArrayList<BeritaModel>> getBeritaApproval();

    @GET("get_approve_vid0.php")
    Call<ArrayList<VideoModel>> getVideoApproval();

    @GET("get_all_kategori.php")
    Call<ResponseBody> getKategori();


    @GET("get_all_video.php")
    Call<ArrayList<VideoModel>> getAllVideo();

    @GET("get_video_bykategori.php")
    Call<ArrayList<VideoModel>> getVideoByKategori(@Query("video_kategori_id") String id);

    @GET("get_all_user.php")
    Call<ArrayList<UserModel>> getAllUser();

    @FormUrlEncoded
    @POST("update_approve.php")
    Call<ResponseBody> approved(@Field("berita_id") String id,
                                @Field("headline") String headline);

    @GET("get_comvid_byid.php")
    Call<ArrayList<KomenVideo>> getKomenVideo(@Query("video_id") String id);

    @GET("get_like.php")
    Call<ResponseBody> getLikeBerita(@Query("berita_id") String id);


    @GET("get_dislike.php")
    Call<ResponseBody> getDisLikeBerita(@Query("berita_id") String id);

    @FormUrlEncoded
    @POST("add_comvid.php")
    Call<ResponseBody> komenVideo(@Field("comvid_video_id") String id_video,
                                  @Field("comvid_user_id") String user_id,
                                  @Field("comvid_isi") String komen);

    @FormUrlEncoded
    @POST("add_comment.php")
    Call<ResponseBody> komenBerita(@Field("comment_berita_id") String id_video,
                                   @Field("comment_user_id") String user_id,
                                   @Field("comment_isi") String komen);

    @Multipart
    @POST("add_videoSA.php")
    Call<ResponseBody> addVideoSA(@Part("video_judul") RequestBody judul,
                                  @Part MultipartBody.Part file,
                                  @Part("video_file") RequestBody url,
                                  @Part("video_deskripsi") RequestBody deskripsi,
                                  @Part("video_kategori_id") RequestBody kategori_id,
                                  @Part("video_user_id") RequestBody id_user);

    @Multipart
    @POST("add_videoAdm.php")
    Call<ResponseBody> addVideoAdmin(@Part("video_judul") RequestBody judul,
                                     @Part MultipartBody.Part file,
                                     @Part("video_file") RequestBody url,
                                     @Part("video_deskripsi") RequestBody deskripsi,
                                     @Part("video_kategori_id") RequestBody kategori_id,
                                     @Part("video_user_id") RequestBody id_user);

    @Multipart
    @POST("add_beritaAdm.php")
    Call<ResponseBody> tambahBeritaAdmin(@Part("berita_judul") RequestBody judul,
                                         @Part MultipartBody.Part file,
                                         @Part("berita_isi") RequestBody isi,
                                         @Part("berita_kategori_id") RequestBody kategori_id,
                                         @Part("berita_user_id") RequestBody id);

    @Multipart
    @POST("add_beritaSA.php")
    Call<ResponseBody> tambahBeritaSA(@Part("berita_judul") RequestBody judul,
                                      @Part MultipartBody.Part file,
                                      @Part("berita_isi") RequestBody isi,
                                      @Part("berita_kategori_id") RequestBody kategori_id,
                                      @Part("berita_user_id") RequestBody id);

    @Multipart
    @POST("edit_profile.php")
    Call<ResponseBody> editProfil(@Part("user_id") RequestBody id,
                                  @Part MultipartBody.Part file,
                                  @Part("user_nama") RequestBody nama,
                                  @Part("user_nip") RequestBody nip);

    @FormUrlEncoded
    @POST("edit_profile.php")
    Call<ResponseBody> editProfilDua(@Field("user_id") String id,
                                     @Field("user_nama") String nama,
                                     @Field("user_nip") String nip);

    @FormUrlEncoded
    @POST("update_pass.php")
    Call<ResponseBody> editPassword(@Field("user_id") String id,
                                    @Field("username") String username,
                                    @Field("password_old") String old,
                                    @Field("password_new")String baru);


    @GET("get_mynewsSA.php")
    Call<ResponseBody> myNews(@Query("user_id") String id);

    @FormUrlEncoded
    @POST("acc_video.php")
    Call<ResponseBody> accVideo(@Field("video_id") String id);

    @FormUrlEncoded
    @POST("delete_video.php")
    Call<ResponseBody> hapusVideo(@Field("video_id") String id);

    @FormUrlEncoded
    @POST("delete_news.php")
    Call<ResponseBody> hapusBerita(@Field("berita_id") String id);

    @FormUrlEncoded
    @POST("acc_user.php")
    Call<ResponseBody> accUser(@Field("user_id") String id);
}
