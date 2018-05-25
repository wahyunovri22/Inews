package com.can.creative.inews.Activity.Berita;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Activity.DetailBeritaActivity;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaSayaActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipe;
    private LinearLayout div;
    Shared shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_saya);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
        shared = new Shared(this);
        getBerita(true);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBerita(true);
            }
        });
    }

    private void getBerita(boolean rm){
        if (rm) {
            if (div.getChildCount() > 0) div.removeAllViews();
        }
        swipe.setRefreshing(true);
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.myNews(shared.getSpId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    swipe.setRefreshing(false);
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        if (jsonArray == null){
                            Toast.makeText(BeritaSayaActivity.this, "data Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = jsonArray.optJSONObject(i);
                                final String id = jsonObject.optString("berita_id");
                                final String gambar = jsonObject.optString("berita_gambar");
                                final String judul = jsonObject.optString("berita_judul");
                                final String isi = jsonObject.optString("berita_isi");
                                final String tanggal = jsonObject.optString("created_at");
                                final String nama_user = jsonObject.optString("user_nama");
                                String rule = jsonObject.optString("user_role");
                                String kategori = jsonObject.optString("kategori_name");
                                String like = jsonObject.optString("like");
                                String dislike = jsonObject.optString("dislike");
                                String komen = jsonObject.optString("comment");
                                String berit_user_id = jsonObject.optString("berita_user_id");
                                final String user_gambar = jsonObject.optString("user_gambar");

                                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View addview = layoutInflater.inflate(R.layout.row_news, null);

                                    final TextView txtJudul = addview.findViewById(R.id.txt_judul);
                                    final TextView txtPengirim = addview.findViewById(R.id.txt_pengirim);
                                    final ImageView imgBerita = addview.findViewById(R.id.img_berita);
                                    final TextView txtLike = addview.findViewById(R.id.txt_like);
                                    final TextView txtDislike = addview.findViewById(R.id.txt_dislike);
                                    final TextView txtKomen = addview.findViewById(R.id.txt_komen);
                                    final CardView cardView = addview.findViewById(R.id.card);

                                    txtJudul.setText(judul);
                                    txtPengirim.setText(kategori);
                                    txtDislike.setText(dislike);
                                    txtLike.setText(like);
                                    txtKomen.setText(komen);
                                    Picasso.with(getApplicationContext())
                                            .load(Config.URL_IMAGE + gambar)
                                            .error(R.drawable.no_image_found)
                                            .into(imgBerita);

                                    cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(), DetailBeritaActivity.class);
                                            shared.saveSPString(shared.SP_JUDUL_BERITA, judul);
                                            shared.saveSPString(shared.SP_ISI_BERITA, isi);
                                            shared.saveSPString(shared.SP_GAMBAR_BERITA, gambar);
                                            shared.saveSPString(shared.SP_TANGGAL_BERITA, tanggal);
                                            shared.saveSPString(shared.SP_PEMBUAT_BERITA, nama_user);
                                            shared.saveSPString(shared.SP_FOTO_PEMBUAT_BERITA, user_gambar);
                                            shared.saveSPString(shared.SP_BERITA_ID, id);
                                            startActivity(intent);
                                        }
                                    });


                                    div.addView(addview);
                                }
                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipe.setRefreshing(false);
                Toast.makeText(BeritaSayaActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        div = (LinearLayout) findViewById(R.id.div);
    }
}
