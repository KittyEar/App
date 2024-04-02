package com.example.data;

public class UserDto{
    private String username;
    private String password;

    // 构造函数、getter和setter方法

    public UserDto() {
        // 空构造函数
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
