package com.bjsasc.orderservice.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.bjsasc.orderservice.entity.OrderEntity;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {
	@RequestMapping(value = "/config/refresh")
	@ResponseBody
	public String refresh() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
		ResponseEntity<String> stringResponseEntity = restTemplate
				.postForEntity("http://localhost:8763/actuator/refresh", requestEntity, String.class);
		return stringResponseEntity.getStatusCode().value() + ":" + stringResponseEntity.getBody();
	}

	@RequestMapping(value = "/get")
	@ResponseBody
	public OrderEntity get() {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(8L);
		orderEntity.setCustomerName("test测试");
		orderEntity.setDate(LocalDateTime.now());
		return orderEntity;
	}

	@RequestMapping(value = "/getById")
	@ResponseBody
	public OrderEntity get(Long id) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(id);
		orderEntity.setCustomerName("test测试");
		orderEntity.setDate(LocalDateTime.now());
		return orderEntity;
	}

	@RequestMapping(value = "/normal")
	@ResponseBody
	public String normal() {
		return "1";
	}

	@RequestMapping(value = "/abnormal")
	@ResponseBody
	public String abNormal() throws InterruptedException {
		log.info("abnormal-start:{}", LocalDateTime.now());
		Thread.sleep(8000);
		log.info("abnormal-end:{}", LocalDateTime.now());
		return "2";
	}

	@RequestMapping(value = "/abnormalWithCallBack")
	@ResponseBody
	public String abnormalWithCallBack() throws InterruptedException {
		log.info("abnormalWithCallBack-start:{}", LocalDateTime.now());
		Thread.sleep(6000);
		log.info("abnormalWithCallBack-end:{}", LocalDateTime.now());
		return "3";
	}

}
