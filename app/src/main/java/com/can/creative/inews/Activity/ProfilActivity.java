package com.can.creative.inews.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {

    private CardView cartTelepon;
    private CardView cardPosisi;
    private CardView cardEmail;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private CircleImageView ciFoto;
    private TextView tvProfilNamaLengkap;
    private TextView txtRule;
    private TextView txtEmail;
    Shared shared;
    private TextView txtDaerah;
    private CardView cardNip;
    private TextView txtNip;
    private String imagePath, idUser, h;
    File imageFile;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.back);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        this.handler = new Handler();
        this.handler.postDelayed(runnable, 2000);
        Permission();
        Awal();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooserGalerry();
            }
        });

        cardEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEmail();
            }
        });

        cardNip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editNip();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            editNama();
        }
        if (id == R.id.action_ganti_password) {
            editPassword();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void Awal() {
        shared = new Shared(this);
        h = shared.getSpGambar();
        tvProfilNamaLengkap.setText(shared.getSpNama());
        txtEmail.setText(shared.getSpEmail());
        txtRule.setText(shared.getSpRule());
        txtDaerah.setText(shared.getSpUserKategori());
        txtNip.setText(shared.getSpNip());
        Picasso.with(getApplicationContext())
                .load(Config.URL_IMAGE + shared.getSpGambar())
                .error(R.drawable.no_image_found)
                .into(ciFoto);
    }

    private void initView() {
        cartTelepon = (CardView) findViewById(R.id.card_telepon);
        cardPosisi = (CardView) findViewById(R.id.card_posisi);
        cardEmail = (CardView) findViewById(R.id.card_email);
        ciFoto = (CircleImageView) findViewById(R.id.ciFoto);
        tvProfilNamaLengkap = (TextView) findViewById(R.id.tvProfilNamaLengkap);
        txtRule = (TextView) findViewById(R.id.txt_rule);
        txtEmail = (TextView) findViewById(R.id.txt_email);
        txtDaerah = (TextView) findViewById(R.id.txt_daerah);
        cardNip = (CardView) findViewById(R.id.card_nip);
        txtNip = (TextView) findViewById(R.id.txt_nip);
    }

    private void editTelepon() {
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.ganti_telp, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Ganti No Telepon Baru");

        final EditText edtUcapan = (EditText) dialogView.findViewById(R.id.edt_ucapan);

        edtUcapan.setText(null);

        dialog.setPositiveButton("GANTI", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtUcapan.getText().toString();

                Toast.makeText(ProfilActivity.this, "" + edtUcapan.getText().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void editNama() {
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.ganti_telp, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Ganti Nama Anda");

        final EditText edtUcapan = (EditText) dialogView.findViewById(R.id.edt_ucapan);
        final TextView txtJudul = (TextView) dialogView.findViewById(R.id.txt_judul);
        txtJudul.setText("Ganti Nama Anda");

        edtUcapan.setText(null);

        dialog.setPositiveButton("Rename", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ProgressDialog pd = new ProgressDialog(ProfilActivity.this);
                pd.setTitle("Mohon Tunggu ...");
                pd.setCancelable(false);
                pd.show();

                ApiService apiService = Client.getInstanceRetrofit();
                apiService.editProfilDua(shared.getSpId(),
                        edtUcapan.getText().toString(),
                        txtNip.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            pd.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String pesan = jsonObject.optString("pesan");
                                Toast.makeText(ProfilActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                                shared.saveSPString(shared.SP_GAMBAR, h);
                                shared.saveSPString(shared.SP_NAMA, edtUcapan.getText().toString());
                                shared.saveSPString(shared.SP_NIP, txtNip.getText().toString());
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
                        Toast.makeText(ProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void editPosisi() {
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.ganti_telp, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Ganti Posisi");

        final EditText edtUcapan = (EditText) dialogView.findViewById(R.id.edt_ucapan);
        final TextView txtJudul = (TextView) dialogView.findViewById(R.id.txt_judul);
        txtJudul.setText("Ganti Pososi");

        edtUcapan.setText(null);

        dialog.setPositiveButton("GANTI", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtUcapan.getText().toString();

                Toast.makeText(ProfilActivity.this, "" + edtUcapan.getText().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void editEmail() {
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.ganti_telp, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Ganti Email");

        final EditText edtUcapan = (EditText) dialogView.findViewById(R.id.edt_ucapan);
        final TextView txtJudul = (TextView) dialogView.findViewById(R.id.txt_judul);
        txtJudul.setText("Ganti Email");

        edtUcapan.setText(null);

        dialog.setPositiveButton("GANTI", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtUcapan.getText().toString();

                Toast.makeText(ProfilActivity.this, "" + edtUcapan.getText().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void editPassword() {
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.ganti_pass, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Ganti Password");

        final EditText edtLama = (EditText) dialogView.findViewById(R.id.edt_pass_lama);
        final EditText edtBaru= (EditText) dialogView.findViewById(R.id.edt_pass_baru);
        final TextView txtJudul = (TextView) dialogView.findViewById(R.id.txt_judul);
        txtJudul.setText("Ganti Password");

        dialog.setPositiveButton("Ganti", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog pd = new ProgressDialog(ProfilActivity.this);
                pd.setCancelable(false);
                pd.show();
                ApiService apiService = Client.getInstanceRetrofit();
                apiService.editPassword(shared.getSpId(),
                        shared.getSpUsername(),
                        edtLama.getText().toString(),
                        edtBaru.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pd.dismiss();
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String pesan = jsonObject.optString("pesan");
                                Toast.makeText(ProfilActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void editNip(){
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.ganti_telp, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Ganti Nip");

        final EditText edtUcapan = (EditText) dialogView.findViewById(R.id.edt_ucapan);
        final TextView txtJudul = (TextView) dialogView.findViewById(R.id.txt_judul);
        txtJudul.setText("Ganti Nip");

        edtUcapan.setText(null);

        dialog.setPositiveButton("Ganti", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ProgressDialog pd = new ProgressDialog(ProfilActivity.this);
                pd.setTitle("Mohon Tunggu ...");
                pd.setCancelable(false);
                pd.show();

                ApiService apiService = Client.getInstanceRetrofit();
                apiService.editProfilDua(shared.getSpId(),
                        tvProfilNamaLengkap.getText().toString(),
                        edtUcapan.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            pd.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String pesan = jsonObject.optString("pesan");
                                Toast.makeText(ProfilActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                                shared.saveSPString(shared.SP_GAMBAR, h);
                                shared.saveSPString(shared.SP_NAMA, tvProfilNamaLengkap.getText().toString());
                                shared.saveSPString(shared.SP_NIP, edtUcapan.toString());
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
                        Toast.makeText(ProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void Permission() {
        if (ContextCompat.checkSelfPermission(ProfilActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ProfilActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ProfilActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void editImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Mohon Tunggu ...");
        pd.setCancelable(false);
        pd.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("user_gambar", imageFile.getName(), requestFile);

        RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), tvProfilNamaLengkap.getText().toString());
        RequestBody nip = RequestBody.create(MediaType.parse("text/plain"), txtNip.getText().toString());
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), shared.getSpId());

        shared.saveSPString(shared.SP_GAMBAR, imageFile.getName());
        shared.saveSPString(shared.SP_NAMA, tvProfilNamaLengkap.getText().toString());
        shared.saveSPString(shared.SP_NIP, txtNip.getText().toString());
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.editProfil(user_id,
                body,
                nama,
                nip).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        Toast.makeText(ProfilActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //gallery
    private void showFileChooserGalerry() {
        Intent qq = new Intent(Intent.ACTION_PICK);
        qq.setType("image/*");
        startActivityForResult(Intent.createChooser(qq, "Pilih Foto"), 100);
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), Config.PICK_FILE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
                return;

            }
//            else {
//                Toast.makeText(this, "Gambar Ada", Toast.LENGTH_SHORT).show();
//            }
            Uri selectImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
            if (c != null) {
                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePathColumn[0]);
                imagePath = c.getString(columnIndex);

//                Glide.with(this).load(new File(imagePath)).into(cvEditProfil);
                Picasso.with(this).load(new File(imagePath)).into(ciFoto);
                h = new File(imagePath).getName();
                imageFile = new File(imagePath);
//                uploadImage();
//                Toast.makeText(this, "Mbuh", Toast.LENGTH_SHORT).show();
                c.close();
                editImage();

//                te.setVisibility(View.GONE);
//                imageVi.setVisibility(View.VISIBLE);
            } else {
//                te.setVisibility(View.VISIBLE);
//                imageVi.setVisibility(View.GONE);
                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Awal();
            ProfilActivity.this.handler.postDelayed(runnable, 3000);
        }
    };

}
