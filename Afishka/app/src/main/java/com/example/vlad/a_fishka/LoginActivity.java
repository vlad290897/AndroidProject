package com.example.vlad.a_fishka;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Vlad on 07.03.2018.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    ProgressDialog pd;
    Button mSignIn;
    Button mSignUp;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.login);
        editTextPassword = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);

        mSignIn = findViewById(R.id.signIn);
        mSignIn.setOnClickListener(this);

        mSignUp = findViewById(R.id.signUp);
        mSignUp.setOnClickListener(this);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if( user != null){

                }else {

                }
            }
        };


    }


    @Override
    public void onClick(View view) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        switch(view.getId()){
            case R.id.signIn:
                if(email.isEmpty())
                    editTextEmail.setError("Вы не ввели E-mail !");
                if(password.isEmpty()&& !email.isEmpty())
                    editTextPassword.setError("Вы не ввели пароль !");
                if(!email.isEmpty() && !password.isEmpty())
                    signIn(email,password);
                break;


            case R.id.signUp:
               Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }

    public void signIn(String email,String password){
        progressBar.setVisibility(ProgressBar.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Авторизация прошла успешно", Toast.LENGTH_LONG).show();
                } else {Toast toast = Toast.makeText(LoginActivity.this,"Неверное имя пользователя или пароль!",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show(); progressBar.setVisibility(ProgressBar.INVISIBLE); }
            }
        });
    }


}

