package com.example.androidhw2.User;

import retrofit2.http.GET;
import retrofit2.Call;

public interface UserService {
    @GET("/api")
    public Call<Users> getUsers();
    
    
}
