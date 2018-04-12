package com.example.vlad.a_fishka.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.vlad.a_fishka.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private  EditText editTextEmail;
    private   EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextSurname;

    ProgressBar progressBar;
    Button buttonRegistration;
    FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);

        editTextName = findViewById(R.id.ET_name);
        editTextSurname = findViewById(R.id.ET_surname);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);

        buttonRegistration = findViewById(R.id.btn_register);
        buttonRegistration.setOnClickListener(this);


    }

    public void insertDatabase(String name,String surname){
        int admLvl = 0;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference myUser = database.getReference(uid);

        myUser.child("Admin LVL").setValue(admLvl);
        myUser.child("Имя").setValue(name);
        myUser.child("Фамилия").setValue(surname);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

    }

    public void signUp(String email,String password,final String name ,final String surname){
        progressBar.setVisibility(ProgressBar.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    insertDatabase(name,surname);
                    Intent intent = new Intent ( RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this,"Регистрация успешна",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(RegisterActivity.this,"Регистрация провалена",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
        });
    }


    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if ( !isOnline(this) ) {
            Toast.makeText(getApplicationContext(),
                    "Нет соединения с интернетом!", Toast.LENGTH_LONG).show();
            return;

        }

        if (name.isEmpty()){
            editTextName.setError("Введите Имя !");
        } else
        if(surname.isEmpty()){
            editTextSurname.setError("Введите Фамилию !");
        }else
        if(email.isEmpty()){
            editTextEmail.setError("Введите E-mail !");
        }else
        if(password.isEmpty()){
            editTextPassword.setError("Введите пароль");
        }else signUp(email,password,name,surname);
    }
}