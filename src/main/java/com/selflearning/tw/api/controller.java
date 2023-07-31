package com.selflearning.tw.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/others/MockMlprDispatch")
public class controller {

    @GetMapping("/request")
    public String returnNull(){
        System.out.println("i guarantee i got ya");
        return null;
    }
}
