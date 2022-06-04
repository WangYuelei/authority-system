package com.wyl.controller;


import com.wyl.service.UserService;
import com.wyl.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Resource
    private UserService userService;

    /*
     * 查詢所有用戶(測試方法)
     * */
    @GetMapping("/listAll")
    public Result listAll() {
        return Result.ok(userService.list());
    }
}

