package com.example.moviesnob;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesnob.model.Data;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.util.Date;

public class Comment extends AppCompatActivity {
    private Button savebtn;
    private EditText com;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Data, MyViewHolder> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        final String uId = mUser.getUid();
        final String user = mUser.getDisplayName();


        String moId  = getIntent().getStringExtra("movieid");
        String moname  = getIntent().getStringExtra("moviename");
        Toolbar tool= findViewById(R.id.tool);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(moname);




        mDatabase = FirebaseDatabase.getInstance().getReference().child("Comments").child(moId);
        mDatabase.keepSynced(true);


        progressDialog = new ProgressDialog(this);
        savebtn = findViewById(R.id.savebtn);
        com = findViewById(R.id.comment);


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moId = getIntent().getStringExtra("movieid");


                String comm = com.getText().toString().trim();

                if (TextUtils.isEmpty(comm)) {
                    com.setError("Required");
                    return;
                }


                progressDialog.setMessage("Processing...");

                String id = mDatabase.push().getKey();

                String date = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(comm, date, uId, moId,user);
                mDatabase.child(id).setValue(data);

                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }
        });

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        String moId = getIntent().getStringExtra("movieid");

        Query query = FirebaseDatabase.getInstance().getReference().child("Comments").child(moId);

        FirebaseRecyclerOptions<Data> option = new FirebaseRecyclerOptions.Builder<Data>().setQuery(query, new SnapshotParser<Data>() {
            @NonNull
            @Override
            public Data parseSnapshot(@NonNull DataSnapshot snapshot) {

                return new Data(

                        snapshot.child("comment").getValue(String.class),
                        snapshot.child("date").getValue(String.class),
                        snapshot.child("id").getValue(String.class),
                        snapshot.child("mid").getValue(String.class),
                        snapshot.child("user").getValue(String.class)

                );
            }
        }).build();

        adapter = new FirebaseRecyclerAdapter<Data, Comment.MyViewHolder>(option) {


            @Override
            public Comment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
                return new Comment.MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Comment.MyViewHolder holder, int position, @NonNull Data model) {
                holder.setData(model);

            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        if (adapter != null) {
            adapter.startListening();
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        android.view.View myView;
        TextView myTitle, myDate, usern;


        public MyViewHolder(android.view.View itemView) {
            super(itemView);
            myView = itemView;
            myTitle = itemView.findViewById(R.id.commm);
            myDate = itemView.findViewById(R.id.date);
            usern = itemView.findViewById(R.id.username);
        }

        void setData(Data data) {
            String date = data.getDate();
            myDate.setText(date);
            String title = data.getComment();
            myTitle.setText(title);
            String user = data.getUser();
            usern.setText(user);


        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null) {
            adapter.stopListening();
        }
    }
}

