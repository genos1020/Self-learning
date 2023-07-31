package com.selflearning.tw.springSecurity.configs;

import com.selflearning.tw.springSecurity.bpo.intf.UserService;
import com.selflearning.tw.springSecurity.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class initUser {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @PostConstruct
    private void init(){
        log.info("############# init user #############");
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserName("JN");
        user.setPassword(passwordEncoder.encode("qwe"));
        user.setRole("ROLE_ADMIN");
        user.setCreateTime(LocalDateTime.now());
        userService.createUser(user);
        log.info("############# init user completed #############");
    }
}
