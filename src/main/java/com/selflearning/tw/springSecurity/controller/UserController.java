package com.selflearning.tw.springSecurity.controller;

import com.selflearning.tw.springSecurity.bpo.intf.UserService;
import com.selflearning.tw.springSecurity.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sl/v1")
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_ENGINEER')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userService.getUsers();
    }

}
