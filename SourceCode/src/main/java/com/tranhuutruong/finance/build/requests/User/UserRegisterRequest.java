package com.tranhuutruong.finance.build.requests.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UserRegisterRequest {
    private String userName;

    private String passWord;

    private String firstname;

    private String lastname;

    private String email;

    private String roleName;
}
