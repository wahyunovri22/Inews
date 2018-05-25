package com.can.creative.inews.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.rengwuxian.materialedittext.MaterialEditText;

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

public class RegisterActivity extends AppCompatActivity {

    private CircleImageView ciFoto;
    private Switch switch1;
    private Button btnLogin;
    private MaterialEditText edtEmail;
    private MaterialEditText edtNip;
    private MaterialEditText edtUsername;
    private MaterialEditText edtPassword;
    private Spinner spnKategori;
    private Button btnRegister;
    List<String> listNama = new ArrayList<>();
    List<String> listId = new ArrayList<>();
    String idku;
    private MaterialEditText edtNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        getKategori();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Mohon Tunggu");
        pd.setCancelable(false);
        pd.show();
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.Register(edtUsername.getText().toString(),
                idku,
                edtNip.getText().toString(),
                edtNama.getText().toString(),
                edtPassword.getText().toString(),edtEmail.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        pd.dismiss();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        Toast.makeText(RegisterActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
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
                Toast.makeText(RegisterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
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
                Toast.makeText(RegisterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        ciFoto = (CircleImageView) findViewById(R.id.ciFoto);
        switch1 = (Switch) findViewById(R.id.switch1);
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtEmail = (MaterialEditText) findViewById(R.id.edt_email);
        edtNip = (MaterialEditText) findViewById(R.id.edt_nip);
        edtUsername = (MaterialEditText) findViewById(R.id.edt_username);
        edtPassword = (MaterialEditText) findViewById(R.id.edt_password);
        spnKategori = (Spinner) findViewById(R.id.spn_kategori);
        btnRegister = (Button) findViewById(R.id.btn_register);
        edtNama = (MaterialEditText) findViewById(R.id.edt_nama);
    }
}
