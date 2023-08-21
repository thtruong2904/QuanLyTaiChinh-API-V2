package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.Budget;
import com.tranhuutruong.finance.build.entities.Goal;
import com.tranhuutruong.finance.build.entities.GoalProgress;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import com.tranhuutruong.finance.build.repositories.GoalProgressRepository;
import com.tranhuutruong.finance.build.repositories.GoalRepository;
import com.tranhuutruong.finance.build.repositories.User.UserRepository;
import com.tranhuutruong.finance.build.requests.GoalProgressRequest;
import com.tranhuutruong.finance.build.requests.GoalRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService{

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalProgressRepository goalProgressRepository;

    @Override
    public ApiResponse<Object> getAllGoalByUser(Long idUser)
    {
        Iterable<Goal> goals = goalRepository.getAllByUser(idUser);
        if(!goals.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có mục tiêu được đặt ra").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách mục tiêu").data(goals).build();
    }

    @Override
    public ApiResponse<Object> addGoal(Long idUser, GoalRequest goalRequest)
    {
        Optional<UserInformation> userInformation = userRepository.findById(idUser);
        if(!userInformation.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy người dùng").build();
        }
        Goal goal = goalRepository.findByUserAndNameAndDeadline(idUser, goalRequest.getName(), goalRequest.getDeadline());
        if(goal != null)
        {
            return ApiResponse.builder().status(101).message("Mục tiêu với mốc thời gian đã được đặt ra").build();
        }
        Date currentDate = new Date(System.currentTimeMillis());
        if(currentDate.after(goalRequest.getDeadline()))
        {
            return ApiResponse.builder().status(101).message("Mốc thời gian đặt ra mục tiêu phải lớn hơn ngày hiện tại!").build();
        }
        Goal newGoal = Goal.builder().userInformation(userInformation.get())
                .name(goalRequest.getName()).amount(goalRequest.getAmount())
                .deadline(goalRequest.getDeadline()).status("Chưa hoàn thành").build();
        goalRepository.save(newGoal);
        return ApiResponse.builder().status(200).message("Thêm mục tiêu thành công").build();
    }

    @Override
    public ApiResponse<Object> updateGoal(Long idUser, Long idGoal, GoalRequest goalRequest)
    {
        Goal goal = goalRepository.findByUserAndGoalId(idUser, idGoal);
        if(goal == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy mục tiêu").build();
        }
        Goal goalCheck = goalRepository.findByUserAndNameAndDeadline(idUser, goalRequest.getName(), goalRequest.getDeadline());
        if(goalCheck != null)
        {
            return ApiResponse.builder().status(101).message("Mục tiêu với mốc thời gian đã được đặt ra").build();
        }
        Date currentDate = new Date(System.currentTimeMillis());
        if(currentDate.after(goalRequest.getDeadline()))
        {
            return ApiResponse.builder().status(101).message("Mốc thời gian đặt ra mục tiêu phải lớn hơn ngày hiện tại!").build();
        }
        goal.setName(goalRequest.getName());
        goal.setDeadline(goalRequest.getDeadline());
        goal.setAmount(goalRequest.getAmount());
        goalRepository.save(goal);
        return ApiResponse.builder().status(200).message("Sửa mục tiêu thành công").build();
    }

    @Override
    public ApiResponse<Object> deleteGoal(Long idUser, Long idGoal)
    {
        Goal goal = goalRepository.findByUserAndGoalId(idUser, idGoal);
        if(goal == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy mục tiêu").build();
        }
        List<GoalProgress> goalProgressList = goalProgressRepository.findAllByGoalId(goal.getId());
        for(GoalProgress goalProgress : goalProgressList)
        {
            goalProgressRepository.delete(goalProgress);
        }
        goalRepository.delete(goal);
        return ApiResponse.builder().status(200).message("Xóa mục tiêu thành công").build();
    }

    @Override
    public ApiResponse<Object> addDeposit(Long idUser, Long idGoal, GoalProgressRequest goalProgressRequest)
    {
        Goal goal = goalRepository.findByUserAndGoalId(idUser, idGoal);
        if(goal == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy mục tiêu").build();
        }
        Date current = new Date(System.currentTimeMillis());
        GoalProgress newGoalProgress = GoalProgress.builder().goal(goal).deposit(goalProgressRequest.getDeposit()).date(current).build();
        goalProgressRepository.save(newGoalProgress);
        return ApiResponse.builder().status(200).message("Thêm tiền cho mục tiêu thành công").build();
    }

    @Override
    public ApiResponse<Object> findGoalByName(Long idUser, String name)
    {
        Iterable<Goal> listGoal = goalRepository.findByName(idUser, name);
        if(!listGoal.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có mục tiêu cần tìm kiếm").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách mục tiêu tìm kiếm").data(listGoal).build();
    }
}
