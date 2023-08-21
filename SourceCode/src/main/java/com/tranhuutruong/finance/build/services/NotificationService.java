package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.responses.ApiResponse;

public interface NotificationService {

    public ApiResponse<Object> getAllNotification(Long idUser);
}
