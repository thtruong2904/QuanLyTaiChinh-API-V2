package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.Notification;
import com.tranhuutruong.finance.build.repositories.NotificationRepository;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public ApiResponse<Object> getAllNotification(Long idUser)
    {
        List<Notification> listNotification = notificationRepository.getAllNotification(idUser);
        if(listNotification == null) {
            return ApiResponse.builder().status(101).message("Chưa có thông báo").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách thông báo").data(listNotification).build();
    }
}
