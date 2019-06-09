package com.example.moviesnob;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    private EditText email;
    private EditText uname, fname, lname;
    private EditText pass;
    private Button register;
    private TextView rlogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private static final String CHANNEL_ID = "Movie_Snob";
    private static final String CHANNEL_NAME = "Movie_Snob";
    private static final String CHANNEL_DESC = "Movie Details";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.reg_email);
        pass = findViewById(R.id.reg_pass);
        uname = findViewById(R.id.reg_user);
        register = findViewById(R.id.reg_btn);
        rlogin = findViewById(R.id.log_reg);


        mAuth = FirebaseAuth.getInstance();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        rlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mPassword = pass.getText().toString().trim();
                String mUsername = uname.getText().toString().trim();


                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(mPassword)) {
                    pass.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(mUsername)) {
                    uname.setError("Required");
                    return;
                }
                progressDialog.setMessage("Registering new user...");
                progressDialog.show();
                callsignup(mEmail, mPassword, mUsername);
            }


        });
    }
    //registration successful notification
    private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Registration Successful!")
                .setContentText("You have successfully Registered! Welcome to the MovieSnob Family!!")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("You have successfully Registered! Welcome to the MovieSnob Family!!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notifym= NotificationManagerCompat.from(this);
        notifym.notify(1,mBuilder.build());
    }

    private void callsignup(String email, String password, final String username) {

        //registering user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userProfile(username);
                    Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    progressDialog.dismiss();
                    displayNotification();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration Unsuccessful. Try Again", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }


    //Set UserDisplay Name
    private void userProfile(String username)
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("", "User profile updated.");
                            }
                        }
                    });
        }
    }



}

