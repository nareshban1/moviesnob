package com.example.moviesnob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesnob.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText email;
    private EditText uname,fname,lname;
    private EditText pass;
    private Button register;
    private TextView rlogin;
    private FirebaseAuth Firebasea;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.reg_email);
        pass = findViewById(R.id.reg_pass);
        uname = findViewById(R.id.reg_user);
        fname = findViewById(R.id.reg_fname);
        lname = findViewById(R.id.reg_lname);
        register = findViewById(R.id.reg_btn);
        rlogin = findViewById(R.id.log_reg);


        Firebasea = FirebaseAuth.getInstance();

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

                final String mEmail=email.getText().toString().trim();
                String mPassword= pass.getText().toString().trim();
                final String mUsername= uname.getText().toString().trim();
                final String mFname= fname.getText().toString().trim();
                final String mLname= lname.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(mPassword)){
                    pass.setError("Required");
                }
                if(TextUtils.isEmpty(mUsername)){
                    uname.setError("Required");
                }
                progressDialog.setMessage("Processing...");
                progressDialog.show();

                Firebasea.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            User user= new User(mUsername, mEmail, mFname,mLname);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Login.class));
                                        progressDialog.dismiss();
                                    }

                                }
                            });

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
}
