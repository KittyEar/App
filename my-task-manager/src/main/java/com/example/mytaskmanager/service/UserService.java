package com.example.service;

import com.example.data.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    // 模拟用户数据存储
    private static Map<String, User> users = new HashMap<>();

    // 添加用户
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // 根据用户名查找用户
    public User getUserByUsername(String username) {
        return users.get(username);
    }

    // 检查用户是否存在
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}
