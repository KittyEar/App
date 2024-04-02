package com.example.controller;

import com.example.data.User;
import com.example.data.UserDto;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController("/")
public class AuthController {
    @Autowired
    private UserService userService;
    @CrossOrigin(origins = "*") // 允许所有域
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        // 检查用户名是否已存在
        if (userService.userExists(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists.");
        }

        // 创建用户对象并保存
        User user = new User(userDto.getUsername(), userDto.getPassword());
        userService.addUser(user);

        return ResponseEntity.ok("User registered successfully.");
    }
    @ResponseBody
    @CrossOrigin(origins = "*") // 允许所有域
    @GetMapping("/login")
    public String login() {
        // 检查用户是否存在
//        User user = userService.getUserByUsername(userDto.getUsername());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid username or password.");
//        }
//
//        // 检查密码是否匹配
//        if (!user.getPassword().equals(userDto.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid username or password.");
//        }
//
//        return ResponseEntity.ok("Login successful.");
        return "hello";
    }
}
