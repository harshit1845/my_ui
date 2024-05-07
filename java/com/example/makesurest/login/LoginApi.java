package com.example.makesurest.login;

import com.example.makesurest.model.LoginResponce;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class LoginApi {
    private static final String BASE_URL = "http://sundaramtech.com/ST_Makesure_API/";

    private static LoginApi instance;

    private LoginApi() {
    }

    public static synchronized LoginApi getInstance() {
        if (instance == null) {
            instance = new LoginApi();
        }
        return instance;
    }

    public interface LoginService {
        @GET("Login/CheckLogin")
        Call<LoginResponce> login(@Query("username") String username, @Query("password") String password,@Query("flag") int flag);
    }

    public Call<LoginResponce> login(String username, String password,int flag) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService service = retrofit.create(LoginService.class);
        return service.login(username, password,flag);
    }
}