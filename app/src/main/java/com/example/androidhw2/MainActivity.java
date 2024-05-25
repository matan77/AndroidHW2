package com.example.androidhw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.androidhw2.UserApi.*;
import com.example.androidhw2.databinding.ActivityMainBinding;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private User curr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        binding.btnNext.setOnClickListener(v -> fetchUser());

        binding.btnAdd.setOnClickListener(v -> {
            if (curr == null){
                Toast.makeText(this, "It is currently not possible to add to the collection", Toast.LENGTH_SHORT).show();            }
            else{
                //todo add to db
            }
        });

        binding.btnViewUsers.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchUser();
    }

    private void fetchUser() {
        toggleButtons(false);

        Retrofit retrofit = UserApiClient.getClient();
        UserService service = retrofit.create(UserService.class);
        Call<Users> getUsersAsync = service.getUsers();

        getUsersAsync.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                Users users = response.body();
                if (users != null && users.getUsers() != null && !users.getUsers().isEmpty()) {
                    curr = users.getUsers().get(0);

                    binding.tvFirstName.setText(curr.getName().getFirst());
                    binding.tvLastName.setText(curr.getName().getLast());
                    binding.tvAge.setText(String.format(Locale.getDefault(), "Age: %d", curr.getDob().getAge()));
                    binding.tvEmail.setText(String.format("Email: %s", curr.getEmail()));
                    binding.tvCity.setText(String.format("City: %s", curr.getLocation().getCity()));
                    binding.tvCountry.setText(String.format("Country: %s", curr.getLocation().getCountry()));

                    Glide.with(binding.getRoot()).load(curr.getPicture().getLarge()).into(binding.imgVProfile);
                } else {
                    curr = null;
                    setErrorUser();
                }

                toggleButtons(true);
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable throwable) {
                setErrorUser();
                toggleButtons(true);
            }
        });
    }

    private void setErrorUser() {
        binding.tvFirstName.setText(R.string.error);
        binding.tvLastName.setText(R.string.error);
        binding.tvAge.setText(String.format("Age: %s", getString(R.string.error)));
        binding.tvEmail.setText(String.format("Email: %s", getString(R.string.error)));
        binding.tvCity.setText(String.format("City: %s", getString(R.string.error)));
        binding.tvCountry.setText(String.format("Country: %s", getString(R.string.error)));
        binding.imgVProfile.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    private void toggleButtons(boolean isEnabled) {
        binding.btnNext.setEnabled(isEnabled);
        binding.btnAdd.setEnabled(isEnabled);
        binding.btnViewUsers.setEnabled(isEnabled);
    }
}