package com.tranhuutruong.finance.build.requests.User;

import lombok.Data;

@Data
public class VerifyCodeCheckRequest {
    private String emailRequest;

    private String code;
}
