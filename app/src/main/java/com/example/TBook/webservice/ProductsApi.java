package com.example.TBook.webservice;

import com.example.TBook.entity.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductsApi {
    String BASE_URL="https://run.mocky.io/";
    @GET("v3/6cff8544-b482-4328-a96e-fb96b1162389")
    Call<ArrayList<Products>> getResult();

}

/*
v3/3f029edf-2de1-4d97-9255-c6851abcd43e
*/
