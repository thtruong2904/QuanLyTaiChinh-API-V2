package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.requests.UserCategoryRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;

public interface UserCategoryService {
    public ApiResponse<Object> getAllUserCategoryByUser(Long idUser);

    public ApiResponse<Object> addUserCategory(Long idUser, Long idCategoryType, UserCategoryRequest userCategoryRequest);

    public ApiResponse<Object> updateUserCategory(Long idUserCategory, Long idUser, Long idCategory, UserCategoryRequest userCategoryRequest);

    public ApiResponse<Object> deleteUserCategory(Long idUserCategory, Long idUser);

    public ApiResponse<Object> getAllCategoryIncomeByUser(Long idUser);

    public ApiResponse<Object> getAllCategoryExpenseByUser(Long idUser);

    public ApiResponse<Object> findUserCategoryByName(Long idUser, String name);
}
