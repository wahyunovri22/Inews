package com.can.creative.inews.Activity.Berita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Adapter.KomenVideoAdapter;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.Model.KomenVideo;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.youtubeplayer.ui.PlayerUIController;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailVideoActivity extends AppCompatActivity {

    private YouTubePlayerView youtubePlayerView;
    private TextView txtJudul;
    private TextView txtJumlahLike;
    private TextView txtJumlahDislike;
    private ImageView imgWa;
    private ImageView imgFb;
    private RecyclerView rv;
    private EditText edtKomen;
    private ImageView imgKomen;
    private ImageView imgShare;
    private TextView txtPos;
    ArrayList<KomenVideo> list = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private CircleImageView imgPembuat;
    private TextView txtPengepos;
    private TextView txtTanggalPos;
    private SwipeRefreshLayout swipe;
    Shared shared;
    private TextView txtDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();

        Awal();
        imgKomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostKomen();
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "https://www.youtube.com/watch?v="+getIntent().getStringExtra("file");
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_dua, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void Awal() {
        shared = new Shared(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        getKomen();


        final PlayerUIController uiController = youtubePlayerView.getPlayerUIController();
        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(final YouTubePlayer youTubePlayer) {
                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        super.onReady();
                        uiController.showYouTubeButton(false);
                        youTubePlayer.loadVideo(getIntent().getStringExtra("file"), 0);
                    }
                });
            }
        }, true);

        txtJudul.setText(getIntent().getStringExtra("judul"));
        txtPengepos.setText(getIntent().getStringExtra("nama"));
        txtTanggalPos.setText(getIntent().getStringExtra("tanggal"));
        txtDeskripsi.setText(getIntent().getStringExtra("deskripsi"));
        Picasso.with(getApplicationContext())
                .load(Config.URL_IMAGE + getIntent().getStringExtra("gambar"))
                .error(R.drawable.no_image_found)
                .into(imgPembuat);
    }

    private void getKomen() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getKomenVideo(getIntent().getStringExtra("id")).enqueue(new Callback<ArrayList<KomenVideo>>() {
            @Override
            public void onResponse(Call<ArrayList<KomenVideo>> call, Response<ArrayList<KomenVideo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    mAdapter = new KomenVideoAdapter(list, DetailVideoActivity.this);
                    rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KomenVideo>> call, Throwable t) {

            }
        });
    }

    private void PostKomen() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.komenVideo(getIntent().getStringExtra("id"),
                shared.getSpId(), edtKomen.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        edtKomen.setText(null);
                        edtKomen.setHint("Beri komentar");
                        String pesan = jsonObject.optString("pesan");
                        Toast.makeText(DetailVideoActivity.this, "" + pesan, Toast.LENGTH_SHORT).show();

                        getKomen();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailVideoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        youtubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        txtJudul = (TextView) findViewById(R.id.txt_judul);
        txtJumlahLike = (TextView) findViewById(R.id.txt_jumlah_like);
        txtJumlahDislike = (TextView) findViewById(R.id.txt_jumlah_dislike);
        imgWa = (ImageView) findViewById(R.id.img_wa);
        imgFb = (ImageView) findViewById(R.id.img_fb);
        rv = (RecyclerView) findViewById(R.id.rv);
        edtKomen = (EditText) findViewById(R.id.edt_komen);
        imgKomen = (ImageView) findViewById(R.id.img_komen);
        imgShare = (ImageView) findViewById(R.id.img_share);
        txtPos = (TextView) findViewById(R.id.txt_pos);
        imgPembuat = (CircleImageView) findViewById(R.id.img_pembuat);
        txtPengepos = (TextView) findViewById(R.id.txt_pengepos);
        txtTanggalPos = (TextView) findViewById(R.id.txt_tanggal_pos);
        txtDeskripsi = (TextView) findViewById(R.id.txt_deskripsi);
    }
}
