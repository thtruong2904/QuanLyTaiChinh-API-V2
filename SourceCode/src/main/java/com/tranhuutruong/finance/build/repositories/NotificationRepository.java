package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.userInformation.id = :idUser")
    List<Notification> getAllNotification(@Param("idUser") Long idUser);
}
