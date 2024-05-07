package com.example.makesurest.dispatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.adapter.BatchResponseAdapter;
import com.example.makesurest.adapter.BatchStatusAdapter;
import com.example.makesurest.adapter.DispatchNoAdapter;
import com.example.makesurest.adapter.ProductListAdapter;
import com.example.makesurest.batchList.BatchSelectionActivity;
import com.example.makesurest.databinding.ActivityDispatchSelectionBinding;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.DispatchNumberRequest;
import com.example.makesurest.model.DispatchNumberResponce;
import com.example.makesurest.product.ProductSelectionActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispatchSelection extends AppCompatActivity {
    Button scanButton;
    ConstraintLayout cdDivider,caeDetailsCv;
    DispatchNoAdapter adapter;
    ImageView imageNoRecord;
    BatchResponseAdapter adapter1;
    TextInputLayout productSpp;
    RecyclerView recyclerView;
     ActivityDispatchSelectionBinding binding;
    String comapnyId;
    String productId,batchIdd,dispatchid,message, productName,batchName;
    TextView tvNoJob,product_name,batch_dt;
    List<DispatchNumberResponce> packgingResponces;
    ArrayList<String> dispatchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDispatchSelectionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dispatch_selection);
        recyclerView = findViewById(R.id.recyclerView);
        product_name = findViewById(R.id.product_name);
        batch_dt = findViewById(R.id.batch_dt);
        tvNoJob = findViewById(R.id.tvNoJob);
        imageNoRecord = findViewById(R.id.imageNoRecord);
        cdDivider = findViewById(R.id.cdDivider);
        caeDetailsCv = findViewById(R.id.caeDetailsCv);
//        companyN = findViewById(R.id.companyN);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        comapnyId = intent.getStringExtra("CompanyId");
        productId = getIntent().getStringExtra("proId");
        batchIdd = getIntent().getStringExtra("batchId");
        productName = getIntent().getStringExtra("productName");
        batchName = getIntent().getStringExtra("batchName");
        getDispatchDetails();

        product_name.setText(productName);
        batch_dt.setText(batchName);

        adapter = new DispatchNoAdapter();
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(user -> {

            Toast.makeText(getApplicationContext(),"You clicked : "+user.getDispatchNumber(), Toast.LENGTH_SHORT).show();

            String dispatchNumber = user.getDispatchNumber();
            String dispatchid = user.getDispatchId();
            String dispatchcount = user.getQyt();

            Intent i = new Intent(DispatchSelection.this, DispachScanning.class);
            i.putExtra("activity","dispatch");
            i.putExtra("proId", productId);
            i.putExtra("batchId",batchIdd);
            i.putExtra("CompanyId", comapnyId);
            i.putExtra("dispatchId", dispatchid);
            i.putExtra("dispatchCount", dispatchcount);
            i.putExtra("productName", productName);
            i.putExtra("batchName", batchName);
            startActivity(i);

        });


    }

    public void getDispatchDetails() {
        DispatchNumberRequest packgingRequest = new DispatchNumberRequest();
        packgingRequest.setProdId(productId);
        packgingRequest.setCompnyID(comapnyId);
        packgingRequest.setBatchId(batchIdd);
        packgingRequest.setFlag("1");
        Call<List<DispatchNumberResponce >> call = ApiClinet.getUserService().getDispatchNumber(packgingRequest);
        call.enqueue(new Callback<List<DispatchNumberResponce>>() {
            @Override
            public void onResponse(Call<List<DispatchNumberResponce>> call, Response<List<DispatchNumberResponce>> response) {
                if (response.isSuccessful()) {
                          packgingResponces = response.body();
                           message = packgingResponces.get(0).getMessage();
                           System.out.println("m"+message);

                           if (message.contains("Dispatch no not found")){
                               recyclerView.setVisibility(View.GONE);
                               cdDivider.setVisibility(View.GONE);
                               caeDetailsCv.setVisibility(View.GONE);
                               imageNoRecord.setVisibility(View.VISIBLE);
                               tvNoJob.setVisibility(View.VISIBLE);
                           }
                           System.out.println("message"+packgingResponces);
                          DispatchNoAdapter dispatchNoAdapter = new DispatchNoAdapter(); // Initialize the object
                          adapter.setUserList(packgingResponces);

                }
            }

            @Override
            public void onFailure(Call<List<DispatchNumberResponce>> call, Throwable t) {
                // Handle the error.
            }
        });

    }





}