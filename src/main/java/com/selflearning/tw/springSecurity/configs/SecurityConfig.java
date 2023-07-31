package com.selflearning.tw.springSecurity.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearning.tw.springSecurity.bpo.intf.UserService;
import com.selflearning.tw.springSecurity.model.User;
import com.selflearning.tw.springSecurity.repo.UserRepository;
import com.selflearning.tw.springSecurity.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private ApplicationUserService applicationUserService;

    @Autowired
    private UserRepository userRepo;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
//                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 講解定義能夠存取路由的角色配置
                .authorizeRequests()
                .antMatchers("/api/v1/**")
                .authenticated()
//                .antMatchers("/api/v1/login/admin/**").hasRole("ADMIN")
//                .antMatchers("/api/v1/login/user/**").hasRole("USER")
                .and()
                .formLogin()
                .loginProcessingUrl("/api/sl/v1/login")
                .successHandler((req, resp, auth) -> {
                    String account = auth.getName();
                    System.out.println("login auth: " + auth);
                    System.out.println("login name(account): " + account);

                    // 更新使用者最後登入時間
                    Optional<User> usersOptional = userRepo.findByUserName(account);
                    if(usersOptional.isPresent()){
                        User users = usersOptional.get();
                        users.setLastLoginTime(LocalDateTime.now());
                        userRepo.save(users);
                    }

                    Collection collection = auth.getAuthorities();
                    String authority = collection.iterator().next().toString();
                    HttpSession session = req.getSession();
                    session.setAttribute("logged_in", account);
                    session.setAttribute("user_type", authority);
                    Map<String, String> result = new HashMap<>();
                    result.put("message", "登入成功");
                    result.put("authority", authority);
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    resp.setStatus(200);
                    ObjectMapper om = new ObjectMapper();
                    out.write(om.writeValueAsString(result));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, auth) -> {
                    System.out.println(req);
                    System.out.println(resp);
                    System.out.println(auth);
                    Map<String, String> result = new HashMap<>();
                    result.put("message", "登入失敗");
                    result.put("authority", null);
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    resp.setStatus(200);
                    ObjectMapper om = new ObjectMapper();
                    out.write(om.writeValueAsString(result));
                    out.flush();
                    out.close();
                })
                .passwordParameter("password")
                .usernameParameter("username")
                .permitAll()
                .and()
                // logout的配置
                .logout()
                // 定義登出的路由
                .logoutUrl("/api/sl/v1/logout")
                // 主動將session destroy掉
                .invalidateHttpSession(true)
                // 添加登出成功的handler
                .logoutSuccessHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    resp.setStatus(200);
                    Map<String, String> result = new HashMap<>();
                    result.put("message", "登出成功");
                    ObjectMapper om = new ObjectMapper();
                    out.write(om.writeValueAsString(result));
                    out.flush();
                    out.close();
                })
                .and()
                .cors()
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
//        auth.userDetailsService(applicationUserService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
