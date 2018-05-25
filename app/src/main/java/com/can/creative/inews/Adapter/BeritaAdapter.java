package com.can.creative.inews.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Activity.AdminActivity;
import com.can.creative.inews.Activity.Berita.DetailVideoActivity;
import com.can.creative.inews.Activity.DetailBeritaActivity;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Helper.Shared;
import com.can.creative.inews.Model.BeritaModel;
import com.can.creative.inews.Model.LikeModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cis on 09/05/2018.
 */

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaHolder> {

    private List<BeritaModel> beritaModels;
    private List<LikeModel> likeModels;
    private Context context;
    Shared shared;

    public BeritaAdapter(List<BeritaModel> beritaModels, Context context) {
        this.beritaModels = beritaModels;
        this.context = context;
    }

    @Override
    public BeritaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news,parent,false);
        BeritaHolder holder = new BeritaHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(BeritaHolder holder, int position) {
        BeritaModel bm = beritaModels.get(position);
        holder.txtJudul.setText(bm.getBeritaJudul());
        holder.txtPengirim.setText(bm.getKategoriName());
        holder.txtLike.setText(bm.getLike());
        holder.txtDislike.setText(bm.getDislike());
        holder.txtKomen.setText(bm.getComment());
        Picasso.with(context)
                .load(Config.URL_IMAGE + bm.getBeritaGambar())
                .error(R.drawable.no_image_found)
                .into(holder.imgBerita);
        holder.bm = bm;
    }

    @Override
    public int getItemCount() {
        return beritaModels.size();
    }

    public class BeritaHolder extends RecyclerView.ViewHolder{

        TextView txtJudul,txtPengirim,txtLike,txtDislike,txtKomen;
        ImageView imgBerita;
        BeritaModel bm;
        public BeritaHolder(View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtPengirim = itemView.findViewById(R.id.txt_pengirim);
            imgBerita = itemView.findViewById(R.id.img_berita);
            txtLike = itemView.findViewById(R.id.txt_like);
            txtDislike = itemView.findViewById(R.id.txt_dislike);
            txtKomen = itemView.findViewById(R.id.txt_komen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shared = new Shared(context);
                    if (shared.getSpRule().equals("superadmin")) {
                        final CharSequence[] dialogItem = {"Lihat", "Hapus", "Edit"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Tentukan Pilihan Anda");
                        builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                switch (i) {
                                    case 0:
                                        Intent intent = new Intent(context, DetailBeritaActivity.class);
                                        shared.saveSPString(shared.SP_JUDUL_BERITA, bm.getBeritaJudul());
                                        shared.saveSPString(shared.SP_ISI_BERITA, bm.getBeritaIsi());
                                        shared.saveSPString(shared.SP_GAMBAR_BERITA, bm.getBeritaGambar());
                                        shared.saveSPString(shared.SP_TANGGAL_BERITA, bm.getCreatedAt());
                                        shared.saveSPString(shared.SP_PEMBUAT_BERITA, bm.getUserNama());
                                        shared.saveSPString(shared.SP_FOTO_PEMBUAT_BERITA, bm.getUserGambar());
                                        shared.saveSPString(shared.SP_BERITA_ID, bm.getBeritaId());
                                        context.startActivity(intent);
                                        break;
                                    case 1:
                                        new android.app.AlertDialog.Builder(context)
                                                .setMessage("Apakah Anda ingin menghapusnya?")
                                                .setCancelable(false)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        final ProgressDialog pd = new ProgressDialog(context);
                                                        pd.setTitle("Mohon Tunggu");
                                                        pd.setCancelable(false);
                                                        pd.show();
                                                        ApiService apiService = Client.getInstanceRetrofit();
                                                        apiService.hapusBerita(bm.getBeritaId()).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if (response.isSuccessful()){
                                                                    pd.dismiss();
                                                                    try {
                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                        String pesan = jsonObject.optString("pesan");
                                                                        Toast.makeText(context, ""+pesan, Toast.LENGTH_SHORT).show();
                                                                        Intent intent1 = new Intent(context,AdminActivity.class);
                                                                        context.startActivity(intent1);
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
                                                                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                })
                                                .setNegativeButton("No", null)
                                                .show();
                                        break;
                                    case 2:

                                        break;
                                }
                            }
                        });
                        builder.create().show();
                    }else {
                        Intent intent = new Intent(context, DetailBeritaActivity.class);
                        shared.saveSPString(shared.SP_JUDUL_BERITA, bm.getBeritaJudul());
                        shared.saveSPString(shared.SP_ISI_BERITA, bm.getBeritaIsi());
                        shared.saveSPString(shared.SP_GAMBAR_BERITA, bm.getBeritaGambar());
                        shared.saveSPString(shared.SP_TANGGAL_BERITA, bm.getCreatedAt());
                        shared.saveSPString(shared.SP_PEMBUAT_BERITA, bm.getUserNama());
                        shared.saveSPString(shared.SP_FOTO_PEMBUAT_BERITA, bm.getUserGambar());
                        shared.saveSPString(shared.SP_BERITA_ID, bm.getBeritaId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
