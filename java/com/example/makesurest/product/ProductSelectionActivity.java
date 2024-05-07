package com.example.makesurest.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
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
import com.example.makesurest.MainActivity;
import com.example.makesurest.R;
import com.example.makesurest.adapter.CompanyListAdapter;
import com.example.makesurest.adapter.ProductListAdapter;
import com.example.makesurest.batchList.BatchSelectionActivity;
import com.example.makesurest.companyselection.CompanySelection;
import com.example.makesurest.companyselection.CompanyViewModel;
import com.example.makesurest.databinding.ActivityCompanySelectionBinding;
import com.example.makesurest.databinding.ActivityProductSelectionBinding;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.ProductRequest;
import com.example.makesurest.model.ProductResponse;
import com.example.makesurest.model.ResponseCompanyListModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSelectionActivity extends AppCompatActivity {
    private ProductViewModel userViewModel;
    ProductListAdapter adapter;
    RecyclerView recyclerView;
    List<ProductResponse> productResponses;
    TextView tvNoJob,tvAGoToFindJob;
    ImageView imageView;
    Button btn;
    String comapnyId,comapny,activitys;
    TextInputEditText searchProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityProductSelectionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_selection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Product");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        searchProduct = findViewById(R.id.searchProduct);
        tvNoJob = findViewById(R.id.tvNoJob);
        tvAGoToFindJob = findViewById(R.id.tvAGoToFindJob);
        imageView = findViewById(R.id.imageView);

        tvAGoToFindJob.setVisibility(View.GONE);
        tvNoJob.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        adapter = new ProductListAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        comapnyId = intent.getStringExtra("CompanyId");
        comapny = getIntent().getStringExtra("companyName");
        activitys = getIntent().getStringExtra("activity");

        getProduct();


        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchProduct.toString().length() > 0) {
                    filter(s.toString());
//                    adapter.filter(binding.search.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.setOnItemClickListener(user -> {

//            Toast.makeText(getApplicationContext(),"You clicked : "+user.getProductName(), Toast.LENGTH_SHORT).show();

            String productName = user.getProductName();

            if(activitys.equals("aggregation")) {
                Intent i = new Intent(ProductSelectionActivity.this, BatchSelectionActivity.class);
                i.putExtra("productName", productName);
                i.putExtra("gtinCompany", user.getGtinCompanyCode());
                i.putExtra("proId", user.getProdId());
                i.putExtra("companyId", comapnyId);
                i.putExtra("activity","aggregation");
                startActivity(i);
            }
            else {
                Intent i = new Intent(ProductSelectionActivity.this, BatchSelectionActivity.class);
                i.putExtra("productName", productName);
                i.putExtra("gtinCompany", user.getGtinCompanyCode());
                i.putExtra("proId", user.getProdId());
                i.putExtra("companyId", comapnyId);
                i.putExtra("activity","dispatch");

                startActivity(i);
            }


        });

    }


    private void filter(String text) {

        // Filter the list of users based on the search text.
        ArrayList<ProductResponse> filteredList = new ArrayList<>();

        // Iterate over the original list of users.
        for (ProductResponse item :  productResponses) {
            // Check if the user's batch number contains the search text.
            if (item.getProductName().contains(text)) {
                // Add the user to the filtered list.
                filteredList.add(item);
            }
        }
        // Notify the adapter of the change.
        adapter.setUserList(filteredList);

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
                     productResponses = response.body();
                   adapter.setUserList(productResponses);
                    tvAGoToFindJob.setVisibility(View.GONE);
                    tvNoJob.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);


                }

            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                searchProduct.setVisibility(View.GONE);
                tvAGoToFindJob.setVisibility(View.VISIBLE);
                tvNoJob.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                // Handle the error.
            }
        });
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