package com.bjsasc.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsasc.consumer.client.OrderClient;
import com.bjsasc.consumer.entity.OrderEntity;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/remote")
public class RemoteOrder {
	@Autowired
	private OrderClient client;

	@RequestMapping("/get")
	@ResponseBody
	public OrderEntity get() {
		log.error("true:");
		return client.getOne(10L);
	}
}
