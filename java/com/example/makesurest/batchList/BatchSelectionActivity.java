package com.example.makesurest.batchList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.adapter.BatchListAdapter;
import com.example.makesurest.adapter.ProductListAdapter;
import com.example.makesurest.batchInfo.BatchStatus;
import com.example.makesurest.databinding.ActivityBatchSelectionBinding;
import com.example.makesurest.databinding.ActivityBatchSelectionBindingImpl;
import com.example.makesurest.databinding.ActivityProductSelectionBinding;
import com.example.makesurest.dispatch.DispatchSelection;
import com.example.makesurest.model.BatchRequest;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.ProductRequest;
import com.example.makesurest.model.ProductResponse;
import com.example.makesurest.pakageSelection.PackageSelectionActivity;
import com.example.makesurest.product.ProductViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchSelectionActivity extends AppCompatActivity {
    private ProductViewModel userViewModel;
    BatchListAdapter adapter;
    RecyclerView recyclerView;
    Button btn;
    String productId,productName,companyId,activitys,productPackageId,batchId,batchName;
    TextView tvNoJob,tvAGoToFindJob;
    ImageView imageView;
    List<BatchResponse> batchResponses;
    TextInputEditText searchBatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBatchSelectionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_batch_selection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Batch");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        tvNoJob = findViewById(R.id.tvNoJob);
        tvAGoToFindJob = findViewById(R.id.tvAGoToFindJob);
        imageView = findViewById(R.id.imageView);
        searchBatch = findViewById(R.id.searchBatch);

        tvAGoToFindJob.setVisibility(View.GONE);
        tvNoJob.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        adapter = new BatchListAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        productId = intent.getStringExtra("proId");
        productName = getIntent().getStringExtra("productName");
        companyId = getIntent().getStringExtra("companyId");

        activitys = getIntent().getStringExtra("activity");
        getBatch();


        searchBatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchBatch.toString().length() > 0) {
                    filter(s.toString());
//                    adapter.filter(binding.search.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.setOnItemClickListener(user -> {

//            Toast.makeText(getApplicationContext(),"You clicked : "+user.getBatchNumber(), Toast.LENGTH_SHORT).show();
                  productPackageId = user.getProdPackageId();
                  batchId =user.getBatchId();
                  batchName =user.getBatchNumber();

//            String company = user.getProductName();


            if (activitys.equals("aggregation")) {

                Intent i = new Intent(BatchSelectionActivity.this, PackageSelectionActivity.class);
//            i.putExtra("company", company);
                i.putExtra("proId", productId);
                i.putExtra("productPackageId", user.getProdPackageId());
                i.putExtra("batchId", user.getBatchId());
                i.putExtra("batchName", user.getBatchNumber());
                i.putExtra("productName",productName);
                i.putExtra("activity","aggregation");
                i.putExtra("CompanyId",companyId);

                startActivity(i);
            }

            else {
                Intent i = new Intent(BatchSelectionActivity.this, DispatchSelection.class);
                i.putExtra("activity","dispatch");
                i.putExtra("proId", productId);
                i.putExtra("batchId", user.getBatchId());
                i.putExtra("batchName", user.getBatchNumber());
                i.putExtra("productName", productName);
                i.putExtra("CompanyId", companyId);
                startActivity(i);
            }

        });

    }


    private void filter(String text) {

        // Filter the list of users based on the search text.
        ArrayList<BatchResponse> filteredList = new ArrayList<>();

        // Iterate over the original list of users.
        for (BatchResponse item :  batchResponses) {
            // Check if the user's batch number contains the search text.
            if (item.getBatchNumber().contains(text)) {
                // Add the user to the filtered list.
                filteredList.add(item);
            }
        }
        // Notify the adapter of the change.
        adapter.setUserList(filteredList);

    }

    public void getBatch() {
        BatchRequest batchRequest = new BatchRequest();
        batchRequest.setProdId(productId);
        batchRequest.setCompnyID(companyId);
        Call<List<BatchResponse>> call = ApiClinet.getUserService().getBatch(batchRequest);
        call.enqueue(new Callback<List<BatchResponse>>() {
            @Override
            public void onResponse(Call<List<BatchResponse>> call, Response<List<BatchResponse>> response) {
                if (response.isSuccessful()) {
                    // Parse the response from the API into a list of ProductResponse objects.
                    batchResponses = response.body();

                    adapter.setUserList(batchResponses);
                    tvAGoToFindJob.setVisibility(View.GONE);
                    tvNoJob.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<List<BatchResponse>> call, Throwable t) {
                // Handle the error.
                showNoDataMessage("No Data Found in This Product");
                searchBatch.setVisibility(View.GONE);
                tvAGoToFindJob.setVisibility(View.VISIBLE);
                tvNoJob.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }
        });

    }


    private void showNoDataMessage(String message) {
        // Show a message indicating that no data is available
        // You can use a TextView or any other UI element to display the message
        Toast.makeText(BatchSelectionActivity.this, message, Toast.LENGTH_SHORT).show();
    }


//    private void getData() {
//        userViewModel.getAllUsers().observe(this, userList -> {
//            adapter.setUserList((ArrayList<ProductResponse>) userList);
//
////            System.out.println("userList"+userList);
//
//
//
//        });
//    }
}