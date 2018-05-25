package com.can.creative.inews.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.can.creative.inews.Fragment.KategoriSatuFragment;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.R;
import com.squareup.picasso.Picasso;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Shared shared;
    TextView txtNama, txtKantorWilayah;
    ImageView imgDepan;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shared = new Shared(this);

        this.handler = new Handler();
        this.handler.postDelayed(runnable, 2000);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        txtNama = header.findViewById(R.id.txt_nama);
        txtKantorWilayah = header.findViewById(R.id.txt_kantor_wilayah);
        imgDepan = header.findViewById(R.id.imageView);

        Awal();
    }

    private void Awal() {
        txtNama.setText(shared.getSpNama());
        Picasso.with(getApplicationContext())
                .load(Config.URL_IMAGE + shared.getSpGambar())
                .error(R.drawable.no_image_found)
                .into(imgDepan);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contentku, new KategoriSatuFragment())
                .commit();
        getSupportActionBar().setTitle("Home");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Apakah Anda ingin keluar?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UserHomeActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contentku, new KategoriSatuFragment())
                    .commit();
            getSupportActionBar().setTitle("Home");
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_static) {
            Intent intent = new Intent(getApplicationContext(), StatisticActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            shared.saveSPBoolean(shared.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            txtNama.setText(shared.getSpNama());
            Picasso.with(getApplicationContext())
                    .load(Config.URL_IMAGE + shared.getSpGambar())
                    .error(R.drawable.no_image_found)
                    .into(imgDepan);
            UserHomeActivity.this.handler.postDelayed(runnable, 3000);
        }
    };
}
