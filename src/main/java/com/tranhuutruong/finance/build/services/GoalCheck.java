package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.Goal;
import com.tranhuutruong.finance.build.entities.Notification;
import com.tranhuutruong.finance.build.repositories.GoalRepository;
import com.tranhuutruong.finance.build.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class GoalCheck {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Scheduled(cron = "0 0 * * * *") // tự động chạy lúc 0h hàng ngày (cron = "0 0 * * * *")
    public void checkGoalDeadline()
    {
        LocalDate currentDate = LocalDate.now();
        List<Goal> allGoal = goalRepository.findAll();
        for(Goal goal : allGoal)
        {
            if(!goal.getStatus().equals("Hoàn thành"))
            {
                LocalDate deadline = goal.getDeadline().toLocalDate();
                Long amount = goal.getAmount();
                Long totalDeposit = goal.getTotalDeposit();
                if(currentDate.isAfter(deadline))
                {
                    if(amount > totalDeposit)
                    {
                        Notification notification = new Notification();
                        notification.setUserInformation(goal.getUserInformation());
                        notification.setName("Mục tiêu đã quá hạn");
                        notification.setDetail("Mục tiêu " + goal.getName() + " của bạn sắp hết hạn");
                        Date currDateSql = Date.valueOf(currentDate);
                        notification.setDate(currDateSql);
                        notification.setStatus("Chưa đọc");
                        notificationRepository.save(notification);

                        goal.setStatus("Đã quá hạn");
                        goalRepository.save(goal);
                    }
                }
                else if(currentDate.isEqual(deadline))
                {
                    if(totalDeposit >= amount)
                    {
                        Notification notification = new Notification();
                        notification.setUserInformation(goal.getUserInformation());
                        notification.setName("Mục tiêu đã hoàn thành");
                        notification.setDetail("Mục tiêu " + goal.getName() + " của bạn đã hoàn thành");
                        Date currDateSql = Date.valueOf(currentDate);
                        notification.setDate(currDateSql);
                        notification.setStatus("Chưa đọc");
                        notificationRepository.save(notification);

                        goal.setStatus("Hoàn thành");
                        goalRepository.save(goal);
                    }
                }
                else if(currentDate.isBefore(deadline))
                {
                    Long dayBetween = ChronoUnit.DAYS.between(currentDate, deadline);
                    if(dayBetween == 3)
                    {
                        if(totalDeposit < amount)
                        {
                            Notification notification = new Notification();
                            notification.setUserInformation(goal.getUserInformation());
                            notification.setName("Mục tiêu sắp hết hạn");
                            notification.setDetail("Mục tiêu " + goal.getName() + " của bạn sắp hết hạn");
                            Date currDateSql = Date.valueOf(currentDate);
                            notification.setDate(currDateSql);
                            notification.setStatus("Chưa đọc");
                            notificationRepository.save(notification);
                        }
                    }
                    if(totalDeposit >= amount)
                    {
                        Notification notification = new Notification();
                        notification.setUserInformation(goal.getUserInformation());
                        notification.setName("Mục tiêu đã hoàn thành");
                        notification.setDetail("Mục tiêu " + goal.getName() + " của bạn đã hoàn thành");
                        Date currDateSql = Date.valueOf(currentDate);
                        notification.setDate(currDateSql);
                        notification.setStatus("Chưa đọc");
                        notificationRepository.save(notification);

                        goal.setStatus("Hoàn thành");
                        goalRepository.save(goal);
                    }
                }
            }
        }
    }
}
