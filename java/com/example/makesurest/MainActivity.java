package com.example.makesurest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.makesurest.barcodeTracing.TrackDataActivity;
import com.example.makesurest.batchInfo.BatchStatus;
//import com.example.makesurest.batchInfo.BatchStatusViewModel;
import com.example.makesurest.login.LoginActivity;
import com.example.makesurest.model.ProductResponse;
import com.example.makesurest.product.ProductSelectionActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CardView batchStatus, aggregation, dispatch, traceBarcode,sampling;
    TextView companyName;
    private MainActivityViewModel userViewModel;
    String comapnyId,user;
    private ArrayList<ProductResponse> userArrayList = new ArrayList<>();
    private MutableLiveData<List<ProductResponse>> mutableLiveData = new MutableLiveData<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        batchStatus = findViewById(R.id.users_card);
        aggregation = findViewById(R.id.aggregation_card);
        dispatch = findViewById(R.id.dispatch_card);
        traceBarcode = findViewById(R.id.trace_barcode_card);
        companyName = findViewById(R.id.companyName);

        Intent intent = getIntent();

         user = intent.getStringExtra("company");
         comapnyId = intent.getStringExtra("companyId");


        System.out.println("companyId"+comapnyId);
        companyName.setText(user);

//        editor = prefs1.edit();

        batchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, BatchStatus.class);
                i.putExtra("companyName",user);
                i.putExtra("CompanyId",comapnyId);
                startActivity(i);
            }
        });

        aggregation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ProductSelectionActivity.class);
                i.putExtra("companyName",user);
                i.putExtra("CompanyId",comapnyId);
                i.putExtra("activity","aggregation");
                startActivity(i);

            }
        });

        traceBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, TrackDataActivity.class);
                i.putExtra("companyName",user);
                i.putExtra("CompanyId",comapnyId);
                i.putExtra("activity","aggregation");
                startActivity(i);

            }
        });


        dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProductSelectionActivity.class);
                i.putExtra("companyName",user);
                i.putExtra("CompanyId",comapnyId);
                i.putExtra("activity","dispatch");
                startActivity(i);

            }
        });

    }

    private boolean isLoggedIn() {
        // Retrieve the login state from local storage (e.g., SharedPreferences)
        // Return true if the user is logged in, false otherwise
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("isLoggedIn", false);
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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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