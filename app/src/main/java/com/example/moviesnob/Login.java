package com.example.moviesnob;

import android.app.Notification;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button login;
    private TextView lregister;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private static final String CHANNEL_ID = "Movie_Snob";
    private static final String CHANNEL_NAME = "Movie_Snob";
    private static final String CHANNEL_DESC = "Movie Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        email = findViewById(R.id.log_email);
        pass = findViewById(R.id.log_pass);
        login = findViewById(R.id.log_btn);
        lregister = findViewById(R.id.log_reg);
        mAuth = FirebaseAuth.getInstance();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        lregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail=email.getText().toString().trim();
                String mPassword= pass.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(mPassword)){
                    pass.setError("Required");
                }

                progressDialog.setMessage("Processing...");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                            displayNotification();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Login Successful!")
                .setContentText("You have successfully logged in!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notifym= NotificationManagerCompat.from(this);
        notifym.notify(1,mBuilder.build());
    }
}
