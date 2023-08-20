package com.tranhuutruong.finance.build.responses;

import com.tranhuutruong.finance.build.entities.users.UserInformation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private boolean status;

    private String message;

    private String accessToken;

    private Long expiresIn;

    private String refreshToken;

    private UserInformation userModel;
}
