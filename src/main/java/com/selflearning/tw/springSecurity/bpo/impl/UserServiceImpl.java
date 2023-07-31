package com.selflearning.tw.springSecurity.bpo.impl;

import com.selflearning.tw.springSecurity.bpo.intf.UserService;
import com.selflearning.tw.springSecurity.model.User;
import com.selflearning.tw.springSecurity.repo.UserRepository;
import com.selflearning.tw.springSecurity.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserVO getUserByUsername(String username) {
        UserVO userVO = new UserVO();

        try{
            Optional<User> userOptional = userRepo.findByUserName(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                userVO.setUsername(user.getUserName());
                userVO.setPassword(user.getPassword());
                userVO.setRole(user.getRole());
                userVO.setLastLoginTime(user.getLastLoginTime());
            }

        }catch(Exception e){
            log.error(e.getMessage(), e);
        }

        return userVO;
    }

    @Override
    public void createUser(User user) {
        try{
            String userName = user.getUserName();
            Optional<User> userOptional = userRepo.findByUserName(userName);
            if(userOptional.isPresent()){
                log.info("User name {} is existed.", userName);
            }else{
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(user);
                log.info("User {} is created successfully.", userName);
            }
        }catch(Exception e){
          log.error(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {

        try{
            Optional<User> userOptional = userRepo.findById(user.getUserId());
            if(userOptional.isPresent()) {
                User entity = userOptional.get();

                entity.setPassword(passwordEncoder.encode(user.getPassword()));
                entity.setRole(user.getRole());
                entity.setUpdateTime(LocalDateTime.now());

                log.info("Update user success.");
                return true;
            }else{
                log.info("User Not Found.");
            }

        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
