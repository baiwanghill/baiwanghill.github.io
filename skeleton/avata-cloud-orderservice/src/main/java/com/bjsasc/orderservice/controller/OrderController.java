package com.bjsasc.orderservice.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bjsasc.orderservice.entity.OrderEntity;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
	@Autowired
	Environment environment;

	@RequestMapping(value = "/config/version")
	public String version() {
		String version = environment.getProperty("version");
		return version;
	}

	@RequestMapping(value = "/config/testString")
	public String testString() {
		String version = environment.getProperty("test-string");
		return version;
	}

	@RequestMapping(value = "/config/refresh")
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
	public OrderEntity get() {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(8L);
		orderEntity.setCustomerName("test测试");
		orderEntity.setDate(LocalDateTime.now());
		return orderEntity;
	}

	@RequestMapping(value = "/getById")
	public OrderEntity get(Long id) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(id);
		orderEntity.setCustomerName("test测试");
		orderEntity.setDate(LocalDateTime.now());
		return orderEntity;
	}

	@RequestMapping(value = "/normal")
	public String normal() {

		return "1";
	}

	@RequestMapping(value = "/abnormal")
	public String abNormal() throws InterruptedException {
		log.info("abnormal-start:{}", LocalDateTime.now());
		Thread.sleep(8000);
		log.info("abnormal-end:{}", LocalDateTime.now());
		return "2";
	}

	@RequestMapping(value = "/abnormalWithCallBack")
	public String abnormalWithCallBack() throws InterruptedException {
		log.info("abnormalWithCallBack-start:{}", LocalDateTime.now());
		Thread.sleep(6000);
		log.info("abnormalWithCallBack-end:{}", LocalDateTime.now());
		return "3";
	}

}
