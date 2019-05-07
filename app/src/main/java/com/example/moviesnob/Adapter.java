package com.example.moviesnob;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> lmovie;
    RequestOptions options ;


    public Adapter (Context mContext, List<Movie> lmovie){

        this.mContext = mContext;
        this.lmovie= lmovie;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo);
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view= mInflater.inflate(R.layout.card_view,parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, Details.class);
                // sending data process
                i.putExtra("movie_name",lmovie.get(viewHolder.getAdapterPosition()).getTitle());
                i.putExtra("description",lmovie.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("rating",lmovie.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("img",lmovie.get(viewHolder.getAdapterPosition()).getImage_url());
                i.putExtra("imgb",lmovie.get(viewHolder.getAdapterPosition()).getBack_url());

                mContext.startActivity(i);

            }
        });




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.mTitle.setText(lmovie.get(i).getTitle());

        // load image from the internet using Glide
        Glide.with(mContext).load(lmovie.get(i).getImage_url()).apply(options).into(holder.poster);

    }

    @Override
    public int getItemCount() {

        return lmovie.size();
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
