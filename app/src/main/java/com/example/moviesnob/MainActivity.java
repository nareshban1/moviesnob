package com.example.moviesnob;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.moviesnob.Fragments.HomeFragment;
import com.example.moviesnob.Fragments.Nowplaying;
import com.example.moviesnob.Fragments.Popular;
import com.example.moviesnob.Fragments.Upcoming;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    TextView Uemail;
    private static long back_pressed;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();



        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_main);
        }


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

       // mrv= findViewById(R.id.recyclerView);

        //jsoncall();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_main);
        }






    }




    //method for press twice to exit
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






    //menu bar

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();

        if(id == R.id.nav_main){
            fragment = new HomeFragment();
        }

        //reloads the app
        else if(id == R.id.nav_Popular){
            fragment = new Popular();

        }

        else if(id == R.id.nav_Now){
            fragment = new Nowplaying();

        }
        else if(id == R.id.nav_Upcoming){
            fragment = new Upcoming();

        }


        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main, fragment);
            ft.commit();

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
