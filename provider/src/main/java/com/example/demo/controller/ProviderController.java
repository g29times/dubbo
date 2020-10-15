package com.example.demo.controller;

import com.example.demo.api.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * Rest Api
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/15 14:11
 * @see Object
 * @since 1.0
 */
@RestController
public class ProviderController {

    @GetMapping("/hello")
    public String hello(String name) {
        System.out.println("Rest//hello: " + name);
        return "Name: " + name;
    }

    @GetMapping("/user")
    public String user(@RequestBody UserDTO user) {
        System.out.println("Rest//user: " + user);
        return "User: " + user;
    }

}
