package com.can.creative.inews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.can.creative.inews.Helper.Config;
import com.can.creative.inews.Model.KomenVideo;
import com.can.creative.inews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cis on 21/05/2018.
 */

public class KomenVideoAdapter extends RecyclerView.Adapter<KomenVideoAdapter.KomenVideHolder> {

    private List<KomenVideo> komenVideos;
    Context context;

    public KomenVideoAdapter(List<KomenVideo> komenVideos, Context context) {
        this.komenVideos = komenVideos;
        this.context = context;
    }

    @Override
    public KomenVideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coment, parent, false);
        KomenVideHolder holder = new KomenVideHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(KomenVideHolder holder, int position) {
        KomenVideo km = komenVideos.get(position);
        holder.txtNama.setText(km.getUserNama());
        holder.txtIsi.setText(km.getComvidIsi());
        Picasso.with(context).load(Config.URL_IMAGE + km.getUserGambar())
                .error(R.drawable.no_image_found).into(holder.imageView);
        holder.km = km;
    }

    @Override
    public int getItemCount() {
        return komenVideos.size();
    }

    public class KomenVideHolder extends RecyclerView.ViewHolder{

        TextView txtNama,txtIsi;
        CircleImageView imageView;
        KomenVideo km;
        public KomenVideHolder(View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txt_nama);
            txtIsi = itemView.findViewById(R.id.txt_komen);
            imageView = itemView.findViewById(R.id.imgPengomen);

        }
    }
}
