package com.can.creative.inews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Model.KomenModel;
import com.can.creative.inews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cis on 14/05/2018.
 */

public class KomenAdapter extends RecyclerView.Adapter<KomenAdapter.KomenHolder> {

    private List<KomenModel> komenModels;
    private Context context;

    public KomenAdapter(List<KomenModel> komenModels, Context context) {
        this.komenModels = komenModels;
        this.context = context;
    }

    @Override
    public KomenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coment,parent,false);
        KomenHolder holder = new KomenHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(KomenHolder holder, int position) {
        KomenModel model = komenModels.get(position);
        holder.txtNama.setText(model.getUserNama());
        holder.txtKomen.setText(model.getCommentIsi());
        Picasso.with(context).load(Config.URL_IMAGE + model.getUserGambar())
                .error(R.drawable.no_image_found)
                .into(holder.imgPengomen);
        holder.km = model;
    }

    @Override
    public int getItemCount() {
        return komenModels.size();
    }

    public class KomenHolder extends RecyclerView.ViewHolder{

        TextView txtNama,txtKomen;
        ImageView imgPengomen;
        KomenModel km;
        public KomenHolder(View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txt_nama);
            txtKomen = itemView.findViewById(R.id.txt_komen);
            imgPengomen = itemView.findViewById(R.id.imgPengomen);
        }
    }
}
