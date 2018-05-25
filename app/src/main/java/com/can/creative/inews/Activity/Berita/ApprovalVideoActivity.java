package com.can.creative.inews.Activity.Berita;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.can.creative.inews.Adapter.VideoAdapter;
import com.can.creative.inews.Adapter.VideoApproveAdapter;
import com.can.creative.inews.Model.VideoModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalVideoActivity extends AppCompatActivity {

    private RecyclerView div;
    List<VideoModel> list = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        Awal();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVideo();
            }
        });
    }

    private void Awal() {
        swipe.setColorSchemeResources(R.color.colorPrimaryDark);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        div.setLayoutManager(layoutManager);

        getVideo();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVideo();
            }
        });
    }

    private void getVideo() {
        swipe.setRefreshing(true);
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getVideoApproval().enqueue(new Callback<ArrayList<VideoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<VideoModel>> call, Response<ArrayList<VideoModel>> response) {
                if (response.isSuccessful()) {
                    swipe.setRefreshing(false);
                    list = response.body();
                    mAdapter = new VideoApproveAdapter(list, ApprovalVideoActivity.this);
                    div.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VideoModel>> call, Throwable t) {
                swipe.setRefreshing(false);
            }
        });
    }

    private void initView() {
        div = (RecyclerView) findViewById(R.id.div);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
    }
}
