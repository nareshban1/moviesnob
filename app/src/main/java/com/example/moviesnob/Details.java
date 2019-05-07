package com.example.moviesnob;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        String name  = getIntent().getExtras().getString("movie_name");
        String description = getIntent().getExtras().getString("description");
        String rating = getIntent().getExtras().getString("rating") ;
        String image_url = getIntent().getExtras().getString("imgb") ;
        String image = getIntent().getExtras().getString("img") ;
        String rel = getIntent().getExtras().getString("release") ;





        TextView mname = findViewById(R.id.movie_name);
        TextView mdescription = findViewById(R.id.description);
        TextView mrating  = findViewById(R.id.rating) ;
        ImageView img = findViewById(R.id.movieimage);
        ImageView imgs = findViewById(R.id.movieimg);
        TextView rele = findViewById(R.id.releasedate);
        Toolbar tool= findViewById(R.id.tool);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(name);


        // setting values to each view
        //tool.setTitle(name);
        mname.setText(name);
        mdescription.setText(description);
        mrating.setText(rating);
        rele.setText(rel);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.logo).error(R.drawable.logo);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);
        Glide.with(this).load(image).apply(requestOptions).into(imgs);






    }
}
