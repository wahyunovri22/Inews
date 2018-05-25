package com.can.creative.inews.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Adapter.VideoAdapter;
import com.can.creative.inews.Model.VideoModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.youtubeplayer.ui.PlayerUIController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDuaFragment extends Fragment {


    String slug;
    private SwipeRefreshLayout swipe;
    private LinearLayout div;
    private RecyclerView rv;
    List<VideoModel> list = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    public VideoDuaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_dua, container, false);
        initView(view);

        Awal();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVideo();
            }
        });
        return view;
    }

    private void Awal(){
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);

        getVideo();
    }

    private void getVideo() {
       swipe.setRefreshing(true);
       ApiService apiService = Client.getInstanceRetrofit();
       apiService.getAllVideo().enqueue(new Callback<ArrayList<VideoModel>>() {
           @Override
           public void onResponse(Call<ArrayList<VideoModel>> call, Response<ArrayList<VideoModel>> response) {
               if (response.isSuccessful()){
                   swipe.setRefreshing(false);
                   list = response.body();
                   if (list == null){
                       Toast.makeText(getContext(), "video kosong", Toast.LENGTH_SHORT).show();
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
               Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void initView(View view) {
        swipe = view.findViewById(R.id.swipe);
        rv =view.findViewById(R.id.rv);
    }
}
