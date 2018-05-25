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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Activity.AdminActivity;
import com.can.creative.inews.Activity.Berita.DetailBeritaDuaActivity;
import com.can.creative.inews.Activity.ProfilActivity;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Model.BeritaModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cis on 18/05/2018.
 */

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ApprovalHolder>{

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    private List<BeritaModel> beritaModels;
    View dialogView;
    String Cek ="0";
    Context context;

    public ApprovalAdapter(List<BeritaModel> beritaModels, Context context) {
        this.beritaModels = beritaModels;
        this.context = context;
    }

    @Override
    public ApprovalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_approval,parent,false);
        ApprovalHolder holder = new ApprovalHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(ApprovalHolder holder, int position) {
        BeritaModel bm = beritaModels.get(position);
        holder.txtJudul.setText(bm.getBeritaJudul());
        holder.txtPengirim.setText(bm.getKategoriName());
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

    public class ApprovalHolder extends RecyclerView.ViewHolder{

        TextView txtJudul,txtPengirim,txtLike;
        ImageView imgBerita;
        BeritaModel bm;
        public ApprovalHolder(View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtPengirim = itemView.findViewById(R.id.txt_pengirim);
            imgBerita = itemView.findViewById(R.id.img_berita);
            txtLike = itemView.findViewById(R.id.txt_like);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] dialogItem = {"Lihat", "Setujui"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Tentukan Pilihan Anda");
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            switch (i) {
                                case 0:
                                    Intent intent = new Intent(context, DetailBeritaDuaActivity.class);
                                    intent.putExtra("judul", bm.getBeritaJudul());
                                    intent.putExtra("image_pembuat", bm.getUserGambar());
                                    intent.putExtra("pembuat", bm.getUserNama());
                                    intent.putExtra("tanggal", bm.getCreatedAt());
                                    intent.putExtra("image", bm.getBeritaGambar());
                                    intent.putExtra("isi", bm.getBeritaIsi());
                                    context.startActivity(intent);
                                    break;
                                case 1:
                                    dialog = new AlertDialog.Builder(context);
                                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    dialogView = inflater.inflate(R.layout.layout_acc, null);
                                    dialog.setView(dialogView);
                                    dialog.setCancelable(true);
                                    dialog.setIcon(R.mipmap.ic_launcher);
                                    dialog.setTitle("Silahkan Approve");

                                    final CheckBox cbHeadline = (CheckBox) dialogView.findViewById(R.id.cb_headline);

                                    cbHeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            if (cbHeadline.isChecked()){
                                                Cek = "1";
                                            }else if (!cbHeadline.isChecked()){
                                                Cek = "0";
                                            }
                                        }
                                    });

                                    dialog.setPositiveButton("Approve", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final ProgressDialog pd = new ProgressDialog(context);
                                            pd.setTitle("Menunggu ...");
                                            pd.setCancelable(false);
                                            pd.show();
                                            ApiService apiService = Client.getInstanceRetrofit();
                                            apiService.approved(bm.getBeritaId(),Cek).enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()){
                                                        pd.dismiss();
                                                        Toast.makeText(context, ""+Cek, Toast.LENGTH_SHORT).show();
                                                        cbHeadline.setChecked(false);
                                                        Intent intent1 = new Intent(context, AdminActivity.class);
                                                        context.startActivity(intent1);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    pd.dismiss();
                                                    Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }
}
