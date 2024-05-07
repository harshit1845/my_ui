package com.example.makesurest.pakageSelection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.adapter.BatchResponseAdapter;
import com.example.makesurest.adapter.BatchStatusAdapter;
import com.example.makesurest.aggregration.AggregationMain;
import com.example.makesurest.batchList.BatchSelectionActivity;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.CountCaseResponce;
import com.example.makesurest.model.CountRequest;
import com.example.makesurest.model.PackagingCountRequest;
import com.example.makesurest.model.PackagingCountResponce;
import com.example.makesurest.model.PackgingRequest;
import com.example.makesurest.model.PackgingResponce;
import com.example.makesurest.product.ProductSelectionActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageSelectionActivity extends AppCompatActivity  {
    Button scanButton;
    PackagingAdapter adapter;
    BatchResponseAdapter adapter1;
    RecyclerView recyclerView;
    String comapnyId,proPackage;
    String  packging_level,comapny,Gtinn,Gtin,productId,batchIdd,productMatrixId,childCount,perentCount,productMatrixId1,myCount,childPackage,parentPackage,batchName;
    TextView packging_norms,companyN;
    String primaryID,secondaryID;
    String batchNo,productNamee;
    List<PackgingResponce> packging = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_selection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Packaging Level");
        recyclerView = findViewById(R.id.recyclerView);
        packging_norms = findViewById(R.id.packging_norms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        comapnyId = intent.getStringExtra("CompanyId");
        System.out.println("companyId"+comapnyId);
        comapny = getIntent().getStringExtra("companyName");
        productId = getIntent().getStringExtra("proId");
        proPackage = getIntent().getStringExtra("productPackageId");
        batchIdd = getIntent().getStringExtra("batchId");
        batchName = getIntent().getStringExtra("batchName");
        productNamee = getIntent().getStringExtra("productName");
        System.out.println("batch_no"+batchName);


        MyPackagingLevel();

        adapter = new PackagingAdapter(packging);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PackagingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PackgingResponce primaryItem, PackgingResponce secondaryItem) {


                Gtin = primaryItem.getGtinNo();
                Gtinn = secondaryItem.getGtinNo();

                childPackage = primaryItem.getLevelName();
                parentPackage = secondaryItem.getLevelName();

                 primaryID = primaryItem.getProductMatrixId();
                 secondaryID = secondaryItem.getProductMatrixId();
                String message = "Primary ID: " + primaryID + ", Secondary ID: " + secondaryID;
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                getCountChild();

                getCountP();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void MyPackagingLevel() {
        PackgingRequest packgingRequest = new PackgingRequest();
        packgingRequest.setProdId(productId);
        packgingRequest.setProdPackagingId(proPackage);
        Call<List<PackgingResponce>> call = ApiClinet.getUserService().getPackgeing(packgingRequest);
        call.enqueue(new Callback<List<PackgingResponce>>() {
            @Override
            public void onResponse(Call<List<PackgingResponce>> call, Response<List<PackgingResponce>> response) {
                if (response.isSuccessful()) {
                    List<PackgingResponce> packgingResponces = response.body();

                       packging_level =packgingResponces.get(0).getPackaging();

                     packging_norms.setText(packging_level);

                    // Sort the packgingResponces list based on product_matrix_id
                    Collections.sort(packgingResponces, new Comparator<PackgingResponce>() {
                        @Override
                        public int compare(PackgingResponce o1, PackgingResponce o2) {
                            Integer id1 = Integer.valueOf(o1.getProductMatrixId());
                            Integer id2 = Integer.valueOf(o2.getProductMatrixId());
                            return id1.compareTo(id2);
                        }
                    });
                    // Create a list to hold the level transitions
                    List<String> levelTransitions = new ArrayList<>();
                    // Iterate through the sorted list and add level transitions
                    for (int i = 0; i < packgingResponces.size() - 1; i++) {
                        String transition = packgingResponces.get(i).getLevelName() + " to " +
                                packgingResponces.get(i + 1).getLevelName();
                        levelTransitions.add(transition);
                    }
                    // Create and set the adapter for RecyclerView
                    adapter.setUserList(packgingResponces);
//                    adapter = new PackagingAdapter(packgingResponces);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PackgingResponce>> call, Throwable t) {
                // Handle the error.
            }
        });
    }

    public void getCountP() {
        CountRequest countRequest = new CountRequest();
        countRequest.setProductMatrix(secondaryID);
        countRequest.setBatchNo(batchName);
        Call<List<CountCaseResponce>> call = ApiClinet.getUserService().getCount(countRequest);
        call.enqueue(new Callback<List<CountCaseResponce>>() {
            @Override
            public void onResponse(Call<List<CountCaseResponce>> call, Response<List<CountCaseResponce>> response) {
                if (response.isSuccessful()) {
                    List<CountCaseResponce> countCaseResponce = response.body();
                    myCount = countCaseResponce.get(0).getCountParent();
                    System.out.println("mycount"+myCount);
                    Intent i = new Intent(PackageSelectionActivity.this, AggregationMain.class);
                    i.putExtra("companyName", comapny);
                    i.putExtra("batchId",batchIdd);
                    i.putExtra("companyId",comapnyId);
                    i.putExtra("proId",productId);
                    i.putExtra("proMatrixId",primaryID);
                    i.putExtra("proMatrixId1",secondaryID);
                    i.putExtra("pcount",perentCount);
                    i.putExtra("sId",secondaryID);
                    i.putExtra("ct",myCount);
                    i.putExtra("cPak",childPackage);
                    i.putExtra("pPak",parentPackage);
                    i.putExtra("batchName",batchName);
                    i.putExtra("productName",productNamee);
                    i.putExtra("gtin",Gtin);
                    i.putExtra("gtinn",Gtinn);
                    i.putExtra("norms",packging_level);
                    startActivity(i);

                }
            }

            @Override
            public void onFailure(Call<List<CountCaseResponce>> call, Throwable t) {
                // Handle the error.
            }
        });

    }

}