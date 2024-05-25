package com.example.androidhw2;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidhw2.UserDb.User;
import com.example.androidhw2.databinding.UserItemBinding;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private final UserItemBinding binding;

    public UserViewHolder(@NonNull UserItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(User user) {
        Glide.with(binding.getRoot()).load(user.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(binding.imgVProfile);
        binding.tvFirstName.setText(user.firstName);
        binding.tvLastName.setText(user.lastName);
        binding.tvCountry.setText(user.country);
        binding.tvCity.setText(user.city);
    }
}
