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
import android.widget.TextView;
import android.widget.Toast;

import com.can.creative.inews.Activity.AdminActivity;
import com.can.creative.inews.Activity.Berita.DetailBeritaDuaActivity;
import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Model.UserModel;
import com.can.creative.inews.R;
import com.can.creative.inews.Retrofit.ApiService;
import com.can.creative.inews.Retrofit.Client;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cis on 23/05/2018.
 */

public class UserApproveAdapter extends RecyclerView.Adapter<UserApproveAdapter.UserApproveHolder> {

    List<UserModel> userModels;
    Context context;

    public UserApproveAdapter(List<UserModel> userModels, Context context) {
        this.userModels = userModels;
        this.context = context;
    }

    @Override
    public UserApproveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,parent,false);
        UserApproveHolder holder = new UserApproveHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserApproveHolder holder, int position) {
        UserModel um = userModels.get(position);
        holder.txtNama.setText(um.getUserNama());
        holder.txtDaerah.setText(um.getKategoriName());
        holder.txtRule.setText(um.getUserRole());
        Picasso.with(context)
                .load(Config.URL_IMAGE + um.getUserGambar())
                .error(R.drawable.no_image_found)
                .into(holder.imgUser);
        holder.um = um;
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class UserApproveHolder extends RecyclerView.ViewHolder{

        TextView txtNama,txtDaerah,txtRule;
        CircleImageView imgUser;
        UserModel um;
        public UserApproveHolder(View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txt_nama);
            txtDaerah = itemView.findViewById(R.id.txt_daerah);
            txtRule = itemView.findViewById(R.id.txt_rule);
            imgUser = itemView.findViewById(R.id.img_user);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] dialogItem = {"Setujui"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Tentukan Pilihan Anda");
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            switch (i) {
                                case 0:
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
                                                    apiService.accUser(um.getUserId()).enqueue(new Callback<ResponseBody>() {
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
                            }
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }
}
