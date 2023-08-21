package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.entities.users.UserInformation;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/all")
    public Iterable<UserInformation> getAllUser()
    {
        return userService.getAllUser();
    }

    @PutMapping(value = "/delete/{idUser}")
    public ApiResponse<Object> deleteUser(@PathVariable("idUser") Long idUser)
    {
        return userService.deleteUser(idUser);
    }

    @PutMapping(value = "/unlock/{idUser}")
    public ApiResponse<Object> unlockUser(@PathVariable("idUser") Long idUser)
    {
        return userService.unlockUser(idUser);
    }

}
