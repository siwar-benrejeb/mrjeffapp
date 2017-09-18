package com.mrjeffapp.customer.client.login;

import com.mrjeffapp.customer.client.login.model.RetroLoginRequest;
import com.mrjeffapp.customer.client.login.model.RetroLoginResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//@FeignClient(name = "retro-login-client", url = "${external-services.retro-login-service.host}")
//public interface RetroLoginClient {
//
//    @RequestMapping(method = POST, value = "/passbackend/checkpassword.php")
//    RetroLoginResponse validateLogin(@RequestHeader("AuthorizationServiceToken") String token, RetroLoginRequest retroLoginRequest);

//}
