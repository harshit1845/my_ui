package com.example.makesurest.repostory;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.makesurest.Api;
import com.example.makesurest.ApiClinet;
import com.example.makesurest.model.ProductResponse;
import com.example.makesurest.model.ResponseBatchs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepo {

//    private ArrayList<ProductResponse> userArrayList = new ArrayList<>();
//    private MutableLiveData<List<ProductResponse>> mutableLiveData = new MutableLiveData<>();
//    private Application application;
//
//    public ProductRepo(Application application) {
//        this.application = application;
//    }
//
//
//    public MutableLiveData<List<ProductResponse>> getUsers() {
//        Api apiService = ApiClinet.getUserService();
//        Call<List<ProductResponse>> call = apiService.getProductListAPI();
//        call.enqueue(new Callback<List<ProductResponse>>() {
//            @Override
//            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
//                if (response.body() != null) {
//                    userArrayList = (ArrayList<ProductResponse>) response.body();
//                    mutableLiveData.setValue(userArrayList);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
//            }
//        });
//        return mutableLiveData;
//    }
}
