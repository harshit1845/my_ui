package com.example.makesurest.companyselection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.MainActivity;
import com.example.makesurest.R;
import com.example.makesurest.adapter.CompanyListAdapter;
import com.example.makesurest.databinding.ActivityCompanySelectionBinding;
import com.example.makesurest.login.LoginActivity;
import com.example.makesurest.model.BatchRequest;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.CompanyRequset;
import com.example.makesurest.model.CompanyResponce;
import com.example.makesurest.model.ResponseCompanyListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanySelection extends AppCompatActivity {

    private CompanyViewModel userViewModel;
    CompanyListAdapter adapter;
    RecyclerView recyclerView;
    List<ResponseCompanyListModel> batchResponses;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompanySelectionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_company_selection);


        if (!isLoggedIn()) {
            // User is not logged in, navigate to the login screen
            startActivity(new Intent(this, LoginActivity.class));
            finish(); // Finish the current activity so the user can't navigate back to it
        } else {
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            adapter = new CompanyListAdapter();
            recyclerView.setAdapter(adapter);
        }


        adapter.setOnItemClickListener(user -> {

            Toast.makeText(getApplicationContext(),"You clicked : "+user.getCompanyName(), Toast.LENGTH_SHORT).show();

            String company = user.getCompanyName();

            Intent i = new Intent(CompanySelection.this, CompanyWithLicence.class);
            i.putExtra("company", company);
            i.putExtra("gtinCompany", user.getGtinCompanyCode());
            i.putExtra("gtinCounty", user.getGtinCountryCode());
            i.putExtra("companyId", user.getCompanyId());
            startActivity(i);



        });

        getData();



    }

    // Check if the user is logged in
    private boolean isLoggedIn() {
        // Retrieve the login state from local storage (e.g., SharedPreferences)
        // Return true if the user is logged in, false otherwise
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void getData() {
        CompanyRequset companyRequset = new CompanyRequset();
        companyRequset.setCompanyId(null);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // shows the setting menu
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // given the on click feature for the menu
        int id = item.getItemId();
        switch (id) {
//            case R.id.sqlConnectionServer:
//                startActivity(new Intent(MainActivity.this, SQLConnection.class));
//                return true;
//            case R.id.settings:
//                startActivity(new Intent(MainActivity.this, Settings.class));
//                return true;
            case R.id.logout_menu:
//                editor.clear().commit();
//                editor2.clear().commit();
                startActivity(new Intent(CompanySelection.this, LoginActivity.class));
                finish();
                return true;
//            case R.id.offlineScanning:
//                startActivity(new Intent(MainActivity.this, OfflineCaseScan.class));
//                return true;

                /* case R.id.palettesearch:
                startActivity(new Intent(MainActivity.this, PaletteSearch.class));
                return true;

            case R.id.casesearch:
                startActivity(new Intent(MainActivity.this, CaseSearch.class));
                return true;
            */

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }




}