package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.requests.UserCategoryRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.security.UserPrinciple.UserPrincipal;
import com.tranhuutruong.finance.build.services.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user-category")
public class UserCategoryController {

    @Autowired
    private UserCategoryService userCategoryService;

    @GetMapping(value = "/all")
    public ApiResponse<Object> getAllUserCategoryByUser(UserPrincipal userPrincipal)
    {
        return userCategoryService.getAllUserCategoryByUser(userPrincipal.getUserId());
    }

    @GetMapping(value = "/income")
    public ApiResponse<Object> getAllIncomeUserCategory(UserPrincipal userPrincipal)
    {
        return userCategoryService.getAllCategoryIncomeByUser(userPrincipal.getUserId());
    }

    @GetMapping(value = "/expense")
    public ApiResponse<Object> getAllExpenseUserCategory(UserPrincipal userPrincipal)
    {
        return userCategoryService.getAllCategoryExpenseByUser(userPrincipal.getUserId());
    }

    @PostMapping(value = "/add/{idCategory}")
    public ApiResponse<Object> addUserCategory(UserPrincipal userPrincipal, @PathVariable("idCategory") Long idCategory, @RequestBody UserCategoryRequest userCategoryRequest){
        return userCategoryService.addUserCategory(userPrincipal.getUserId(), idCategory, userCategoryRequest);
    }

    @PutMapping(value = "/update/{idUserCategory}/{idCategory}")
    public ApiResponse<Object> updateUserCategory(UserPrincipal userPrincipal,
                                                  @PathVariable("idUserCategory") Long idUserCategory,
                                                  @PathVariable("idCategory") Long idCategory,
                                                  @RequestBody UserCategoryRequest userCategoryRequest){
        return userCategoryService.updateUserCategory(idUserCategory, userPrincipal.getUserId(), idCategory, userCategoryRequest);
    }

    @DeleteMapping(value = "/delete/{idUserCategory}")
    public ApiResponse<Object> deleteUserCategory(UserPrincipal userPrincipal, @PathVariable("idUserCategory") Long idUserCategory)
    {
        return userCategoryService.deleteUserCategory(idUserCategory, userPrincipal.getUserId());
    }

    @GetMapping(value = "/search")
    public ApiResponse<Object> findByName(UserPrincipal userPrincipal, @RequestParam(name = "name", required = false) String name)
    {
        return userCategoryService.findUserCategoryByName(userPrincipal.getUserId(), name);
    }
}
