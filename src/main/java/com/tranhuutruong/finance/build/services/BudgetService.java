package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.requests.BudgetRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;

public interface BudgetService {
    public ApiResponse<Object> getAllBudgetByUser(Long idUser);
    public ApiResponse<Object> addBudget(Long userId, Long idUserCategory, BudgetRequest budgetRequest);
    public ApiResponse<Object> updateBudget(Long idBudget, Long idUser, BudgetRequest budgetRequest);
    public ApiResponse<Object> deleteBudget(Long idBudget, Long idUser);

    public ApiResponse<Object> findBudgetByName(Long idUser, String name);
}
