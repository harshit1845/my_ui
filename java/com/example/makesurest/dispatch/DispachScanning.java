package com.example.makesurest.dispatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.Helpers;
import com.example.makesurest.R;
import com.example.makesurest.adapter.CaseChildAdapter;
import com.example.makesurest.aggregration.AggregationMain;
import com.example.makesurest.model.DispatchNumberRequest;
import com.example.makesurest.model.DispatchNumberResponce;
import com.example.makesurest.model.DispatchScanningResponce;
import com.example.makesurest.model.DispatchScanningResquest;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispachScanning extends AppCompatActivity {
    String companyId,batchId,productId,dispatchId,activity,dispatchCount,scanCases,productName,batchName;
    TextInputEditText scanCase;
    TextView errorLabel,countCase,proName,batchNamee;
    ArrayList ScannedArray;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> qrCodesList = new ArrayList<>();
    ConstraintLayout errorLabeleCv,caseChildCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispach_scanning);
        scanCase = findViewById(R.id.scanHG);
        countCase = findViewById(R.id.countCase);
        errorLabel = findViewById(R.id.errorLabel);
        proName = findViewById(R.id.proName);
        batchNamee = findViewById(R.id.batchId);
        errorLabeleCv = findViewById(R.id.errorLabeleCv);
        caseChildCv = findViewById(R.id.caseChildCv);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ScannedArray = new ArrayList();
        Intent intent = getIntent();
        companyId = intent.getStringExtra("CompanyId");
        productId = getIntent().getStringExtra("proId");
        batchId = getIntent().getStringExtra("batchId");
        dispatchId = getIntent().getStringExtra("dispatchId");
        dispatchCount = getIntent().getStringExtra("dispatchCount");
        productName = getIntent().getStringExtra("productName");
        batchName = getIntent().getStringExtra("batchName");
        scanCase.requestFocus();

        proName.setText(productName);
        batchNamee.setText(batchName);
        errorLabel.setVisibility(View.GONE);

        scanCase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = scanCase.getInputType(); // backup the input type
                scanCase.setInputType(InputType.TYPE_NULL); // disable soft input
                scanCase.onTouchEvent(event); // call native handler
                scanCase.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        scanCase.setOnKeyListener(new View.OnKeyListener() {
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
                        errorLabeleCv.setVisibility(View.VISIBLE);


                        scanCases = scanCase.getText().toString();

                        errorLabel.setVisibility(View.VISIBLE);

                        int sizearray = qrCodesList.size();

                        System.out.println("size"+sizearray);
                        System.out.println("HOLOGRAM NO"+qrCodesList);

                        if (keyCode != KeyEvent.KEYCODE_BACK) {

                            if (scanCases.length() < 25) {
                                if (sizearray < Integer.parseInt(String.valueOf(dispatchCount))) {

                                    if (!qrCodesList.contains(scanCases)) {
                                        callDispatchAPI();

                                    } else {
                                        errorLabel.setText("Already scan");
                                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                                        scanCase.setText("");
                                        Toast.makeText(DispachScanning.this, "Already scan", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    errorLabel.setText("Dispatch completed can't add more");
                                    errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                                    errorLabel.setTextColor(getResources().getColor(R.color.red));
                                    scanCase.setText("");
                                    Toast.makeText(DispachScanning.this, "Dispatch completed can't add more", Toast.LENGTH_SHORT).show();
                                }

                            } else if (scanCases.length() >= 25) {
                                errorLabel.setText("Barcode length is not correct");
                                errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                                errorLabel.setTextColor(getResources().getColor(R.color.red));
                                scanCase.setText("");
                                Toast.makeText(DispachScanning.this, "Barcode length is not correct", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });

    }


    public void callDispatchAPI() {
        DispatchScanningResquest dispatchScanningResquest = new DispatchScanningResquest();
        dispatchScanningResquest.setProdId(productId);
        dispatchScanningResquest.setCompnyID(companyId);
        dispatchScanningResquest.setBatchId(batchId);
        dispatchScanningResquest.setDispatchId(dispatchId);
        dispatchScanningResquest.setSscc(scanCases);
        dispatchScanningResquest.setFlag("2");

        Call<List<DispatchScanningResponce>> call = ApiClinet.getUserService().getDispatchScanning(dispatchScanningResquest);
        call.enqueue(new Callback<List<DispatchScanningResponce>>() {
            @Override
            public void onResponse(Call<List<DispatchScanningResponce>> call, Response<List<DispatchScanningResponce>> response) {
                if (response.isSuccessful()) {
                    List<DispatchScanningResponce> packgingResponces = response.body();


                    String message = packgingResponces.get(0).getMessage();

                    if (message.contains("SSCC Number not Found....")){
                        errorLabel.setText("SSCC Number not Found....");
                        scanCase.setText("");
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                    }


                    else if (message.contains("Batch does not exist....")){
                        errorLabel.setText("Batch Id Mismatch....");
                        scanCase.setText("");
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if (message.contains("Already dispatched....")){
                        errorLabel.setText("Already dispatched....");
                        scanCase.setText("");
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                    }
                    else {
                        caseChildCv.setVisibility(View.VISIBLE);
                        Collections.reverse(qrCodesList);
                        qrCodesList.add(scanCases);
                        Collections.reverse(qrCodesList);
                        //Toast.makeText(CreateCase.this, "Valid barcode", Toast.LENGTH_SHORT).show();
                        scanCase.setText("");
                        countCase.setText("Total Scan " + qrCodesList.size() + " From " + dispatchCount);
                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa" + qrCodesList.size());
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(DispachScanning.this);
                        recyclerView.setLayoutManager(layoutManager);
//                        mAdapter = new DispachScanning.MyAdapter(ScannedArray);
//                        recyclerView.setAdapter(mAdapter);

                        CaseChildAdapter adapter = new CaseChildAdapter(getBaseContext(), qrCodesList);
                        recyclerView.setAdapter(adapter);

                        errorLabel.setText("Dispatch Successfully");
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_green_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.colorGreen));
                        Helpers.ScanOk();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<DispatchScanningResponce>> call, Throwable t) {
                // Handle the error.
            }
        });

    }

    @Override
    protected void onResume() {
//        scanCase.clearFocus();
        super.onResume();

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}