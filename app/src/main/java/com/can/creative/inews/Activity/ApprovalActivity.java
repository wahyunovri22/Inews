package com.can.creative.inews.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.can.creative.inews.Adapter.ApprovalAdapter;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.Model.BeritaModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalActivity extends AppCompatActivity {

    private CardView cardView2;
    private RecyclerView rv;
    List<BeritaModel> list = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        Awal();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBerita();
            }
        });
    }
    private void Awal(){
        swipe.setColorSchemeResources(R.color.colorPrimary);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        getBerita();
    }

    private void getBerita() {
        swipe.setRefreshing(true);
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getBeritaApproval().enqueue(new Callback<ArrayList<BeritaModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BeritaModel>> call, Response<ArrayList<BeritaModel>> response) {
                swipe.setRefreshing(false);
                list = response.body();
                if (list.isEmpty()){
                    Toast.makeText(getApplicationContext(), "berita belum ada", Toast.LENGTH_SHORT).show();
                }else {
                    mAdapter = new ApprovalAdapter(list,ApprovalActivity.this);
                    rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BeritaModel>> call, Throwable t) {
                swipe.setRefreshing(false);
//                Toast.makeText(ApprovalActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        swipe = findViewById(R.id.swipe);
    }
}
