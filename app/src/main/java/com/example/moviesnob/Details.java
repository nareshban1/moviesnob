package com.example.moviesnob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Details extends AppCompatActivity {
    Button comment;
    private FirebaseAuth mAuth;

    int id;
    private String ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        final String name  = getIntent().getExtras().getString("movie_name");
        String description = getIntent().getExtras().getString("description");
        String rating = getIntent().getExtras().getString("rating") ;
        String image_url = getIntent().getExtras().getString("imgb") ;
        String image = getIntent().getExtras().getString("img") ;
        String rel = getIntent().getExtras().getString("release") ;
        id = getIntent().getExtras().getInt("id") ;







        TextView mname = findViewById(R.id.movie_name);
        TextView mdescription = findViewById(R.id.description);
        TextView mrating  = findViewById(R.id.rating) ;
        ImageView img = findViewById(R.id.movieimage);
        ImageView imgs = findViewById(R.id.movieimg);
        TextView rele = findViewById(R.id.releasedate);
        Toolbar tool= findViewById(R.id.tool);
        setSupportActionBar(tool);
        ids= Integer.toString(id);
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


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();

        final Intent intent = new Intent(this, Comment.class);
        comment = (Button) findViewById(R.id.Comm);




        if(mUser != null) {
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent.putExtra("movieid", ids);
                    intent.putExtra("moviename", name);

                    startActivity(intent);
                }
            });
        }
        else{

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Log In and Try Again",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });
        }




    }


}
