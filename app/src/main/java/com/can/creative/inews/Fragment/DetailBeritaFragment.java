package com.can.creative.inews.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Adapter.KomenAdapter;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.Model.KomenModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBeritaFragment extends Fragment {


    private TextView txtJudul;
    private CircleImageView imgPembuat;
    private TextView txtPengepos;
    private TextView txtTanggalPos;
    private ImageView imgBerita;
    private TextView txtIsiBerita;
    Shared shared;
    private RecyclerView rv;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<KomenModel> list = new ArrayList<>();
    private TextView txtJumlahLike;
    private TextView txtJumlahDislike;

    public DetailBeritaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_berita, container, false);
        initView(view);
        Awal();
        return view;
    }

    private void Awal() {
        shared = new Shared(getActivity());
        txtJudul.setText(shared.getSpJudulBerita());
        Picasso.with(getActivity()).load(Config.URL_IMAGE + shared.getSpGambarBerita()).into(imgBerita);
        txtPengepos.setText(shared.getSpPembuatBerita());
        Picasso.with(getActivity()).load(Config.URL_IMAGE + shared.getSpFotoPembuatBerita()).into(imgPembuat);
        txtTanggalPos.setText(shared.getSpTanggalBerita());
        txtIsiBerita.setText(Html.fromHtml(shared.getSpIsiBerita()));

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        getKomen();
        getLike();
        getDislike();
    }

    private void getLike(){
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getLikeBerita(shared.getSpBeritaId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.optJSONObject(0);
                        String jumlah = jsonObject.optString("count_like");

                        txtJumlahLike.setText(jumlah);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDislike(){
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getDisLikeBerita(shared.getSpBeritaId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.optJSONObject(0);
                        String jumlah = jsonObject.optString("count(like_dislike_id)");

                        txtJumlahDislike.setText(jumlah);
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

    private void getKomen() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getKomen(shared.getSpBeritaId()).enqueue(new Callback<ArrayList<KomenModel>>() {
            @Override
            public void onResponse(Call<ArrayList<KomenModel>> call, Response<ArrayList<KomenModel>> response) {
                list = response.body();
                if (list.isEmpty()) {
                    Toast.makeText(getActivity(), "komen kosong", Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter = new KomenAdapter(list, getActivity());
                    rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KomenModel>> call, Throwable t) {
//                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        txtJudul = (TextView) view.findViewById(R.id.txt_judul);
        imgPembuat = (CircleImageView) view.findViewById(R.id.img_pembuat);
        txtPengepos = (TextView) view.findViewById(R.id.txt_pengepos);
        txtTanggalPos = (TextView) view.findViewById(R.id.txt_tanggal_pos);
        imgBerita = (ImageView) view.findViewById(R.id.img_berita);
        txtIsiBerita = (TextView) view.findViewById(R.id.txt_isi_berita);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        txtJumlahLike = (TextView) view.findViewById(R.id.txt_jumlah_like);
        txtJumlahDislike = (TextView) view.findViewById(R.id.txt_jumlah_dislike);
    }
}
