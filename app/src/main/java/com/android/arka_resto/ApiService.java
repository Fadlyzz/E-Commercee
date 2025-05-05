package com.android.arka_resto;

import com.android.arka_resto.models.LoginResponse;
import com.android.arka_resto.models.RegisterResponse;
import com.android.arka_resto.models.MenuItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> login(@Body Map<String, String> body);

    @POST("register")
    Call<RegisterResponse> register(@Body Map<String, String> body);

    @GET("makanan")
    Call<List<MenuItem>> getMakanan();

    @GET("minuman")
    Call<List<MenuItem>> getMinuman();
}
