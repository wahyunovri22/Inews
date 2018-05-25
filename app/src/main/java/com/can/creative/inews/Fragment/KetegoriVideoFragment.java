package com.can.creative.inews.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.can.creative.inews.Adapter.VideoAdapter;
import com.can.creative.inews.Model.VideoModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class KetegoriVideoFragment extends Fragment {

    String slug;
    private SwipeRefreshLayout swipe;
    private RecyclerView rv;
    List<VideoModel> list = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @SuppressLint("ValidFragment")
    public KetegoriVideoFragment(String slug) {
        // Required empty public constructor
        this.slug = slug;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ketegori_video, container, false);
        initView(view);

        Awal();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVideo(slug);
            }
        });
        return view;
    }

    private void Awal(){
        layoutManager =new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);

        getVideo(slug);
    }

    private void getVideo(String slug){
        swipe.setRefreshing(true);
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getVideoByKategori(slug).enqueue(new Callback<ArrayList<VideoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<VideoModel>> call, Response<ArrayList<VideoModel>> response) {
                if (response.isSuccessful()){
                    swipe.setRefreshing(false);
                    list = response.body();
                    if (list.isEmpty()){
                        Toast.makeText(getActivity(), "Vieo Belum Ada", Toast.LENGTH_SHORT).show();
                    }else {
                        mAdapter = new VideoAdapter(list, getActivity());
                        rv.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VideoModel>> call, Throwable t) {
                swipe.setRefreshing(false);
            }
        });
    }

    private void initView(View view) {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        rv = (RecyclerView) view.findViewById(R.id.rv);
    }
}
