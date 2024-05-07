package com.example.makesurest.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.makesurest.ApiClinet;
import com.example.makesurest.MainActivity;
import com.example.makesurest.R;
import com.example.makesurest.companyselection.CompanySelection;
import com.example.makesurest.forgetPassword.ForgetPasswordActivity;
import com.example.makesurest.forgetPassword.ForgetPasswordUserFiled;
import com.example.makesurest.model.LoginResponce;
import com.example.makesurest.model.SendLoginRequestModel;
import com.example.makesurest.utils.PasswordValidator;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    String username,password;
    TextInputEditText userName,Password;
    TextView forgetPassword;
    Button btn;
    int flag =1;
    private LoginResponce responsee;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        userName = findViewById(R.id.user_id);
        Password = findViewById(R.id.password);
        btn = findViewById(R.id.login);
        forgetPassword = findViewById(R.id.forgetPassword);
        username = userName.getText().toString();
        password = Password.getText().toString();



        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgetPasswordUserFiled.class);
                startActivity(i);
            }
        });

//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        if (isLoggedIn()) {
//            // User is already logged in, navigate to MainActivity
//            startActivity(new Intent(LoginActivity.this, CompanySelection.class));
//            finish(); // Finish LoginActivity to prevent going back to it
//            return; // Return to avoid further execution of code
//        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(userName.getText().toString())){
                    Toast.makeText(LoginActivity.this,"Username Required", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(Password.getText().toString())){
                    Toast.makeText(LoginActivity.this," Password Required", Toast.LENGTH_LONG).show();
                }

                else{

                 login();

                }

            }
        });

    }



    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    public void login() {
        String enteredPassword = Password.getText().toString();

        // Validate password
        if (!PasswordValidator.isPasswordValid(enteredPassword)) {
            Toast.makeText(LoginActivity.this, "Password must be at least 8 characters long and contain at least one special character.", Toast.LENGTH_LONG).show();
            return;
        }

        SendLoginRequestModel loginRequest = new SendLoginRequestModel();
        loginRequest.setUsername(userName.getText().toString());
        loginRequest.setPassword(Password.getText().toString());
        loginRequest.setFlag(flag);

            Call<LoginResponce> loginResponseCall = ApiClinet.getUserService().userLogin(loginRequest);

            loginResponseCall.enqueue(new Callback<LoginResponce>() {

                @Override
                public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {

                    String responseMessage = response.message();
                    Log.d("LoginApi", responseMessage);

                    if (response.isSuccessful()) {
                        LoginResponce loginResponse = response.body();
//                        loginResponse.getMessage().equals("user i")

                      if (loginResponse.isUserActive()) {
                          Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                          Intent i = new Intent(LoginActivity.this, CompanySelection.class);
                          // After successful login, set the isLoggedIn flag
//                          SharedPreferences.Editor editor = sharedPreferences.edit();
//                          editor.putBoolean("isLoggedIn", true);
//                          editor.apply();

                          // Navigate to MainActivity
                          startActivity(new Intent(LoginActivity.this, CompanySelection.class));
                          finish();

                      }

                      else  {
                          Toast.makeText(LoginActivity.this, "User or Password  not Valid.", Toast.LENGTH_LONG).show();
                      }

                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponce> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

            });



    }



}




