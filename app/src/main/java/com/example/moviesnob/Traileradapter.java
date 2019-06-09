package com.example.moviesnob;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesnob.model.Movie;
import com.example.moviesnob.model.Trailer;

import java.util.List;

public class Traileradapter extends RecyclerView.Adapter<Traileradapter.MyViewHolder> {

    private Context mContext;
    private List<Trailer> ltrailer;
    RequestOptions options ;


    public Traileradapter (Context mContext, List<Trailer> ltrailer){

        this.mContext = mContext;
        this.ltrailer= ltrailer;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo);
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view= mInflater.inflate(R.layout.trailer_view,parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting trailer key and using that key to gat videos
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" +ltrailer.get(viewHolder.getAdapterPosition()).getKey()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                mContext.startActivity(intent);



            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.mTitle.setText(ltrailer.get(i).getName());
        Glide.with(mContext).load(ltrailer.get(i).getImage_url()).apply(options).into(holder.poster);

        // load image from the internet using Glide


    }

    @Override
    public int getItemCount() {

        return ltrailer.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        ImageView poster;
        LinearLayout view_container;

        public MyViewHolder(View itemView){
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            mTitle = (TextView) itemView.findViewById(R.id.mtitle);
            poster = (ImageView) itemView.findViewById(R.id.poster);




        }
    }


}
