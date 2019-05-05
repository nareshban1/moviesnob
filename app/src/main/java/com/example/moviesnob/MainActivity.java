package com.example.moviesnob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesnob.model.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    TextView Uemail;
    TextView title;
    private static long back_pressed;
    String email;
    List<Movie> lmovie=new ArrayList<>();
    private String URL_JSON = "https://gist.githubusercontent.com/nareshban1/5921aa304771b596b9d238a7273421a8/raw/908a52b010de3a182fbfa6172956bf577c396e50/movies.json";
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue;
    RecyclerView mrv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();







        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Uemail =  headerView.findViewById(R.id.Temail);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        TextView login = headerView.findViewById(R.id.nav_log);




        if(mUser != null) {
            email = mUser.getEmail();
            login.setText("Log Out");
            login = headerView.findViewById(R.id.nav_log);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mAuth.signOut();
                    Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();

                }
            });

        }
        else{
            email = "User not logged in";
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });
        }
        Uemail.setText(email);

        /*
        //listing movie in content main





        lmovie.add(new Movie("Avengers",R.drawable.avenger));
        lmovie.add(new Movie("Avengers",R.drawable.widow));
        lmovie.add(new Movie("Avengers",R.drawable.cap));
        lmovie.add(new Movie("Avengers",R.drawable.thor));
        lmovie.add(new Movie("Avengers",R.drawable.iron));
        lmovie.add(new Movie("Avengers",R.drawable.scar));
        lmovie.add(new Movie("Avengers",R.drawable.avenger));*/




        jsoncall();
        mrv= findViewById(R.id.recyclerView);

//








    }

    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();

                    try {

                        jsonObject = response.getJSONObject(i);
                        Movie movie = new Movie();

                        movie.setTitle(jsonObject.getString("name"));
                        movie.setImage_url(jsonObject.getString("img"));

                        //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                        lmovie.add(movie);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Toast.makeText(MainActivity.this,"Size of Liste "+String.valueOf(lmovie.size()),Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,lmovie.get(1).toString(),Toast.LENGTH_SHORT).show();

                setAdapter(lmovie);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(ArrayRequest);
    }


    public void setAdapter (List<Movie> lmovie) {

        Adapter myAdapter = new Adapter(this, lmovie);
        mrv.setLayoutManager( new GridLayoutManager(this, 3));
        mrv.setAdapter(myAdapter);




    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
            else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();

        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_discover) {
            //startActivity(new Intent(getApplicationContext(),Discover.class));

        } else if (id == R.id.nav_fav) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
