package com.tranhuutruong.finance.build.requests.User;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String password;

    private String newPassword;
}
