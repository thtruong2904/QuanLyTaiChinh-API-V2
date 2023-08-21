package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.requests.CategoryTypeRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;

public interface CategoryTypeService {
    public ApiResponse<Object> getAllCategory();

    public ApiResponse<Object> addCategory(CategoryTypeRequest categoryRequest);

    public ApiResponse<Object> updateCategory(Long id, CategoryTypeRequest categoryRequest);

    public ApiResponse<Object> deleteCategory(Long id);
}
