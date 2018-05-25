package com.can.creative.inews.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Activity.Berita.LihatActivity;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBeritaActivity extends AppCompatActivity {

    private FloatingActionButton fabPreview;
    private FloatingActionButton fabRelease;
    private FloatingActionButton fabOpsi;
    Animation FabOpen, FabClose, FabClockwise, FabAnticlockwise;
    boolean isOpen = false;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabCancel;
    private ImageView imgHeader;
    private EditText txtCaption;
    private ImageView imgAmbilGambar;
    private EditText txtJudul;
    private EditText txtIsi;
    private String imagePath, idUser, h;
    File imageFile;
    private EditText edtCaption;
    private EditText edtJudul;
    private EditText edtIsi;
    Shared shared;
    List<String> listNama = new ArrayList<>();
    List<String> listId = new ArrayList<>();
    private TextView txtKategori;
    private Spinner spnKategori;
    String idku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_berita);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initView();
        Awal();

        fabOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabClick();
            }
        });
        imgAmbilGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooserGalerry();
            }
        });
        fabPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCaption.getText().toString().isEmpty()){
                    Toast.makeText(TambahBeritaActivity.this, "judul dan gambar harus diisi", Toast.LENGTH_SHORT).show();
                }else if (edtIsi.getText().toString().isEmpty()){
                    Toast.makeText(TambahBeritaActivity.this, "isi berita harus diisi", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), LihatActivity.class);
                    intent.putExtra("image", imagePath);
                    intent.putExtra("judul", edtCaption.getText().toString());
                    intent.putExtra("isi", edtIsi.getText().toString());
//                intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });
        fabRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getSpRule().equals("superadmin")) {
                    beritaSA();
                } else {
                    beritaAdmin();
                }
            }
        });
    }

    private void Awal() {
        shared = new Shared(this);
        Permission();
        getKategori();

        if (shared.getSpRule().equals("superadmin")) {
            txtKategori.setVisibility(View.VISIBLE);
            spnKategori.setVisibility(View.VISIBLE);
            getKategori();
        }
    }

    private void getKategori() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getKategori().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            String id = jsonObject.optString("kategori_id");
                            String nama = jsonObject.optString("kategori_name");

                            listNama.add(nama);
                            listId.add(id);
//                            Toast.makeText(RegisterActivity.this, ""+listNama, Toast.LENGTH_SHORT).show();
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahBeritaActivity.this,
                                    R.layout.support_simple_spinner_dropdown_item, listNama);
                            spnKategori.setAdapter(adapter);

                            spnKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    for (int j = 0; j < listNama.size(); j++) {
                                        if (listNama != null && listNama.get(i).contains(spnKategori.getSelectedItem().toString().trim())) {
                                            idku = listId.get(i);
//                                            Toast.makeText(RegisterActivity.this, ""+idku, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(PendaftaranNasabahKeAgenUserActivity.this, "" + idProdukCompany, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
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
                Toast.makeText(TambahBeritaActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Clear() {
        edtCaption.setText(null);
        edtCaption.setHint("Judul ....");
        edtIsi.setText(null);
        edtIsi.setHint("Isi");
        imgHeader.setImageResource(R.drawable.no_image_found);
    }

    private void beritaAdmin() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Mohon Tunggu ...");
        pd.setCancelable(false);
        pd.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("berita_gambar", imageFile.getName(), requestFile);

        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), edtCaption.getText().toString());
        RequestBody isi = RequestBody.create(MediaType.parse("text/plain"), edtIsi.getText().toString());
        RequestBody user_kategori = RequestBody.create(MediaType.parse("text/plain"), shared.getSpUserKategoriId());
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), shared.getSpId());

        ApiService apiService = Client.getInstanceRetrofit();
        apiService.tambahBeritaAdmin(judul,
                body,
                isi,
                user_kategori,
                user_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        new AlertDialog.Builder(TambahBeritaActivity.this)
                                .setMessage("" + pesan)
                                .setCancelable(false)
                                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Clear();
                                    }
                                })
                                .show();
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

    private void beritaSA() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Mohon Tunggu ...");
        pd.setCancelable(false);
        pd.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("berita_gambar", imageFile.getName(), requestFile);

        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), edtCaption.getText().toString());
        RequestBody isi = RequestBody.create(MediaType.parse("text/plain"), edtIsi.getText().toString());
        RequestBody user_kategori = RequestBody.create(MediaType.parse("text/plain"), idku);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), shared.getSpId());

        ApiService apiService = Client.getInstanceRetrofit();
        apiService.tambahBeritaSA(judul,
                body,
                isi,
                user_kategori,
                user_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        new AlertDialog.Builder(TambahBeritaActivity.this)
                                .setMessage("" + pesan)
                                .setCancelable(false)
                                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Clear();
                                    }
                                })
                                .show();
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
        fabPreview = (FloatingActionButton) findViewById(R.id.fab_preview);
        fabRelease = (FloatingActionButton) findViewById(R.id.fab_release);
        fabOpsi = (FloatingActionButton) findViewById(R.id.fab_opsi);
        fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        fabCancel = (FloatingActionButton) findViewById(R.id.fab_cancel);
        imgHeader = (ImageView) findViewById(R.id.img_header);
        imgAmbilGambar = (ImageView) findViewById(R.id.img_ambil_gambar);
        edtCaption = (EditText) findViewById(R.id.edt_caption);
        edtJudul = (EditText) findViewById(R.id.edt_judul);
        edtIsi = (EditText) findViewById(R.id.edt_isi);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        FabAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        txtKategori = (TextView) findViewById(R.id.txt_kategori);
        spnKategori = (Spinner) findViewById(R.id.spn_kategori);
    }

    private void FabClick() {
        if (isOpen) {
            fabPreview.startAnimation(FabClose);
            fabRelease.startAnimation(FabClose);
            fabCancel.startAnimation(FabClose);
            fabSave.startAnimation(FabClose);
            fabOpsi.startAnimation(FabAnticlockwise);
            fabRelease.setClickable(false);
            fabPreview.setClickable(false);
            fabCancel.setClickable(false);
            fabSave.setClickable(false);
            isOpen = false;
        } else {
            fabPreview.startAnimation(FabOpen);
            fabRelease.startAnimation(FabOpen);
            fabCancel.startAnimation(FabOpen);
            fabSave.startAnimation(FabOpen);
            fabOpsi.startAnimation(FabClockwise);
            fabRelease.setClickable(true);
            fabPreview.setClickable(true);
            fabCancel.setClickable(true);
            fabSave.setClickable(true);
            isOpen = true;
        }
    }

    private void Permission() {
        if (ContextCompat.checkSelfPermission(TambahBeritaActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(TambahBeritaActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(TambahBeritaActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
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
                Picasso.with(this).load(new File(imagePath)).into(imgHeader);
                h = new File(imagePath).getName();
//                uploadImage();
//                Toast.makeText(this, "Mbuh", Toast.LENGTH_SHORT).show();
                c.close();
                imageFile = new File(imagePath);

//                te.setVisibility(View.GONE);
//                imageVi.setVisibility(View.VISIBLE);
            } else {
//                te.setVisibility(View.VISIBLE);
//                imageVi.setVisibility(View.GONE);
                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
