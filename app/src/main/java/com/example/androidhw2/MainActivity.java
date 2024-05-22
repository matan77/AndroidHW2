package com.example.androidhw2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidhw2.User.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = UserApiClient.getClient();
        UserService service = retrofit.create(UserService.class);
        Call<Users> callAsync = service.getUsers();
        callAsync.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                Users users = response.body();
                assert users != null;
                User user = users.getFirstUser();
                String userFullName = user.getEmail();

            }
            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable throwable) {
            }

        });
}}
