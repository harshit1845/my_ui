package com.example.makesurest.batchInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.MainActivity;
import com.example.makesurest.R;
import com.example.makesurest.adapter.BatchResponseAdapter;
import com.example.makesurest.adapter.BatchStatusAdapter;
import com.example.makesurest.adapter.CompanyListAdapter;
import com.example.makesurest.batchList.BatchSelectionActivity;
import com.example.makesurest.companyselection.CompanySelection;
import com.example.makesurest.companyselection.CompanyViewModel;
import com.example.makesurest.databinding.ActivityBatchStatusBinding;
import com.example.makesurest.databinding.ActivityCompanySelectionBinding;
import com.example.makesurest.dispatch.DispatchSelection;
import com.example.makesurest.model.BatchRequest;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.ProductRequest;
import com.example.makesurest.model.ProductResponse;
import com.example.makesurest.model.ResponseBatchs;
import com.example.makesurest.model.ResponseCompanyListModel;
import com.example.makesurest.pakageSelection.PackageSelectionActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchStatus extends AppCompatActivity   {
    BatchStatusAdapter adapter;
    BatchResponseAdapter adapter1;
    RecyclerView recyclerView;
    private ActivityBatchStatusBinding binding;
    String comapnyId,batchName;
    private Spinner spinner;
    String   batchId,comapny,status,st;
    TextView bid,companyN,text;
    String productId,batchStatus;
    List<BatchResponse> productResponses;
    ArrayList<String> dispp = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    ArrayList<String> pId = new ArrayList<>();
    View noRecordLayout;
    TextInputLayout statusSp,search;
    ConstraintLayout searchh;

//    String[] items = {"ready-0","In production-1","production Done-2", "Batch Completed-3" };

    AutoCompleteTextView autoCompleteTextView,autoSp;
    ArrayAdapter<String> adapterItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_batch_status);
//         noRecordLayout = findViewById(R.id.no_record_layout);
        recyclerView = findViewById(R.id.recycler_view);
        companyN = findViewById(R.id.companyN);

        autoSp = findViewById(R.id.autoCompletesp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new BatchStatusAdapter();
        bid= findViewById(R.id.batchStatus1);
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        comapnyId = intent.getStringExtra("CompanyId");
//        productResponseParcelable = getIntent().getParcelableExtra("productIdsParcelable");
//        pId = getIntent().getParcelableExtra("pId");
        comapny = getIntent().getStringExtra("companyName");
//        System.out.println("product"+productResponseParcelable);
        companyN.setText(comapny);

        binding.search.setVisibility(View.GONE);
        binding.searchh.setVisibility(View.GONE);

        binding.imageNoRecord.setVisibility(View.VISIBLE);
//        binding.text.setVisibility(View.GONE);


//        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item,items);

     getProduct();
        System.out.println("abcd"+dispp);

        ArrayAdapter<String> printerSpinnerArray = new ArrayAdapter<String>(BatchStatus.this, android.R.layout.simple_spinner_item, productName);
        autoSp.setAdapter(printerSpinnerArray);

       autoSp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               String item = adapterView.getItemAtPosition(i).toString();

              Toast.makeText(getApplicationContext(),"You clicked : "+item, Toast.LENGTH_SHORT).show();
               productId = pId.get(i);
               System.out.println("piddd"+productId);
               // Clear values in the second dropdown

               sendData();
               binding.search.setVisibility(View.VISIBLE);
               binding.searchh.setVisibility(View.VISIBLE);
//               binding.text.setVisibility(View.VISIBLE);
               binding.imageNoRecord.setVisibility(View.GONE);
               binding.tvAGoToFindJob.setVisibility(View.GONE);
               binding.tvNoJob.setVisibility(View.GONE);
           }
       });


        binding.searchBatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.search.toString().length() > 0) {
                    filter(s.toString());
//                    adapter.filter(binding.search.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        adapter.setOnItemClickListener(user -> {

            Toast.makeText(getApplicationContext(),"You clicked : "+user.getBatchNumber(), Toast.LENGTH_SHORT).show();


            status = user.getFlag();

            batchId =user.getBatchId();
            batchName =user.getBatchNumber();


            if (status.equals("1")){
               st = "In production";
            }
            else if (status.equals("0")){
                st = "ready";
            }

            else if (status.equals("2")){

                st = "production Done";
            }

            else if (status.equals("3")){

                st = "Batch Complete";
            }

                Intent i = new Intent(BatchStatus.this, BatchInformation.class);
                i.putExtra("proId", productId);
                i.putExtra("productPackageId", user.getProdPackageId());
                i.putExtra("batchId", user.getBatchId());
                i.putExtra("batchName", user.getBatchNumber());
                i.putExtra("productName",productName);
                i.putExtra("status",st);
                i.putExtra("companyId",comapnyId);
                i.putExtra("batchSize",user.getBatchSize());
                i.putExtra("date",user.getDate());

//                i.putExtra("packaging",user.p);

                i.putExtra("activity","aggregation");

                startActivity(i);

        });

    }

    private void filter(String text) {

            // Filter the list of users based on the search text.
            ArrayList<BatchResponse> filteredList = new ArrayList<>();
            // Iterate over the original list of users.
            for (BatchResponse item :  productResponses) {
                // Check if the user's batch number contains the search text.
                if (item.getBatchNumber().contains(text)) {
                    // Add the user to the filtered list.
                    filteredList.add(item);
                }
            }
            // Notify the adapter of the change.
            adapter.setUserList(filteredList);
    }

    private void sendData() {
        BatchRequest batchRequest = new BatchRequest();
        batchRequest.setProdId(productId);
        batchRequest.setCompnyID(comapnyId);

        Call<List<BatchResponse>> call = ApiClinet.getUserService().getBatch(batchRequest);
        call.enqueue(new Callback<List<BatchResponse>>() {
            @Override
            public void onResponse(Call<List<BatchResponse>> call, Response<List<BatchResponse>> response) {
                if (response.isSuccessful()) {
                     productResponses = response.body();
                    if (productResponses != null && !productResponses.isEmpty()) {
                        // Data is available, update RecyclerView
                        updateRecyclerView(productResponses);
                    } else {
                        // No data available, show message
                        showNoDataMessage("Batch Not Available");
                    }
                } else {
                    // Handle API error
                    showNoDataMessage("API Error");
                }
            }

            @Override
            public void onFailure(Call<List<BatchResponse>> call, Throwable t) {
                // Handle network or other errors
                showNoDataMessage("No Data Found in This Product");
            }
        });
    }

    private void showNoDataMessage(String message) {
        // Show a message indicating that no data is available
        // You can use a TextView or any other UI element to display the message
//        noRecordLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE); // Hide RecyclerView when no data is available
        Toast.makeText(BatchStatus.this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateRecyclerView(List<BatchResponse> batchResponses) {
        // Update RecyclerView adapter with data
        adapter.setUserList(batchResponses);
//        noRecordLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }



    public void getProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCompanyId(comapnyId);
        Call<List<ProductResponse>> call = ApiClinet.getUserService().getProductList(productRequest);
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    // Parse the response from the API into a list of ProductResponse objects.
                    List<ProductResponse> productResponses = response.body();

                    for (ProductResponse batchResponsess : productResponses) {
                        productName.add(batchResponsess.getProductName());
                        pId.add(batchResponsess.getProdId());
                    }

//                    for (ProductResponse batchResponsess : productResponses) {
//                        pId.add(batchResponsess.getProdId());
//                    }

                }

            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                // Handle the error.
            }
        });

    }

}












