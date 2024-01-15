package com.practice.spring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String name;

	private int money;

	public void minusMoney(int subtractMoney) {
		this.money -= subtractMoney;
	}
}
