package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.requests.User.*;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.responses.LoginResponse;
import com.tranhuutruong.finance.build.responses.UserRegisterResponse;
import com.tranhuutruong.finance.build.security.UserPrinciple.UserPrincipal;
import com.tranhuutruong.finance.build.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/register")
    public UserRegisterResponse registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        UserRegisterResponse userRegisterResponse = userService.register(userRegisterRequest);
        return userRegisterResponse;
    }

    @PostMapping(value = "/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse = userService.login(loginRequest);
        return loginResponse;
    }

    @GetMapping(value = "/refresh/{refresh-token}")
    public LoginResponse refreshToken(@PathVariable("refresh-token") String refreshToken)
    {
        return userService.refreshToken(refreshToken);
    }

    @PutMapping(value = "/change-password")
    public ApiResponse<Object> changePass(Principal principal, @RequestBody ChangePasswordRequest changePasswordRequest)
    {
        return userService.changePassword(principal.getName(), changePasswordRequest);
    }

    @PostMapping(value = "/forgot-password")
    public ApiResponse<Object> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest)
    {
        return userService.forgotPassword(forgotPasswordRequest);
    }

    @PostMapping(value = "/verify-code")
    public ApiResponse<Object> checkVerifyCode(@RequestBody VerifyCodeCheckRequest verifyCodeCheckRequest)
    {
        return userService.checkVerifyCode(verifyCodeCheckRequest);
    }

    @PostMapping(value = "/reset-password")
    public ApiResponse<Object> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest)
    {
        return userService.resetPassword(resetPasswordRequest);
    }

    @GetMapping(value = "/profile")
    public ApiResponse<Object> getProfile(UserPrincipal userPrincipal)
    {
        return userService.getProfile(userPrincipal.getUserId());
    }

    @PutMapping(value = "/profile/update")
    public ApiResponse<Object> updateProfile(UserPrincipal userPrincipal, @RequestBody UserUpdateRequest userUpdateRequest)
    {
        return userService.updateProfile(userPrincipal.getUserId(), userUpdateRequest);
    }
}
