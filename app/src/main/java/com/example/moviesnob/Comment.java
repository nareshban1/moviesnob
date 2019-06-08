package com.example.moviesnob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import com.example.moviesnob.model.Comments;

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
    FirebaseRecyclerAdapter<Comments, MyViewHolder> adapter;


    private String Commentp;
    private String post_key;
    private EditText CommentUp;
    private Button btnDelete;
    private Button btnUpdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);



        String moId  = getIntent().getStringExtra("movieid");
        String moname  = getIntent().getStringExtra("moviename");
        Toolbar tool= findViewById(R.id.tool);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(moname);




        mDatabase = FirebaseDatabase.getInstance().getReference().child("Comments").child(moId);
        mDatabase.keepSynced(true);
        savebtn = findViewById(R.id.savebtn);
        com = findViewById(R.id.comment);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();

        if(mUser!= null) {
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String moId = getIntent().getStringExtra("movieid");

                    String uId = mUser.getUid();
                    String user = mUser.getDisplayName();


                    String comm = com.getText().toString().trim();

                    if (TextUtils.isEmpty(comm)) {
                        com.setError("Required");
                        return;
                    }


                    String id = mDatabase.push().getKey();

                    String date = DateFormat.getDateInstance().format(new Date());
                    Comments data = new Comments(comm, date, uId, moId, user,id);
                    mDatabase.child(id).setValue(data);

                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else{
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Log in And Try again", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
            });
        }


        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser mUser = mAuth.getCurrentUser();

        String moId = getIntent().getStringExtra("movieid");

        Query query = FirebaseDatabase.getInstance().getReference().child("Comments").child(moId);

        FirebaseRecyclerOptions<Comments> option = new FirebaseRecyclerOptions.Builder<Comments>().setQuery(query, new SnapshotParser<Comments>() {
            @NonNull
            @Override
            public Comments parseSnapshot(@NonNull DataSnapshot snapshot) {

                return new Comments(

                        snapshot.child("comment").getValue(String.class),
                        snapshot.child("date").getValue(String.class),
                        snapshot.child("id").getValue(String.class),
                        snapshot.child("mid").getValue(String.class),
                        snapshot.child("user").getValue(String.class),
                        snapshot.child("postid").getValue(String.class)


                );
            }
        }).build();

        adapter = new FirebaseRecyclerAdapter<Comments, Comment.MyViewHolder>(option) {


            @Override
            public Comment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
                return new Comment.MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Comment.MyViewHolder holder, final int position, @NonNull final Comments model) {
                holder.setData(model);

                if (mUser!= null) {

                        holder.myView.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick (View view){
                                    if(mUser.getUid()== model.getId())
                                        post_key = getRef(position).getKey();
                                        Commentp = model.getComment();
                                        updateData();
                                    {

                                }
                            }
                        });

                }



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

        void setData(Comments data) {
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

    public void updateData(){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Comment.this);
        LayoutInflater inflater = LayoutInflater.from(Comment.this);
        View myview = inflater.inflate(R.layout.updatecom,null);
        mydialog.setView(myview);

        final AlertDialog dialog = mydialog.create();
        CommentUp = myview.findViewById(R.id.udcomment);

        CommentUp.setText(Commentp);
        CommentUp.setSelection(Commentp.length());



        btnDelete = myview.findViewById(R.id.delete);
        btnUpdate = myview.findViewById(R.id.update);
        final String mId = getIntent().getStringExtra("movieid");

        final FirebaseUser mUser = mAuth.getCurrentUser();
        final String uId = mUser.getUid();
        final String user = mUser.getDisplayName();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Commentp = CommentUp.getText().toString().trim();
                String mDate = DateFormat.getDateInstance().format(new Date());
                Comments data = new Comments(Commentp,mDate,uId,mId, user,post_key);

                mDatabase.child(post_key).setValue(data);
                dialog.dismiss();
            }
        });

        // for deleting the data from firebase
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(post_key).removeValue();

                dialog.dismiss();
            }
        });


        dialog.show();


    }
}

