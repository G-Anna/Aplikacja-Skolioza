package com.example.myproject.EnteringApp;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Functions.NotesFragment;
import com.example.myproject.Functions.ProfileFragment;
import com.example.myproject.HomeActivity;
import com.example.myproject.R;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;
    String user;
    String pwd;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = new DatabaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mTextViewRegister = (TextView)findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }});





        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = mTextUsername.getText().toString().trim();
                pwd = mTextPassword.getText().toString().trim();


                Boolean res = db.checkUser(user, pwd);
                if (res == true) {
                    Log.e("tag", "Everything is fine. Let's go to the main page");
                    Intent HomePage = new Intent(LoginActivity.this, HomeActivity.class);
                    HomePage.putExtra("username", user);
                    startActivity(HomePage);

                } else {
                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
                /*intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("name", user);
                LoginActivity.this.startActivity(intent);*/
               /* Intent intent2 = new Intent(LoginActivity.this, NotesFragment.class);
                intent2.putExtra("name", user);
                LoginActivity.this.startActivity(intent2);*/
            }






        });


    }

}
