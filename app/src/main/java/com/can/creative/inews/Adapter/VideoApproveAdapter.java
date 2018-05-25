package com.can.creative.inews.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Activity.AdminActivity;
import com.can.creative.inews.Activity.Berita.DetailVideoActivity;
import com.can.creative.inews.Model.VideoModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.jaedongchicken.ytplayer.JLog;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.youtubeplayer.ui.PlayerUIController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cis on 23/05/2018.
 */

public class VideoApproveAdapter extends RecyclerView.Adapter<VideoApproveAdapter.VideoApproveHolder> {

    List<VideoModel> videoModels;
    Context context;

    public VideoApproveAdapter(List<VideoModel> videoModels, Context context) {
        this.videoModels = videoModels;
        this.context = context;
    }

    @Override
    public VideoApproveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video, parent, false);
        VideoApproveHolder holder = new VideoApproveHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoApproveHolder holder, int position) {
        YTParams params = new YTParams();
        final VideoModel vm = videoModels.get(position);
        holder.txtPengirim.setText(vm.getKategoriName());

        params.setPlaybackQuality(PlaybackQuality.small);
        holder.youtubePlayerView.setAutoPlayerHeight(context);
        holder.youtubePlayerView.initialize(vm.getVideoFile(), new YoutubePlayerView.YouTubeListener() {
            @Override
            public void onReady() {

            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {

            }

            @Override
            public void onPlaybackQualityChange(String arg) {

            }

            @Override
            public void onPlaybackRateChange(String arg) {

            }

            @Override
            public void onError(String arg) {

            }

            @Override
            public void onApiChange(String arg) {

            }

            @Override
            public void onCurrentSecond(double second) {

            }

            @Override
            public void onDuration(double duration) {
                String message = "onDuration(" + duration + ")";
            }

            @Override
            public void logs(String log) {

            }
        });
        holder.vm = vm;
    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }

    public class VideoApproveHolder extends RecyclerView.ViewHolder{

        TextView txtPengirim;
        TextView txtLike,txtKomen,txtDislike;
        YouTubePlayerView playerView;
        CardView cardView;
        VideoModel vm;
        YoutubePlayerView youtubePlayerView;
        public VideoApproveHolder(View itemView) {
            super(itemView);

            youtubePlayerView = itemView.findViewById(R.id.youtubePlayerView);
            cardView = itemView.findViewById(R.id.card);
            txtPengirim = itemView.findViewById(R.id.txt_pengirim);
            playerView = itemView.findViewById(R.id.youtube_player_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] dialogItem = {"Lihat", "Setujui"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Tentukan Pilihan Anda");
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            switch (i) {
                                case 0:
                                    Intent intent = new Intent(context, DetailVideoActivity.class);
                                    intent.putExtra("nama", vm.getUserNama());
                                    intent.putExtra("tanggal", vm.getCreatedAt());
                                    intent.putExtra("gambar", vm.getUserGambar());
                                    intent.putExtra("judul", vm.getVideoJudul());
                                    intent.putExtra("file", vm.getVideoFile());
                                    intent.putExtra("id", vm.getVideoId());
                                    intent.putExtra("deskripsi", vm.getVideoDeskripsi());
                                    context.startActivity(intent);
                                    break;
                                case 1:
                                    final ProgressDialog pd = new ProgressDialog(context);
                                    pd.setCancelable(false);
                                    pd.setTitle("Mohon Tunggu");
                                    pd.show();
                                    ApiService apiService = Client.getInstanceRetrofit();
                                    apiService.accVideo(vm.getVideoId()).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()){
                                                try {
                                                    pd.dismiss();
                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                    String pesan = jsonObject.optString("pesan");
                                                    Toast.makeText(context, ""+pesan, Toast.LENGTH_SHORT).show();
                                                    Intent intent1 = new Intent(context, AdminActivity.class);
                                                    context.startActivity(intent1);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            pd.dismiss();
                                            Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }
}
