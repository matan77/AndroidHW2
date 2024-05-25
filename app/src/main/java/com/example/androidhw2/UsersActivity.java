package com.example.androidhw2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhw2.UserDb.User;
import com.example.androidhw2.UserDb.UserDatabase;
import com.example.androidhw2.databinding.ActivityMainBinding;
import com.example.androidhw2.databinding.ActivityUsersBinding;

import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private ActivityUsersBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.usersRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL,false));
        binding.usersRecyclerView.setHasFixedSize(true);





        UserDatabase db = UserDatabase.getInstance(this);
        List<User> users = db.userDao().getAll();
        binding.usersRecyclerView.setAdapter(new UsersRecyclerViewAdapter(users));
    }
}