package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.CategoryType;
import com.tranhuutruong.finance.build.entities.UserCategory;
import com.tranhuutruong.finance.build.repositories.CategoryTypeRepository;
import com.tranhuutruong.finance.build.repositories.UserCategoryRepository;
import com.tranhuutruong.finance.build.requests.CategoryTypeRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryTypeServiceImpl implements CategoryTypeService {

    @Autowired
    private CategoryTypeRepository categoryRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Override
    public ApiResponse<Object> getAllCategory()
    {
        List<CategoryType> categoryList = categoryRepository.findAll();
        return ApiResponse.builder().status(200).message("Danh sách danh mục").data(categoryList).build();
    }

    @Override
    public ApiResponse<Object> addCategory(CategoryTypeRequest categoryRequest)
    {
        CategoryType category = categoryRepository.findByName(categoryRequest.getName());
        if(category != null)
        {
            return ApiResponse.builder().status(101).message("Loại danh mục đã tồn tại").build();
        }
        category = CategoryType.builder().name(categoryRequest.getName()).build();
        categoryRepository.save(category);
        return ApiResponse.builder().status(200).message("Thêm loại danh mục thành công").data(category).build();
    }

    @Override
    public ApiResponse<Object> updateCategory(Long id, CategoryTypeRequest categoryRequest)
    {
        Optional<CategoryType> category = categoryRepository.findById(id);
        if(!category.isPresent())
        {
            return ApiResponse.builder().status(101).message("Không tìm thấy danh mục").build();
        }
        category.get().setName(categoryRequest.getName());
        categoryRepository.save(category.get());
        return ApiResponse.builder().status(200).message("Sửa loại danh mục thành công").build();
    }

    @Override
    public ApiResponse<Object> deleteCategory(Long id)
    {
        Optional<CategoryType> category = categoryRepository.findById(id);
        if(!category.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy loại danh mục").build();
        }
        if(userCategoryRepository.findByCategoryType(category.get().getId()))
        {
            return ApiResponse.builder().status(101).message("Danh mục đang đang sử dụng. Không được xóa").build();
        }
        categoryRepository.delete(category.get());
        return ApiResponse.builder().status(200).message("Xóa loại danh mục thành công").build();
    }
}
