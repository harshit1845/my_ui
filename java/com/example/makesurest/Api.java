package com.example.makesurest;

import com.example.makesurest.model.ActionLogRequest;
import com.example.makesurest.model.ActionLogResponce;
import com.example.makesurest.model.AggregationRequest;
import com.example.makesurest.model.AggregationResponce;
import com.example.makesurest.model.BarcodeTracingRequest;
import com.example.makesurest.model.BarcodeTracingResponse;
import com.example.makesurest.model.BatchRequest;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.CheckDuplicateRequest;
import com.example.makesurest.model.CheckDuplicateResponse;
import com.example.makesurest.model.CompanyRequset;
import com.example.makesurest.model.CountCaseResponce;
import com.example.makesurest.model.CountRequest;
import com.example.makesurest.model.DispatchNumberRequest;
import com.example.makesurest.model.DispatchNumberResponce;
import com.example.makesurest.model.DispatchScanningResponce;
import com.example.makesurest.model.DispatchScanningResquest;
import com.example.makesurest.model.ForgotPasswordRequest;
import com.example.makesurest.model.ForgotPasswordRespose;
import com.example.makesurest.model.LoginResponce;
import com.example.makesurest.model.PackagingCountRequest;
import com.example.makesurest.model.PackagingCountResponce;
import com.example.makesurest.model.PackgingRequest;
import com.example.makesurest.model.PackgingResponce;
import com.example.makesurest.model.ProductRequest;
import com.example.makesurest.model.ProductResponse;
import com.example.makesurest.model.ResponseCompanyListModel;
import com.example.makesurest.model.SendLoginRequestModel;
import com.example.makesurest.model.getUserRequest;
import com.example.makesurest.model.getUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @POST("Login/CheckLogin")
    Call<LoginResponce> userLogin(@Body SendLoginRequestModel loginRequest);

    @POST("Company/GetCompanyInfo")
    Call<List<ResponseCompanyListModel>> getCompanyListAPI(@Body CompanyRequset productRequest);

//    @GET("Company/GetCompanyInfo")
//    Call<List<ResponseCompanyListModel>> getCompanyListAPI();

    @POST("Product/Productbind")
    Call<List<ProductResponse>> getProductList(@Body ProductRequest productRequest);

    @POST("BatchDetail/Batchbind")
    Call<List<BatchResponse>> getBatch(@Body BatchRequest productRequest);

    @POST("Packeging/Packeginginfo")
    Call<List<PackgingResponce>> getPackgeing(@Body PackgingRequest productRequest);

    @POST("Packeging/getcount")
    Call<List<PackagingCountResponce>> getPackagingCount(@Body PackagingCountRequest productRequest);

    @POST("Aggregation/BulkAggregationInsert")
    Call<List<AggregationResponce>> aggregationApi(@Body AggregationRequest aggregationRequest);

    @POST("DispatchDetail/DispatchInfo")
    Call<List<DispatchNumberResponce>> getDispatchNumber(@Body DispatchNumberRequest dispatchNumberRequest);

    @POST("CaseCountParent/CaseCountParentInfo")
    Call<List<CountCaseResponce>> getCount(@Body CountRequest countRequest);

    @POST("DispatchDetail/DispatchDetailInfo")
    Call<List<DispatchScanningResponce>> getDispatchScanning(@Body DispatchScanningResquest dispatchScanningResquest);

    @POST("TrackTraceBarcode/TrackTraceBarcodeDetail")
    Call<BarcodeTracingResponse> getBarcodeDetails(@Body BarcodeTracingRequest barcodeTracingRequest);

    @POST("UserDetail/SelectUser")
    Call<List<getUserResponse>> getUser(@Body getUserRequest getUserRequest);

    @POST("UserDetail/ForgetPassword")
    Call<List<ForgotPasswordRespose>> updatePassword(@Body ForgotPasswordRequest getUserRequest);

    @POST("Aggregation/aggregation_valid")
    Call<List<CheckDuplicateResponse>> checkDuplicate(@Body CheckDuplicateRequest checkDuplicateRequest);

    @POST("ActionLog/ActionlogInsert")
    Call<List<ActionLogResponce>> actionLogApi(@Body ActionLogRequest actionLogRequest);


}