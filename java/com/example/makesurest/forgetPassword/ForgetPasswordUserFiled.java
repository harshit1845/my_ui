package com.example.makesurest.forgetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.adapter.ChildDataAdapter;
import com.example.makesurest.login.LoginActivity;
import com.example.makesurest.model.BarcodeTracingRequest;
import com.example.makesurest.model.BarcodeTracingResponse;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.ChildData;
import com.example.makesurest.model.LoginResponce;
import com.example.makesurest.model.PerantData;
import com.example.makesurest.model.getUserRequest;
import com.example.makesurest.model.getUserResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordUserFiled extends AppCompatActivity {
    String username,password;
    TextInputEditText userName,Password;
    TextView forgetPassword;
    Button btn;
    List<getUserResponse> productResponses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_user_filed);

        userName = findViewById(R.id.user_id);

        btn = findViewById(R.id.login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSecurityQus();

            }
        });
    }

    public void getSecurityQus() {
        username = userName.getText().toString();
        getUserRequest getUserRequest = new getUserRequest();
        getUserRequest.setUsername(username);
        getUserRequest.setFlag("1");

        Call<List<getUserResponse>> call = ApiClinet.getUserService().getUser(getUserRequest);
        call.enqueue(new Callback<List<getUserResponse>>() {
            @Override
            public void onResponse(Call<List<getUserResponse>> call, Response<List<getUserResponse>> response) {
                if (response.isSuccessful()) {
                    productResponses = response.body();

                    String message = productResponses.get(0).getMessage();


//                    if (message == null){
                        String flag = productResponses.get(0).getSecurityQuestion();

                    if (flag.equals("None")){
                        System.out.println("fffffffff" + message);
                        Toast.makeText(ForgetPasswordUserFiled.this," user is not valid", Toast.LENGTH_LONG).show();
                        return;
                    }

                        System.out.println("fffffffffffffffffff" + flag);

                        Intent i = new Intent(ForgetPasswordUserFiled.this, ForgetPasswordActivity.class);
                        i.putExtra("qus",flag);
                        i.putExtra("user",username);
                        startActivity(i);
//                    }

                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<getUserResponse>> call, Throwable t) {
                // Handle the error.
                System.out.println("Request failed: " + t.getMessage());
            }
        });
    }}