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
import android.support.v7.widget.CardView;
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

public class TambahVideoActivity extends AppCompatActivity {

    private CardView cardView;
    private ImageView imgHeader;
    private EditText txtCaption;
    private ImageView imgAmbilGambar;
    private EditText txtJudul;
    private FloatingActionButton fabRelease;
    private FloatingActionButton fabPreview;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabCancel;
    private FloatingActionButton fabOpsi;
    Animation FabOpen, FabClose, FabClockwise, FabAnticlockwise;
    boolean isOpen = false;
    private String imagePath, idUser, h;
    File imageFile;
    private EditText edtDeskripsi;
    Shared shared;
    private EditText edtJudul;
    private EditText edtLink;
    private TextView txtKategori;
    private Spinner spnKategori;
    List<String> listNama = new ArrayList<>();
    List<String> listId = new ArrayList<>();
    String idku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_video);
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

        fabRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shared.getSpRule().equals("superadmin")) {
                    tambahVideo();
                } else {
                    tambahVideoAdmin();
                }

            }
        });

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });

    }

    private void Awal(){
        shared = new Shared(this);
        Permission();
        if (shared.getSpRule().equals("superadmin")){
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahVideoActivity.this,
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
                Toast.makeText(TambahVideoActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tambahVideoAdmin() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Menunggu ...");
        pd.setCancelable(false);
        pd.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("video_cover", imageFile.getName(), requestFile);

        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), edtJudul.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), edtDeskripsi.getText().toString());
        RequestBody user_kategori = RequestBody.create(MediaType.parse("text/plain"), shared.getSpUserKategoriId());
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), shared.getSpId());
        RequestBody file = RequestBody.create(MediaType.parse("text/plain"), edtLink.getText().toString());

        ApiService apiService = Client.getInstanceRetrofit();
        apiService.addVideoAdmin(judul,
                body,
                file,
                deskripsi,
                user_kategori,
                user_id
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        new AlertDialog.Builder(TambahVideoActivity.this)
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
                pd.dismiss();
                Toast.makeText(TambahVideoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tambahVideo() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Menunggu ...");
        pd.setCancelable(false);
        pd.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("video_cover", imageFile.getName(), requestFile);

        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), edtJudul.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), edtDeskripsi.getText().toString());
        RequestBody user_kategori = RequestBody.create(MediaType.parse("text/plain"), idku);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), shared.getSpId());
        RequestBody file = RequestBody.create(MediaType.parse("text/plain"), edtLink.getText().toString());

        ApiService apiService = Client.getInstanceRetrofit();
        apiService.addVideoSA(judul,
                body,
                file,
                deskripsi,
                user_kategori,
                user_id
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        new AlertDialog.Builder(TambahVideoActivity.this)
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
                pd.dismiss();
                Toast.makeText(TambahVideoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FabClick() {
        if (isOpen) {
            fabRelease.startAnimation(FabClose);
            fabCancel.startAnimation(FabClose);
            fabOpsi.startAnimation(FabAnticlockwise);
            fabRelease.setClickable(false);
            fabCancel.setClickable(false);
            isOpen = false;
        } else {
            fabRelease.startAnimation(FabOpen);
            fabCancel.startAnimation(FabOpen);
            fabOpsi.startAnimation(FabClockwise);
            fabRelease.setClickable(true);
            fabCancel.setClickable(true);
            isOpen = true;
        }
    }

    private void Permission() {
        if (ContextCompat.checkSelfPermission(TambahVideoActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(TambahVideoActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(TambahVideoActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void Clear() {
        edtJudul.setText(null);
        edtJudul.setHint("Judul ....");
        edtLink.setText(null);
        edtLink.setHint("Link");
        edtDeskripsi.setText(null);
        edtDeskripsi.setHint("Deskripsi");
        imgHeader.setImageResource(R.drawable.no_image_found);
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
//                uploadImage();i
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
        cardView = (CardView) findViewById(R.id.card_view);
        imgHeader = (ImageView) findViewById(R.id.img_header);
        imgAmbilGambar = (ImageView) findViewById(R.id.img_ambil_gambar);
        fabRelease = (FloatingActionButton) findViewById(R.id.fab_release);
        fabCancel = (FloatingActionButton) findViewById(R.id.fab_cancel);
        fabOpsi = (FloatingActionButton) findViewById(R.id.fab_opsi);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        FabAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        edtDeskripsi = (EditText) findViewById(R.id.edt_deskripsi);
        edtJudul = (EditText) findViewById(R.id.edt_judul);
        edtLink = (EditText) findViewById(R.id.edt_link);
        txtKategori = (TextView) findViewById(R.id.txt_kategori);
        spnKategori = (Spinner) findViewById(R.id.spn_kategori);
    }
}
