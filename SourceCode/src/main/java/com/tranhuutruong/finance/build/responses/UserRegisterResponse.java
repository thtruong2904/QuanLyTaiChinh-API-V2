package com.tranhuutruong.finance.build.responses;

import com.tranhuutruong.finance.build.entities.users.UserInformation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponse {
    private boolean status;

    private String message;

    private UserInformation userModel;
}
