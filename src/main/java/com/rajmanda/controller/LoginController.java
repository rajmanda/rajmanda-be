package com.rajmanda.controller;

import com.rajmanda.helper.LoginHelper;
import com.rajmanda.model.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    LoginHelper loginHelper;

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }

    @PostMapping("/register")
    public String register(@RequestParam("firstName") String firstName, @RequestParam("password") String password, @RequestParam("lastName") String lastName, @RequestParam("email") String email ) {
        loginHelper.registerUser(firstName, lastName, email, password);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestParam String username, @RequestParam String password) {
        return loginHelper.login(username, password);
    }

    @GetMapping("/grantcode")
    public LoginResponseDTO grantCode(@RequestParam("code") String code, @RequestParam("scope") String scope, @RequestParam("authuser") String authUser, @RequestParam("prompt") String prompt) {
        return loginHelper.processGrantCode(code);
    }

    @GetMapping("/accountverification")
    public String verifyAccount(@RequestParam("id") String id) {
        return loginHelper.verifyAccount(id);
    }

    @PostMapping("/generateresetpasswordlink")
    public String generateResetPasswordLink(@RequestParam String email) {
        return loginHelper.initiateResetPasswordLink(email);
    }

    @PutMapping("/changepassword")
    public String changePassword(@RequestParam("token") String token, @RequestParam("new_password") String newPassword) {
        return loginHelper.changePasswordWithToken(token, newPassword);
    }

    @PutMapping("/logoutuser")
    public String logoutUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return loginHelper.logout(email);
    }

    @PostMapping("/refreshaccesstoken")
    public LoginResponseDTO refreshAccessToken(@RequestParam("refresh_token") String refreshToken) {
        return loginHelper.refreshAccessToken(refreshToken);
    }
}
