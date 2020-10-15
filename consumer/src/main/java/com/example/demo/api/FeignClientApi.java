package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("demo-dubbo-provider")
public interface FeignClientApi {
    /**
     * hello
     *
     * @param name name
     * @see com.example.demo.controller.ProviderController#hello(String)
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String hello(@RequestParam(value = "name") String name);

    /**
     * user
     *
     * @param user user
     * @see com.example.demo.controller.ProviderController#user(UserDTO)
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    String user(UserDTO user);
}
