package com.example.moviesnob.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesnob.Adapter;
import com.example.moviesnob.R;
import com.example.moviesnob.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Nowplaying extends Fragment {


    View v;
    List<Movie> lmovie = new ArrayList<>();
    private RequestQueue requestQueue;
    RecyclerView mrv;

    public Nowplaying() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.content_main, container, false);
        getActivity().setTitle("Now Playing");

        mrv = (RecyclerView) v.findViewById(R.id.recyclerViewpop);
        jsoncall();


        return v;
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());





    }

    // maile ni ramrari bhujheko ta chaina tara yo method le json ko url bata data extract garcha ani movie class ma pathaucha.

    public void jsoncall( ) {


        for(int a =1; a<=20;a++) {
            String URL_JSON = "https://api.themoviedb.org/3/movie/now_playing?api_key=c95a6dccccd66a359cf6e9a0a7d8c665&language=en-US&page="+a;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_JSON, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Movie movie = new Movie();
                            movie.setId(jsonObject.getInt("id"));
                            movie.setTitle(jsonObject.getString("title"));
                            movie.setImage_url(jsonObject.getString("poster_path"));
                            movie.setRating(jsonObject.getString("vote_average"));
                            movie.setDescription(jsonObject.getString("overview"));
                            movie.setBack_url(jsonObject.getString("backdrop_path"));
                            movie.setRelease(jsonObject.getString("release_date"));
                            lmovie.add(movie);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    setAdapter(lmovie);

                    //Toast.makeText(getContext() ,"Movies Loaded "+String.valueOf(lmovie.size()),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this,lmovie.get(1).toString(),Toast.LENGTH_SHORT).show();

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }


            });
            requestQueue.add(request);

        }



    }


    public void setAdapter (List<Movie> lmovie) {

        Adapter myAdapter = new Adapter(getContext(), lmovie);
        mrv.setLayoutManager( new GridLayoutManager(getActivity(), 3));
        mrv.setAdapter(myAdapter);

    }

}