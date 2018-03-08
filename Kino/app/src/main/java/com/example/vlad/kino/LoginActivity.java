package com.example.vlad.kino;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import static android.content.ContentValues.TAG;

/**
 * Created by Vlad on 07.03.2018.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
   private EditText editTextEmail;
   private EditText editTextPassword;
   private Button mSignIn;
   private Button mSignUp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.login);
        editTextPassword = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

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
if ( view.getId() == R.id.signIn){
    signIn(email,password);

} else if ( view.getId() == R.id.signUp ){
registration(email,password);
}
    }

    public void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Авторизация прошла успешно", Toast.LENGTH_LONG).show();
                } else Toast.makeText(LoginActivity.this,"Авторизация провалена",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registration(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if ( task.isSuccessful()){
    Toast.makeText(LoginActivity.this,"Регистрация успешна",Toast.LENGTH_LONG).show();
}else Toast.makeText(LoginActivity.this,"Регистрация провалена",Toast.LENGTH_LONG).show();
            }
        });
    }

}
