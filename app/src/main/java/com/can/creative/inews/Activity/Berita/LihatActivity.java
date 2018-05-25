package com.can.creative.inews.Activity.Berita;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.can.creative.inews.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class LihatActivity extends AppCompatActivity {

    private TextView txtJudul;
    private ImageView imgBerita;
    private TextView txtIsiBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        Awal();
    }

    private void Awal(){
        txtJudul.setText(getIntent().getStringExtra("judul"));
        txtIsiBerita.setText(getIntent().getStringExtra("isi"));
        Picasso.with(this).load(new File(getIntent().getStringExtra("image"))).into(imgBerita);
    }

    private void initView() {
        txtJudul = (TextView) findViewById(R.id.txt_judul);
        imgBerita = (ImageView) findViewById(R.id.img_berita);
        txtIsiBerita = (TextView) findViewById(R.id.txt_isi_berita);
    }
}
