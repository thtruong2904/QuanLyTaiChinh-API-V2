package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.requests.CategoryTypeRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.services.CategoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryTypeController {

    @Autowired
    private CategoryTypeService categoryService;

    @GetMapping(value = "/all")
    public ApiResponse<Object> getAllCategory()
    {
        return categoryService.getAllCategory();
    }

    @PostMapping(value = "/add")
    public ApiResponse<Object> addCategory(@RequestBody CategoryTypeRequest categoryRequest)
    {
        return categoryService.addCategory(categoryRequest);
    }

    @PutMapping(value = "/update/{id}")
    public ApiResponse<Object> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryTypeRequest categoryRequest)
    {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<Object> deleteCategory(@PathVariable("id") Long id)
    {
        return categoryService.deleteCategory(id);
    }
}
