package com.tranhuutruong.finance.build.security.UserPrinciple;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserPrincipal {
    public Long getUserId()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Long.parseLong(userPrinciple.getUserId());
    }
}
