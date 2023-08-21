package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.security.UserPrinciple.UserPrincipal;
import com.tranhuutruong.finance.build.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/all")
    public ApiResponse<Object> getAllTransaction(UserPrincipal userPrincipal)
    {
        return notificationService.getAllNotification(userPrincipal.getUserId());
    }
}
