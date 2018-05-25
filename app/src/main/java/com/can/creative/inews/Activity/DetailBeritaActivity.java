package com.can.creative.inews.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Adapter.KomenAdapter;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.Model.KomenModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBeritaActivity extends AppCompatActivity {

    private ImageView imgKomen;
    private ImageView imgWa;
    private TextView txtPos;
    private EditText edtKomen;
    Shared shared;
    private FrameLayout container;
    private ScrollView sv;
    private TextView txtJudul;
    private CircleImageView imgPembuat;
    private TextView txtPengepos;
    private TextView txtTanggalPos;
    private TextView txtJumlahLike;
    private TextView txtJumlahDislike;
    private ImageView imgBerita;
    private TextView txtIsiBerita;
    private RecyclerView rv;
    private LinearLayout ly;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<KomenModel> list = new ArrayList<>();
    private ImageView imgShareWa;
    private ImageView imgShareFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();

        awal();

        edtKomen.setFocusable(false);
        edtKomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtKomen.setFocusableInTouchMode(true);
            }
        });

        imgWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "http://103.82.242.173/inews/";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        imgKomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pusKomen();
            }
        });
    }

    private void pusKomen() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.komenBerita(shared.getSpBeritaId(), shared.getSpId(), edtKomen.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        edtKomen.setText(null);
                        edtKomen.setHint("Beri komentar");
                        Toast.makeText(DetailBeritaActivity.this, "" + pesan, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DetailBeritaActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void initView() {
        imgKomen = (ImageView) findViewById(R.id.img_komen);
        imgWa = (ImageView) findViewById(R.id.img_wa);
        txtPos = (TextView) findViewById(R.id.txt_pos);
        edtKomen = (EditText) findViewById(R.id.edt_komen);
        container = (FrameLayout) findViewById(R.id.container);
        sv = (ScrollView) findViewById(R.id.sv);
        txtJudul = (TextView) findViewById(R.id.txt_judul);
        imgPembuat = (CircleImageView) findViewById(R.id.img_pembuat);
        txtPengepos = (TextView) findViewById(R.id.txt_pengepos);
        txtTanggalPos = (TextView) findViewById(R.id.txt_tanggal_pos);
        txtJumlahLike = (TextView) findViewById(R.id.txt_jumlah_like);
        txtJumlahDislike = (TextView) findViewById(R.id.txt_jumlah_dislike);
        imgBerita = (ImageView) findViewById(R.id.img_berita);
        txtIsiBerita = (TextView) findViewById(R.id.txt_isi_berita);
        rv = (RecyclerView) findViewById(R.id.rv);
        ly = (LinearLayout) findViewById(R.id.ly);
        imgShareWa = (ImageView) findViewById(R.id.img_share_wa);
        imgShareFacebook = (ImageView) findViewById(R.id.img_share_facebook);
    }

    private void awal() {
        shared = new Shared(this);
        txtJudul.setText(shared.getSpJudulBerita());
        Picasso.with(getApplicationContext()).load(Config.URL_IMAGE + shared.getSpGambarBerita()).into(imgBerita);
        txtPengepos.setText(shared.getSpPembuatBerita());
        Picasso.with(getApplicationContext()).load(Config.URL_IMAGE + shared.getSpFotoPembuatBerita()).into(imgPembuat);
        txtTanggalPos.setText(shared.getSpTanggalBerita());
        txtIsiBerita.setText(Html.fromHtml(shared.getSpIsiBerita()));

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        getKomen();
        getLike();
        getDislike();

    }

    private void getLike() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getLikeBerita(shared.getSpBeritaId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.optJSONObject(0);
                        String jumlah = jsonObject.optString("count_like");

                        txtJumlahLike.setText(jumlah);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailBeritaActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDislike() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getDisLikeBerita(shared.getSpBeritaId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.optJSONObject(0);
                        String jumlah = jsonObject.optString("count(like_dislike_id)");

                        txtJumlahDislike.setText(jumlah);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getKomen() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getKomen(shared.getSpBeritaId()).enqueue(new Callback<ArrayList<KomenModel>>() {
            @Override
            public void onResponse(Call<ArrayList<KomenModel>> call, Response<ArrayList<KomenModel>> response) {
                list = response.body();
                if (list.isEmpty()) {
                    Toast.makeText(DetailBeritaActivity.this, "komen kosong", Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter = new KomenAdapter(list, DetailBeritaActivity.this);
                    rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KomenModel>> call, Throwable t) {
//                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
