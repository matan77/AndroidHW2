package com.example.androidhw2.UserApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {
    @SerializedName("results")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getFirstUser(){
        return users.get(0);
    }
}
