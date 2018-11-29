package com.ssd.boris.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ssd.boris.shoppingcart.DBHandler.DBHandler;


public class LoginActivity extends AppCompatActivity {

    private EditText text_email;
    private EditText text_password;
    private DBHandler dbHandler;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "a";
    public static final String Email = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        text_email = (EditText)findViewById(R.id.signin_email);
        text_password = (EditText)findViewById(R.id.signin_password);
    }

    public void loginUser(View view){
        //Log.d("Loginstat","User Logged in..");
        //Toast.makeText(this, "Logged in...", Toast.LENGTH_SHORT).show();

        if (text_email.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Email Cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (text_password.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please Set a password for registration", Toast.LENGTH_SHORT).show();
            return;
        }

        postData();
    }

    private void postData() {
        dbHandler = new DBHandler(this);
        if(dbHandler.checkUser(text_email.getText().toString().trim(), text_password.getText().toString())){
            sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Email,text_email.getText().toString().trim());
            editor.apply();
            editor.commit();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("email",text_email.getText().toString().trim());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid mail ID or Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerPage(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
