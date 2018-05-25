package com.can.creative.inews.Activity.Berita;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBeritaDuaActivity extends AppCompatActivity {

    private ScrollView sv;
    private TextView txtJudul;
    private CircleImageView imgPembuat;
    private TextView txtPengepos;
    private TextView txtTanggalPos;
    private ImageView imgBerita;
    private TextView txtIsiBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita_dua);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        Awal();
    }

    private void Awal(){
        txtJudul.setText(getIntent().getStringExtra("judul"));
        Picasso.with(getApplicationContext())
                .load(Config.URL_IMAGE + getIntent().getStringExtra("image_pembuat"))
                .error(R.drawable.no_image_found)
                .into(imgPembuat);
        txtPengepos.setText(getIntent().getStringExtra("pembuat"));
        txtTanggalPos.setText(getIntent().getStringExtra("tanggal"));
        Picasso.with(getApplicationContext())
                .load(Config.URL_IMAGE + getIntent().getStringExtra("image"))
                .error(R.drawable.no_image_found)
                .into(imgBerita);
        txtIsiBerita.setText(Html.fromHtml(getIntent().getStringExtra("isi")));
    }

    private void initView() {
        sv = (ScrollView) findViewById(R.id.sv);
        txtJudul = (TextView) findViewById(R.id.txt_judul);
        imgPembuat = (CircleImageView) findViewById(R.id.img_pembuat);
        txtPengepos = (TextView) findViewById(R.id.txt_pengepos);
        txtTanggalPos = (TextView) findViewById(R.id.txt_tanggal_pos);
        imgBerita = (ImageView) findViewById(R.id.img_berita);
        txtIsiBerita = (TextView) findViewById(R.id.txt_isi_berita);
    }
}
