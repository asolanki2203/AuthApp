package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    EditText mName, mEmail, mPassword;
    FirebaseAuth fAuth;
    Button mRegister;
    TextView Logingo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mName= (EditText) findViewById(R.id.Name);
        mEmail=(EditText) findViewById(R.id.Email);
        mPassword=(EditText) findViewById(R.id.Password);
        mRegister=(Button) findViewById(R.id.register_button);
        Logingo=(TextView) findViewById(R.id.already_registered);

        fAuth= FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=mName.getText().toString();
                        String email= mEmail.getText().toString();
                        String password= mPassword.getText().toString();

                        if(TextUtils.isEmpty(name)){
                            mName.setError("Name is required");
                            return;
                        }
                        if(TextUtils.isEmpty(email)){
                            mEmail.setError("Email is required");
                            return;
                        }
                        if(TextUtils.isEmpty(password)){
                            mPassword.setError("Password is required");
                            return;
                        }
                        if(password.length()<6){
                            mPassword.setError("Password must be atleast 6 characters long");
                        }

                        //register the user
                        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "User Created!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                else{
                                    Toast.makeText(SignUp.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }

                }
        );
        Logingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}