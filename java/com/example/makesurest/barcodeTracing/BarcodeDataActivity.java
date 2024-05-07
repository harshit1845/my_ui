package com.example.makesurest.barcodeTracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.adapter.ChildBarcodeAdapter;
import com.example.makesurest.model.BarcodeTracingRequest;
import com.example.makesurest.model.BarcodeTracingResponse;
import com.example.makesurest.model.ChildData;
import com.example.makesurest.model.PerantData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarcodeDataActivity extends AppCompatActivity {
    TextView srNo,product,batch,exp,pakagingLevel,gtin,childCount;
    String srno,productName,BatchNo,experieDtae,pLevel,gtinNo,child;
    RecyclerView recyclerView;
    ChildBarcodeAdapter childBarcodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_data);
        srNo = findViewById(R.id.sr_No);
        product = findViewById(R.id.product_name);
        batch = findViewById(R.id.batchNumber);
        childCount = findViewById(R.id.childCount);
        exp = findViewById(R.id.exp);
        pakagingLevel = findViewById(R.id.pLevel);
        gtin = findViewById(R.id.gtin);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        Intent i = getIntent();

        srno = i.getStringExtra("srNo");
        productName = i.getStringExtra("product");
        BatchNo = i.getStringExtra("batch");
        experieDtae = i.getStringExtra("exp");
        pLevel = i.getStringExtra("packLevel");
        gtinNo = i.getStringExtra("gtinNo");
        child =  i.getStringExtra("ChildCount");
//        String childSerialNo = getIntent().getStringExtra("childSerialNo");

//        System.out.println("cccccccccccccccccccccccccccccc"+childSerialNo);




        srNo.setText(srno);
        product.setText(productName);
        batch.setText(BatchNo);
        exp.setText(experieDtae);
        pakagingLevel.setText(pLevel);
        gtin.setText(gtinNo);
        childCount.setText(child);


        ArrayList<Parcelable> parcelableList = i.getParcelableArrayListExtra("ChildDataList");
        List<ChildData> childDataList = new ArrayList<>();
        for (Parcelable parcelable : parcelableList) {
            childDataList.add((ChildData) parcelable);
        }

        // Set up RecyclerView adapter
        childBarcodeAdapter = new ChildBarcodeAdapter();


        // Set list of ChildData objects to adapter
        childBarcodeAdapter.setUserList(childDataList);
        recyclerView.setAdapter(childBarcodeAdapter);


    }

}