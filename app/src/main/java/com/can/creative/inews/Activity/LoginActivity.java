package com.can.creative.inews.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;
    private MaterialEditText edtUsername;
    private MaterialEditText edtPassword;
    Shared shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        Awal();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProsesLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ProsesLogin(){
        if (edtUsername.getText().toString().isEmpty()) {
            edtUsername.setError("username harus diisi");
        } else if (edtPassword.getText().toString().isEmpty()) {
            edtUsername.setError("password harus diisi");
        } else {
            Login();
        }
    }
    private void Awal() {
        shared = new Shared(this);
        if (shared.getSPSudahLogin()) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void Login() {
        final AlertDialog pd;
        pd = new SpotsDialog(this, R.style.Custom);
        pd.show();
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.login(edtUsername.getText().toString(),
                edtPassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String id = jsonObject.optString("user_id");
                        String username = jsonObject.optString("user_name");
                        String nama = jsonObject.optString("user_nama");
                        String rule = jsonObject.optString("user_role");
                        String email = jsonObject.optString("user_email");
                        String nip = jsonObject.optString("user_nip");
                        String user_kategori_id = jsonObject.optString("user_kategori_id");
                        String user_kategori = jsonObject.optString("kategori_name");
                        String gambar = jsonObject.optString("user_gambar");

                        shared.saveSPString(shared.SP_ID, id);
                        shared.saveSPString(shared.SP_USERNAME, username);
                        shared.saveSPString(shared.SP_USER_KATEGORI, user_kategori);
                        shared.saveSPString(shared.SP_NAMA, nama);
                        shared.saveSPString(shared.SP_RULE, rule);
                        shared.saveSPString(shared.SP_EMAIL, email);
                        shared.saveSPString(shared.SP_NIP, nip);
                        shared.saveSPString(shared.SP_USER_KATEGORI_ID, user_kategori_id);
                        shared.saveSPString(shared.SP_GAMBAR, gambar);
                        shared.saveSPBoolean(shared.SP_SUDAH_LOGIN, true);
                        if (rule.equals("superadmin")){
                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else if (rule.equals("user")){
                            Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else if (rule.equals("admin2")){
                            Intent intent = new Intent(getApplicationContext(), AdminDuaActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Akun Tidak ada", Toast.LENGTH_SHORT).show();
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
                pd.dismiss();
                Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);
        edtUsername = (MaterialEditText) findViewById(R.id.edt_username);
        edtPassword = (MaterialEditText) findViewById(R.id.edt_password);
    }
}
