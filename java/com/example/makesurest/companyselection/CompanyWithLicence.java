package com.example.makesurest.companyselection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.MainActivity;
import com.example.makesurest.R;
import com.example.makesurest.adapter.CompanyLicenceListAdapter;
import com.example.makesurest.adapter.CompanyListAdapter;
import com.example.makesurest.databinding.ActivityCompanySelectionBinding;
import com.example.makesurest.databinding.ActivityCompanyWithLicenceBinding;
import com.example.makesurest.model.CompanyRequset;
import com.example.makesurest.model.ResponseCompanyListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyWithLicence extends AppCompatActivity {
    private CompanyViewModel userViewModel;
    CompanyListAdapter adapter;
    RecyclerView recyclerView;
    List<ResponseCompanyListModel> batchResponses;
    String comapanyId,gtinCounty,gtinCompany,company;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompanyWithLicenceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_company_with_licence);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new CompanyListAdapter();
        recyclerView.setAdapter(adapter);


        comapanyId = getIntent().getStringExtra("companyId");
        gtinCounty = getIntent().getStringExtra("gtinCounty");
        gtinCompany = getIntent().getStringExtra("gtinCompany");
        company = getIntent().getStringExtra("company");

//        userViewModel = ViewModelProviders.of(this).get(CompanyViewModel.class);
        adapter.setOnItemClickListener(user -> {

            Toast.makeText(getApplicationContext(),"You clicked : "+user.getCompanyName(), Toast.LENGTH_SHORT).show();

            String company = user.getCompanyName();
            System.out.println("company"+company);

            Intent i = new Intent(CompanyWithLicence.this, MainActivity.class);
            i.putExtra("company", company);
            i.putExtra("gtinCompany", user.getGtinCompanyCode());
            i.putExtra("gtinCounty", user.getGtinCountryCode());
            i.putExtra("companyId", user.getCompanyId());
            startActivity(i);

//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if (company == null || company.isEmpty()) {
//                        Toast.makeText(getApplicationContext(), "Select Company Name", Toast.LENGTH_SHORT).show();
//
//                    }
//                    else {
//
//
//                    }
//                }
//            });

        });

        getData();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(CompanySelection.this, MainActivity.class);
//                startActivity(i);
////                i.putExtra("companyName",)
//
//            }
//        });

    }

    private void getData() {
//        userViewModel.getAllUsers().observe(this, userList -> {
//            adapter.setUserList((ArrayList<ResponseCompanyListModel>) userList);

//            System.out.println("userList"+userList);



//        });

        CompanyRequset companyRequset = new CompanyRequset();
        companyRequset.setCompanyId(comapanyId);
        companyRequset.setFlag("1");
        Call<List<ResponseCompanyListModel>> call = ApiClinet.getUserService().getCompanyListAPI(companyRequset);
        call.enqueue(new Callback<List<ResponseCompanyListModel>>() {
            @Override
            public void onResponse(Call<List<ResponseCompanyListModel>> call, Response<List<ResponseCompanyListModel>> response) {
                if (response.isSuccessful()) {
                    // Parse the response from the API into a list of ProductResponse objects.
                    batchResponses = response.body();

                    adapter.setUserList(batchResponses);


                }

            }

            @Override
            public void onFailure(Call<List<ResponseCompanyListModel>> call, Throwable t) {
                // Handle the error.

            }
        });

    }
}