package com.practice.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.spring.service.StoreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MemberController {

	private final StoreService storeService;

	@GetMapping("/tx")
	public String naverPointConvert() {
		return this.storeService.convert();
	}

	@GetMapping("/test")
	public String aa() {
		return "hi";
	}
}
