package com.example.kanban;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/com/example/kanban/api/data")
    Call<String> getData();
}
