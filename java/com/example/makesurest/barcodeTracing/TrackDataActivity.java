package com.example.makesurest.barcodeTracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.adapter.ChildDataAdapter;
import com.example.makesurest.aggregration.AggregationComplete;
import com.example.makesurest.aggregration.AggregationMain;
import com.example.makesurest.model.BarcodeTracingRequest;
import com.example.makesurest.model.BarcodeTracingResponse;
import com.example.makesurest.model.ChildData;
import com.example.makesurest.model.PerantData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackDataActivity extends AppCompatActivity {
    String scanNos,caseid,casenumber,product_name,batchNo,exp_date,packaging_level,gtinNo,ChildCount;
    TextInputEditText scanNo;
    TextView error;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_data);
        scanNo = findViewById(R.id.caseScan);

        error = findViewById(R.id.error);

        error.setVisibility(View.GONE);


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
                            replace(scanNos);

                            Pattern pattern = Pattern.compile("01(\\d{14})");
                            Matcher matcher = pattern.matcher(scanNos);
                            caseid =  extractValueAfter("<GS>21", result);
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


    private String replace(String data){
        List<Integer> list = new ArrayList<>();
        char temp;
        for(int i=0; i<data.length();i++){
            temp = data.charAt(i);
            if(temp == 0x1d){
                list.add(i);
                //Log.e(TAG,"i:"+i);
            }
        }
        if(list == null || list.size() == 0){
            return data;
        }

        int startIndex= 0;
        for(int i=0; i<list.size(); i++){
            // Log.e(TAG,"list.get(i):"+list.get(i));
            result += data.substring(startIndex,list.get(i));
            startIndex = list.get(i)+1;
            result +="<GS>";
        }
        result += data.substring(startIndex);

        System.out.println("replace barcode :"+result);
        return result;
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
                        casenumber = parentData.getSerialNo();
                        product_name = parentData.getProductName();
                        batchNo = parentData.getBatchNumber();
                        exp_date = parentData.getExpDate();
                        packaging_level = parentData.getPackagingLevel() ;
                        gtinNo = parentData.getGtinNo();
                        ChildCount = parentData.getChildCount();

                        if (ChildCount.equals("0")){
                            error.setText("No Data Found For This Barcode");
                            error.setVisibility(View.VISIBLE);
                            error.setBackgroundResource(R.color.red);
                        }
                        else {
                            error.setVisibility(View.GONE);
                            Intent intent = new Intent(TrackDataActivity.this, BarcodeDataActivity.class);
                            intent.putExtra("srNo", casenumber);
                            intent.putExtra("product", product_name);
                            intent.putExtra("batch", batchNo);
                            intent.putExtra("exp", exp_date);
                            intent.putExtra("packLevel", packaging_level);
                            intent.putExtra("gtinNo", gtinNo);
                            intent.putExtra("ChildCount", ChildCount);
                            // Pass the list of ChildData objects as an intent extra
                            intent.putParcelableArrayListExtra("ChildDataList", new ArrayList(childDataList));
                            startActivity(intent);

                        }

                    } else {
                        System.out.println("Parent Data is null");



                    }

                    if (childDataList != null) {
                        // Log or print child data
                        for (ChildData childData : childDataList) {
                            System.out.println("Child Serial Number: " + childData.getChildSerialNo());
//                            adapter = new ChildDataAdapter(childDataList);
//                            recyclerView.setAdapter(adapter);
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



}