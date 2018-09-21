package com.yang.gateway.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @RequestMapping(value = "/fallback")
    public String fallBack() {
        return "连接超时，请重试……";
    }
}
