package com.selflearning.tw.springSecurity.configs;

import com.selflearning.tw.springSecurity.bpo.intf.UserService;
import com.selflearning.tw.springSecurity.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username:" + username);
        String password = username;
        String role = username;

        try{
            UserVO userVO = userService.getUserByUsername(username);
            log.info("Login user's username: {}", userVO.getUsername());
            password = userVO.getPassword();
            role = userVO.getRole();

            log.info("{}'s role is {}", username, role);
//            return new User(account, password, Collections.emptyList());

        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
        String ROLE = role.toUpperCase();
        ApplicationUserRole applicationUserRole = null;
        for(ApplicationUserRole r : ApplicationUserRole.values()){
            if(r.getPermissions().equals(ROLE)){
                applicationUserRole = r;
            }
        }

//        return new ApplicationUser(
//                account,
//                password,
//                applicationUserRole.getGrantedAuthorities(),
//                true,
//                true,
//                true,
//                true
//        );
        return new User(
                username,
                password,
                applicationUserRole.getGrantedAuthorities()
        );
    }
}
