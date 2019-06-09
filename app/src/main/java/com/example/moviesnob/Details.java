package com.example.moviesnob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesnob.model.Trailer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Details extends AppCompatActivity {
    private FirebaseAuth mAuth;
    int id;
    private String ids;
    List<Trailer> ltrailer = new ArrayList<>();
    private RequestQueue requestQueue;
    RecyclerView mrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        // value from adapter
        final String name  = getIntent().getExtras().getString("movie_name");
        String description = getIntent().getExtras().getString("description");
        String rating = getIntent().getExtras().getString("rating") ;
        String image_url = getIntent().getExtras().getString("imgb") ;
        String image = getIntent().getExtras().getString("img") ;
        String rel = getIntent().getExtras().getString("release") ;
        id = getIntent().getExtras().getInt("id") ;

        mrv = (RecyclerView) findViewById(R.id.mrvv);





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
        Button come= findViewById(R.id.Comm);


        requestQueue = Volley.newRequestQueue(this);
        jsoncall();

        // sending movie id and name to comments page
        come.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("movieid", ids);
                intent.putExtra("moviename",name);
                startActivity(intent);
            }
        });
    }

    //getting trailers of a particulat movie using movie id
     public void jsoncall( ) {

            String URL_JSON = "https://api.themoviedb.org/3/movie/"+ids+"/videos?api_key=c95a6dccccd66a359cf6e9a0a7d8c665&language=en-US";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_JSON, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject json = jsonArray.getJSONObject(i);
                            Trailer trailer = new Trailer();
                            trailer.setKey(json.getString("key"));
                            trailer.setName(json.getString("name"));

                            ltrailer.add(trailer);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    setAdapter(ltrailer);



                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }


            });
            requestQueue.add(request);

        }





    //recycler view adapter
    public void setAdapter (List<Trailer> ltrailer) {

        Traileradapter myAdapter = new Traileradapter(this, ltrailer);
        mrv.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mrv.setAdapter(myAdapter);

    }


}
