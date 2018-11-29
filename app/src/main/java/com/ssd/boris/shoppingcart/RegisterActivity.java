package com.ssd.boris.shoppingcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ssd.boris.shoppingcart.DBHandler.DBHandler;
import com.ssd.boris.shoppingcart.usermodel.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText text_name;
    private EditText text_email;
    private EditText text_password;
    private EditText text_mobile;
    private EditText text_confirm_password;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        text_email = (EditText) findViewById(R.id.email);
        text_name = (EditText) findViewById(R.id.name);
        text_password = (EditText) findViewById(R.id.password);
        text_confirm_password = (EditText) findViewById(R.id.confirmpassword);
        text_mobile = (EditText) findViewById(R.id.mobile);

    }

    public void registerUser(View view){



        if(text_name.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Name Cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (text_email.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Email Cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (text_mobile.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Mobile Number Cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (text_password.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please Set a password for registration", Toast.LENGTH_SHORT).show();
            return;
        }

        if (text_password.getText().toString().equals(text_confirm_password.getText().toString())){
            postData();
        } else {
            Toast.makeText(this, "Password did not match, Please Try again...", Toast.LENGTH_LONG).show();
        }

    }

    private void postData(){

        dbHandler = new DBHandler(this);
            if(!dbHandler.checkUser(text_email.getText().toString().trim())) {
                User user = new User(text_name.getText().toString().trim(), text_email.getText().toString().trim(), text_password.getText().toString().trim(), text_mobile.getText().toString().trim());
                dbHandler.addUser(user);
                clearTextFields();
                Log.d("register user", "User Registered");
                Toast.makeText(this,"Registered",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "User Already Exists, Sign in ...", Toast.LENGTH_SHORT).show();
            }

    }

    private void clearTextFields() {
        text_name.setText(null);
        text_email.setText(null);
        text_mobile.setText(null);
        text_password.setText(null);
        text_confirm_password.setText(null);
    }
    public void loginPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
