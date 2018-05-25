package com.can.creative.inews.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.can.creative.inews.Adapter.UserApproveAdapter;
import com.can.creative.inews.Model.UserModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalUserActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipe;
    private RecyclerView rv;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<UserModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_user);
        initView();

        Awal();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUser();
            }
        });

    }

    private void Awal(){
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        getUser();
    }

    private void getUser(){
        swipe.setRefreshing(true);
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getUserApprove().enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                if (response.isSuccessful()){
                    swipe.setRefreshing(false);
                  list = response.body();
                  mAdapter = new UserApproveAdapter(list, ApprovalUserActivity.this);
                  rv.setAdapter(mAdapter);
                  mAdapter.notifyDataSetChanged();
                  swipe.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                swipe.setRefreshing(false);
            }
        });
    }
    private void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        rv = (RecyclerView) findViewById(R.id.rv);
    }
}
