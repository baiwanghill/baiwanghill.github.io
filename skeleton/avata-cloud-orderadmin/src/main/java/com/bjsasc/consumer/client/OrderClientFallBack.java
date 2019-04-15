package com.bjsasc.consumer.client;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.bjsasc.consumer.entity.OrderEntity;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderClientFallBack implements FallbackFactory<OrderClient> {
 
 
    @Override
    public OrderClient create(Throwable throwable) {
        return new OrderClient() {
 
            @Override
            public OrderEntity getOne(LocalDateTime dateTime) {
                return null;
            }
 
            @Override
            public OrderEntity getOne(Long id) {
                return null;
            }
 
            @Override
            public String normal() {
                return null;
            }
 
            @Override
            public String abnormal() {
                return null;
            }
 
            @Override
            public String abnormalWithCallBack() {
                log.info("abnormalWithCallBack - fail");
                return "abnormalWithCallBack - fail";
            }
        };
 
    }
}