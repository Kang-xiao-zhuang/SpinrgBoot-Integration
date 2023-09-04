package com.zhuang.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Secured({"ROLE_ADMIN", "ROLE_PRODUCT"})
    @RequestMapping("/info")
    public String info() {
        return "Productr Controller ...";
    }
}