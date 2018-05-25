package com.can.creative.inews.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.can.creative.inews.Activity.DetailBeritaActivity;
import com.can.creative.inews.Adapter.BeritaAdapter;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.Model.BeritaModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class NewsDuaFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;
    private SliderLayout sliderSlider;
    private PagerIndicator customIndicator;
    private PagerIndicator customIndicator2;
    private RelativeLayout cardLayout2;
    private CardView cardView2;
    private ImageView ivSharelist;
    String slug;
    private RecyclerView rv;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<BeritaModel> list = new ArrayList<>();
    private SwipeRefreshLayout swipe;
    List<String> listGambar = new ArrayList<>();
    List<String > listJudul = new ArrayList<>();
    List<String> listId = new ArrayList<>();
    List<String> listIsi = new ArrayList<>();
    List<String> listTanggal = new ArrayList<>();
    List<String> listNama = new ArrayList<>();
    List<String> listGambarPembuat = new ArrayList<>();
    HashMap<String, String> url_maps;
    HashMap<String, Integer> file_maps;
    Shared shared;
    String gambar;

//    public static NewsDuaFragment newInstance() {
//        return new NewsDuaFragment();
//    }

    @SuppressLint("ValidFragment")
    public NewsDuaFragment(String slug) {
        // Required empty public constructor
        this.slug = slug;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_dua, container, false);
        initView(view);

        mDemoSlider = (SliderLayout) view.findViewById(R.id.sliderSlider);

        Awal();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBerita(slug);
            }
        });
        return view;
    }

    private void Awal() {
        shared = new Shared(getActivity());
        swipe.setColorSchemeResources(R.color.colorPrimary);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        getBerita(slug);
        getHeadline();
    }

    private void getBerita(String slug) {
//        final AlertDialog pd;
//        pd = new SpotsDialog(getActivity(), R.style.Custom);
//        pd.show();
        swipe.setRefreshing(true);
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getBeritaKategori(slug).enqueue(new Callback<ArrayList<BeritaModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BeritaModel>> call, Response<ArrayList<BeritaModel>> response) {
//                pd.dismiss();
                swipe.setRefreshing(false);
                list = response.body();
                if (list.isEmpty()){
                    Toast.makeText(getActivity(), "Berita Belum ada", Toast.LENGTH_SHORT).show();
                }else {
                    mAdapter = new BeritaAdapter(list, getActivity());
                    rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<BeritaModel>> call, Throwable t) {
//                pd.dismiss();
                swipe.setRefreshing(false);
//                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getHeadline() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getHeadline().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            String gambar = jsonObject.optString("berita_gambar");
                            String judul = jsonObject.optString("berita_judul");
                            String id = jsonObject.optString("berita_id");
                            String isi = jsonObject.optString("berita_isi");
                            String tanggal = jsonObject.optString("created_at");
                            String nama = jsonObject.optString("user_nama");
                            String gambar_pembuat = jsonObject.getString("user_gambar");

                            listId.add(id);
                            listGambar.add(gambar);
                            listJudul.add(judul);
                            listIsi.add(isi);
                            listTanggal.add(tanggal);
                            listNama.add(nama);
                            listGambarPembuat.add(gambar_pembuat);

                            Slider();
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

            }
        });
    }
    private void Slider(){
        for (int i = 0; i <listJudul.size() ; i++) {
            url_maps = new HashMap<String, String>();
            url_maps.put(listJudul.get(i), Config.URL_IMAGE + listGambar.get(i));
            file_maps = new HashMap<String, Integer>();
            file_maps.put(listJudul.get(i), Integer.valueOf(R.drawable.can));
        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(name).image((String) url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private void initView(View view) {
        sliderSlider = (SliderLayout) view.findViewById(R.id.sliderSlider);
        customIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        customIndicator2 = (PagerIndicator) view.findViewById(R.id.custom_indicator2);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent intent = new Intent(getActivity(), DetailBeritaActivity.class);
        shared.saveSPString(shared.SP_JUDUL_BERITA, slider.getDescription());
        for (int i = 0; i <listJudul.size() ; i++) {
            gambar = Config.URL_IMAGE + listGambar.get(i);
            if (gambar.equals(slider.getUrl())){
                shared.saveSPString(shared.SP_GAMBAR_BERITA, listGambar.get(i));
                shared.saveSPString(shared.SP_BERITA_ID, listId.get(i));
                shared.saveSPString(shared.SP_ISI_BERITA, listIsi.get(i));
                shared.saveSPString(shared.SP_PEMBUAT_BERITA, listNama.get(i));
                shared.saveSPString(shared.SP_TANGGAL_BERITA, listTanggal.get(i));
                shared.saveSPString(shared.SP_FOTO_PEMBUAT_BERITA, listGambarPembuat.get(i));
            }

        }
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
