package com.ssd.boris.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ssd.boris.shoppingcart.DBHandler.DBHandler;
import com.ssd.boris.shoppingcart.usermodel.User;

public class UserProfileActivity extends AppCompatActivity {

    private String email;
    private TextView username;
    private TextView useremail;
    private TextView userphone;
    private DBHandler dbHandler;
    private User user;
    SharedPreferences sharedPreferences;
    public static final String mypreferences = "a";
    public static final String Email ="email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = (TextView)findViewById(R.id.username);
        useremail = (TextView)findViewById(R.id.useremail);
        userphone = (TextView)findViewById(R.id.userphone);
        dbHandler = new DBHandler(this);
        user = dbHandler.getUser(email);
        username.setText(user.getName());
        //useremail.setText(user.getEmail());
        sharedPreferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        useremail.setText(sharedPreferences.getString(Email,""));
        userphone.setText(user.getMobile());
    }

    public void logoutUser(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
