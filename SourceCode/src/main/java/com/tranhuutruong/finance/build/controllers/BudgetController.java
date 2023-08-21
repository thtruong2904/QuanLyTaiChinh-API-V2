package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.requests.BudgetRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.security.UserPrinciple.UserPrincipal;
import com.tranhuutruong.finance.build.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping(value = "/all")
    public ApiResponse<Object> getAllBudget(UserPrincipal userPrincipal)
    {
        return budgetService.getAllBudgetByUser(userPrincipal.getUserId());
    }

    @PostMapping(value = "/add/{idUserCategory}")
    public ApiResponse<Object> addBudget(UserPrincipal userPrincipal, @PathVariable("idUserCategory") Long idUserCategory, @RequestBody BudgetRequest budgetRequest)
    {
        return budgetService.addBudget(userPrincipal.getUserId(), idUserCategory, budgetRequest);
    }

    @PutMapping(value = "/update/{idBudget}")
    public ApiResponse<Object> updateBudget(UserPrincipal userPrincipal, @PathVariable("idBudget") Long idBudget, @RequestBody BudgetRequest budgetRequest)
    {
        return budgetService.updateBudget(idBudget, userPrincipal.getUserId(), budgetRequest);
    }

    @DeleteMapping(value = "/delete/{idBudget}")
    public ApiResponse<Object> deleteBudget(UserPrincipal userPrincipal, @PathVariable("idBudget") Long idBudget)
    {
        return budgetService.deleteBudget(idBudget, userPrincipal.getUserId());
    }

    @GetMapping(value = "/search")
    public ApiResponse<Object> findByName(UserPrincipal userPrincipal, @RequestParam(name = "user-category-name", required = false) String name)
    {
        return budgetService.findBudgetByName(userPrincipal.getUserId(), name);
    }
}
