package com.example.makesurest.repostory;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.makesurest.Api;
import com.example.makesurest.ApiClinet;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.ResponseBatchs;
import com.example.makesurest.model.ResponseCompanyListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchStatusRepo {

//    private ArrayList<BatchResponse> userArrayList = new ArrayList<>();
//    private MutableLiveData<List<BatchResponse>> mutableLiveData = new MutableLiveData<>();
//    private Application application;
//
//    public BatchStatusRepo(Application application) {
//        this.application = application;
//    }
//
//
//    public MutableLiveData<List<BatchResponse>> getUsers() {
//        Api apiService = ApiClinet.getUserService();
//        Call<List<BatchResponse>> call = apiService.getBatchListAPI();
//        call.enqueue(new Callback<List<BatchResponse>>() {
//            @Override
//            public void onResponse(Call<List<BatchResponse>> call, Response<List<BatchResponse>> response) {
//                if (response.body() != null) {
//                    userArrayList = (ArrayList<BatchResponse>) response.body();
//                    mutableLiveData.setValue(userArrayList);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<BatchResponse>> call, Throwable t) {
//            }
//        });
//        return mutableLiveData;
//    }

}
