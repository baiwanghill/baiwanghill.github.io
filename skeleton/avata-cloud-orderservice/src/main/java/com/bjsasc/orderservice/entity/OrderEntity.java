package com.bjsasc.orderservice.entity;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class OrderEntity {
	private long id;
	private String CustomerName;
	private LocalDateTime date;
	
}
