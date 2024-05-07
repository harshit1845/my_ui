package com.example.makesurest.barcodeTracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.adapter.ChildDataAdapter;
import com.example.makesurest.aggregration.AggregationComplete;
import com.example.makesurest.aggregration.AggregationMain;
import com.example.makesurest.model.AggregationResponce;
import com.example.makesurest.model.BarcodeTracingRequest;
import com.example.makesurest.model.BarcodeTracingResponse;
import com.example.makesurest.model.ChildData;
import com.example.makesurest.model.CountCaseResponce;
import com.example.makesurest.model.CountRequest;
import com.example.makesurest.model.DispatchNumberRequest;
import com.example.makesurest.model.DispatchNumberResponce;
import com.example.makesurest.model.PerantData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarcodeTracing extends AppCompatActivity {
    TextView batchId,errorLabel,casenumber,product_name,exp_date,packaging_level;
    int batchIdd,companyId,proId;
    String scanNos,proMatrixId,proMatrixId1,cCount,pCount,myCount,cPak,pPak,batchName,message,scanArrayWithoutBrackets,caseid;
    ArrayList ScannedArray,ScannedArrayy;
    private int countParent = 0;
    Button next, CLEARBUTTON;
    TextInputEditText scanNo;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String SelectedTypes;
    String plant[];
    TextView batchNo,packSize,scanDt;
    String hologram,batchNumber,scanDate,packSizee;
    String caseNo = "";
    String printers[];
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    int index;
    private ChildDataAdapter adapter;
    List<BarcodeTracingResponse> child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_tracing);

        scanNo = findViewById(R.id.scanNo);
        errorLabel = findViewById(R.id.errorLabel);
        batchNo = findViewById(R.id.batchNumber);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        casenumber = findViewById(R.id.casenumber);
        product_name = findViewById(R.id.product_name);
        exp_date = findViewById(R.id.exp_date);
        packaging_level = findViewById(R.id.packaging_level);

        scanNo.requestFocus();
        scanNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = scanNo.getInputType(); // backup the input type
                scanNo.setInputType(InputType.TYPE_NULL); // disable soft input
                scanNo.onTouchEvent(event); // call native handler
                scanNo.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        scanNo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                try {
                    if ((keyCode == EditorInfo.IME_ACTION_SEARCH ||
                            keyCode == EditorInfo.IME_ACTION_DONE ||
                            keyCode == EditorInfo.IME_ACTION_SEND ||
                            keyCode == EditorInfo.IME_ACTION_GO ||
                            keyCode == EditorInfo.IME_MASK_ACTION ||
                            (event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) &&
                            !event.isShiftPressed()) {

                        if (keyCode != KeyEvent.KEYCODE_BACK) {
                            // Not needed for this case
                            scanNos = scanNo.getText().toString().trim();
                            caseid =   extractValueAfter("21", scanNos);
                            getBarcodeDetails();
                            scanNo.setText("");
                            System.out.println("scan complete"+caseid);

                        }
                    }
                    else {

                    }

                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });

    }


    private String extractValueAfter(String substring, String input) {
        int index = input.indexOf(substring);
        if (index != -1) {
            // Found the substring, extract the value after it
            return input.substring(index + substring.length());
        } else {
            // Substring not found, return an empty string or handle accordingly
            return "";
        }
    }

    public void getBarcodeDetails() {
        BarcodeTracingRequest barcodeTracingRequest = new BarcodeTracingRequest();
        barcodeTracingRequest.setTrackingId(caseid);

        Call<BarcodeTracingResponse> call = ApiClinet.getUserService().getBarcodeDetails(barcodeTracingRequest);
        call.enqueue(new Callback<BarcodeTracingResponse>() {
            @Override
            public void onResponse(Call<BarcodeTracingResponse> call, Response<BarcodeTracingResponse> response) {
                if (response.isSuccessful()) {
                    BarcodeTracingResponse apiResponse = response.body();

                    PerantData parentData = apiResponse.getParentData();
                    List<ChildData> childDataList = apiResponse.getChilddata();

                    if (parentData != null) {
                        // Log or print parent data
                        System.out.println("Parent Serial Number: " + parentData.getSerialNo());
                        casenumber.setText(parentData.getSerialNo());
                        product_name.setText(parentData.getProductName());
                        batchNo.setText(parentData.getBatchNumber());
                        exp_date.setText(parentData.getExpDate());
                        packaging_level.setText(parentData.getPackagingLevel());
                    } else {
                        System.out.println("Parent Data is null");
                    }

                    if (childDataList != null) {
                        // Log or print child data
                        for (ChildData childData : childDataList) {
                            System.out.println("Child Serial Number: " + childData.getChildSerialNo());
                            adapter = new ChildDataAdapter(childDataList);
                            recyclerView.setAdapter(adapter);
                        }
                    } else {
                        System.out.println("Child Data List is null");
                    }
                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BarcodeTracingResponse> call, Throwable t) {
                // Handle the error.
                System.out.println("Request failed: " + t.getMessage());
            }
        });
    }



    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}