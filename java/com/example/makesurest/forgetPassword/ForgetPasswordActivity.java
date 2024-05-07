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
import com.example.makesurest.login.LoginActivity;
import com.example.makesurest.model.ForgotPasswordRequest;
import com.example.makesurest.model.ForgotPasswordRespose;
import com.example.makesurest.model.getUserRequest;
import com.example.makesurest.model.getUserResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    String qusAnswer,password,qus,rePassword,user;
    TextInputEditText userName,Password,re;
    TextView question;
    List<ForgotPasswordRespose> productResponses;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        btn = findViewById(R.id.login);
        question = findViewById(R.id.tlEnterYourEmail);
        userName = findViewById(R.id.answerr);
        Password = findViewById(R.id.password);
        re = findViewById(R.id.re);


        Intent i = getIntent();
        qus = i.getStringExtra("qus");
        user=i.getStringExtra("user");
        question.setText(qus);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();

            }
        });
    }

    public void updatePassword() {
        qusAnswer = userName.getText().toString();
        password = Password.getText().toString();
        rePassword = re.getText().toString();
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setUsername(user);
        forgotPasswordRequest.setPasswords(password);
        forgotPasswordRequest.setSecurityAnswer(qusAnswer);
        forgotPasswordRequest.setFlag("2");

        Call<List<ForgotPasswordRespose>> call = ApiClinet.getUserService().updatePassword(forgotPasswordRequest);
        call.enqueue(new Callback<List<ForgotPasswordRespose>>() {
            @Override
            public void onResponse(Call<List<ForgotPasswordRespose>> call, Response<List<ForgotPasswordRespose>> response) {
                if (response.isSuccessful()) {
                    productResponses = response.body();

                    String message = productResponses.get(0).getMessage();

                    if (message.equals("user not exist.")) {
                        Toast.makeText(ForgetPasswordActivity.this, "Security Answer Is not Correct", Toast.LENGTH_LONG).show();

                    } else if (!password.equals(rePassword)) {
                        Toast.makeText(ForgetPasswordActivity.this, "Password Mismatch", Toast.LENGTH_LONG).show();
                    } else if (message.equals("Securityanswer is empty or null")) {
                        Toast.makeText(ForgetPasswordActivity.this, "Securityanswer is empty or null", Toast.LENGTH_LONG).show();
                    }

                    else {
                        Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                        Toast.makeText(ForgetPasswordActivity.this, "Password Update Successfully", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }


                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ForgotPasswordRespose>> call, Throwable t) {
                // Handle the error.
                System.out.println("Request failed: " + t.getMessage());
            }
        });
    }
}