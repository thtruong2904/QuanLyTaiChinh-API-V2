package com.tranhuutruong.finance.build.controllers;


import com.tranhuutruong.finance.build.requests.GoalProgressRequest;
import com.tranhuutruong.finance.build.requests.GoalRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.security.UserPrinciple.UserPrincipal;
import com.tranhuutruong.finance.build.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping(value = "/all")
    public ApiResponse<Object> getAllGoalByUser(UserPrincipal userPrincipal)
    {
        return goalService.getAllGoalByUser(userPrincipal.getUserId());
    }

    @PostMapping(value = "/add")
    public ApiResponse<Object> addGoal(UserPrincipal userPrincipal, @RequestBody GoalRequest goalRequest)
    {
        return goalService.addGoal(userPrincipal.getUserId(), goalRequest);
    }

    @PutMapping(value = "/update/{idGoal}")
    public ApiResponse<Object> updateGoal(UserPrincipal userPrincipal, @PathVariable("idGoal") Long idGoal, @RequestBody GoalRequest goalRequest)
    {
        return goalService.updateGoal(userPrincipal.getUserId(), idGoal, goalRequest);
    }

    @DeleteMapping(value = "/delete/{idGoal}")
    public ApiResponse<Object> deleteGoal(UserPrincipal userPrincipal, @PathVariable("idGoal") Long idGoal)
    {
        return goalService.deleteGoal(userPrincipal.getUserId(), idGoal);
    }

    @PostMapping(value = "/add-deposit/{idGoal}")
    public ApiResponse<Object> addDeposit(UserPrincipal userPrincipal, @PathVariable("idGoal") Long idGoal, @RequestBody GoalProgressRequest goalProgressRequest)
    {
        return goalService.addDeposit(userPrincipal.getUserId(), idGoal, goalProgressRequest);
    }

    @GetMapping(value = "/search")
    public ApiResponse<Object> findByName(UserPrincipal userPrincipal, @RequestParam(name = "name", required = false) String name)
    {
        return goalService.findGoalByName(userPrincipal.getUserId(), name);
    }
}
