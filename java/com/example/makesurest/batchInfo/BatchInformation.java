package com.example.makesurest.batchInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.model.BatchRequest;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.PackgingRequest;
import com.example.makesurest.model.PackgingResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchInformation extends AppCompatActivity {
    String proId,productPackageId,batchId,batchName,productName,status,companyId,batchSize,date,packLevel;
    TextView bNo,bId,st,exp,pLevel,bSize;
    List<BatchResponse> batchResponses;
    List<PackgingResponce> packgingResponces;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_information);



        bNo = findViewById(R.id.bNo);
        bId =findViewById(R.id.bId);
        st = findViewById(R.id.st);
        exp = findViewById(R.id.exp);
        pLevel = findViewById(R.id.pLevel);
        bSize = findViewById(R.id.bSize);


        proId = getIntent().getStringExtra("proId");
        companyId = getIntent().getStringExtra("companyId");
        productPackageId = getIntent().getStringExtra("productPackageId");
        batchId = getIntent().getStringExtra("batchId");
        batchName = getIntent().getStringExtra("batchName");
        productName = getIntent().getStringExtra("productName");
        status = getIntent().getStringExtra("status");
        batchSize = getIntent().getStringExtra("batchSize");
        date = getIntent().getStringExtra("date");

//        progressBar.setVisibility(View.VISIBLE);

        MyPackagingLevel();

        bNo.setText(batchName);
        bId.setText(batchId);
        st.setText(status);
        bSize.setText(batchSize);
        exp.setText(date);





    }


    public void MyPackagingLevel() {
        PackgingRequest packgingRequest = new PackgingRequest();
        packgingRequest.setProdId(proId);
        packgingRequest.setProdPackagingId(productPackageId);
        Call<List<PackgingResponce>> call = ApiClinet.getUserService().getPackgeing(packgingRequest);
        call.enqueue(new Callback<List<PackgingResponce>>() {
            @Override
            public void onResponse(Call<List<PackgingResponce>> call, Response<List<PackgingResponce>> response) {
                if (response.isSuccessful()) {
                    // Parse the response from the API into a list of ProductResponse objects.
                    packgingResponces = response.body();
                    if (packgingResponces != null && !packgingResponces.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (PackgingResponce packgingResponce : packgingResponces) {
                            stringBuilder.append(packgingResponce.getLevelName()).append("\n");
                        }
                        pLevel.setText(stringBuilder.toString());
                    } else {
                        // Handle the case when the response body is empty or null
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PackgingResponce>> call, Throwable t) {
                // Handle the error.
            }
        });

    }

}