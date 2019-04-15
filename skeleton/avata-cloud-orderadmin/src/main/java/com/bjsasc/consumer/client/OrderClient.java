package com.bjsasc.consumer.client;

import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bjsasc.consumer.config.OAuth2FeignRequestInterceptor;
import com.bjsasc.consumer.entity.OrderEntity;

@FeignClient(name="service-order",url="http://localhost:8763/order",fallbackFactory = OrderClientFallBack.class,configuration=OAuth2FeignRequestInterceptor.class)
public interface OrderClient {
 
    @RequestMapping(method = RequestMethod.GET,value = "/get")
    OrderEntity getOne(@RequestParam(name="dateTime") LocalDateTime dateTime);
 
    @RequestMapping(method = RequestMethod.GET,value = "/getById")
    OrderEntity getOne(@RequestParam(name="id")Long id);
 
    @RequestMapping(method = RequestMethod.GET,value = "/normal")
    String normal();
 
    @RequestMapping(method = RequestMethod.GET,value = "/abnormal")
    String abnormal();
 
    @RequestMapping(method = RequestMethod.GET,value = "/abnormalWithCallBack")
    String abnormalWithCallBack();
} 
