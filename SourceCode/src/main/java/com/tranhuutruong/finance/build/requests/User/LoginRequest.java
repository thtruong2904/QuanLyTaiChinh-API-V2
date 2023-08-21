package com.tranhuutruong.finance.build.requests.User;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;

    private String password;
}
