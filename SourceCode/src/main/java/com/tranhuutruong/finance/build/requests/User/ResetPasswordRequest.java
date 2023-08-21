package com.tranhuutruong.finance.build.requests.User;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
