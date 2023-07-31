package com.selflearning.tw.springSecurity.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 設定使用者的權限進入點，也就是說使用者要使用，一定要先經過登入審核的動作
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("error", "請先登入才能進行此操作");
        String error = mapper.writeValueAsString(map);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(error);
        writer.flush();
        writer.close();
    }
}
