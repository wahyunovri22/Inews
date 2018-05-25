package com.can.creative.inews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Model.UserModel;
import com.can.creative.inews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cis on 18/05/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<UserModel> userModels;
    Context context;

    public UserAdapter(List<UserModel> userModels, Context context) {
        this.userModels = userModels;
        this.context = context;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        UserHolder holder = new UserHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
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

    public class UserHolder extends RecyclerView.ViewHolder{

        TextView txtNama,txtDaerah,txtRule;
        CircleImageView imgUser;
        UserModel um;
        public UserHolder(View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txt_nama);
            txtDaerah = itemView.findViewById(R.id.txt_daerah);
            txtRule = itemView.findViewById(R.id.txt_rule);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }
}
