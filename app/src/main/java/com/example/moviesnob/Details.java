package com.example.moviesnob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        String name  = getIntent().getExtras().getString("movie_name");
        String description = getIntent().getExtras().getString("description");
        String rating = getIntent().getExtras().getString("rating") ;
        String image_url = getIntent().getExtras().getString("imgb") ;





        TextView mname = findViewById(R.id.movie_name);
        TextView mdescription = findViewById(R.id.description);
        TextView mrating  = findViewById(R.id.rating) ;
        ImageView img = findViewById(R.id.movieimage);
        Toolbar tool= findViewById(R.id.bar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(name);

        // setting values to each view
        //tool.setTitle(name);
        mname.setText(name);
        mdescription.setText(description);
        mrating.setText(rating);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.logo).error(R.drawable.logo);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);

    }
}
