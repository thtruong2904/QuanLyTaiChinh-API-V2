package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.requests.GoalProgressRequest;
import com.tranhuutruong.finance.build.requests.GoalRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;

public interface GoalService {
    public ApiResponse<Object> getAllGoalByUser(Long idUser);

    public ApiResponse<Object> addGoal(Long idUser, GoalRequest goalRequest);

    public ApiResponse<Object> updateGoal(Long idUser, Long idGoal, GoalRequest goalRequest);

    public ApiResponse<Object> deleteGoal(Long idUser, Long idGoal);

    public ApiResponse<Object> addDeposit(Long idUser, Long idGoal, GoalProgressRequest goalProgressRequest);

    public ApiResponse<Object> findGoalByName(Long idUser, String name);
}
