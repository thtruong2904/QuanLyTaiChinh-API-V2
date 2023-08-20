package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.users.UserInformation;
import com.tranhuutruong.finance.build.requests.User.*;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.responses.LoginResponse;
import com.tranhuutruong.finance.build.responses.UserRegisterResponse;

public interface UserService {

    public LoginResponse refreshToken(String refreshToken);
    public LoginResponse login(LoginRequest loginRequest);
    public UserRegisterResponse register(UserRegisterRequest registerRequest);
    public ApiResponse<Object> changePassword(String username, ChangePasswordRequest changePasswordRequest);
    public ApiResponse<Object> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    public ApiResponse<Object> checkVerifyCode(VerifyCodeCheckRequest verifyCode);
    public ApiResponse<Object> resetPassword(ResetPasswordRequest resetPasswordRequest);
    public Iterable<UserInformation> getAllUser();
    public ApiResponse<Object> getProfile(Long idUser);
    public ApiResponse<Object> updateProfile(Long idUser, UserUpdateRequest userUpdateRequest);

    public ApiResponse<Object> deleteUser(Long idUser);

    public ApiResponse<Object> unlockUser(Long idUser);
}
