package com.example.makesurest.repostory;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.makesurest.Api;
import com.example.makesurest.ApiClinet;
import com.example.makesurest.model.ResponseCompanyListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyListRepo {

//    private ArrayList<ResponseCompanyListModel> userArrayList = new ArrayList<>();
//    private MutableLiveData<List<ResponseCompanyListModel>> mutableLiveData = new MutableLiveData<>();
//    private Application application;
//
//    public CompanyListRepo(Application application) {
//        this.application = application;
//    }
//
//    public MutableLiveData<List<ResponseCompanyListModel>> getUsers() {
//        Api apiService = ApiClinet.getUserService();
//        Call<List<ResponseCompanyListModel>> call = apiService.getCompanyListAPI();
//        call.enqueue(new Callback<List<ResponseCompanyListModel>>() {
//            @Override
//            public void onResponse(Call<List<ResponseCompanyListModel>> call, Response<List<ResponseCompanyListModel>> response) {
//                if (response.body() != null) {
//                    userArrayList = (ArrayList<ResponseCompanyListModel>) response.body();
//                    mutableLiveData.setValue(userArrayList);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<ResponseCompanyListModel>> call, Throwable t) {
//            }
//        });
//        return mutableLiveData;
//    }
}
