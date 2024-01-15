package com.practice.spring.service;

import org.springframework.stereotype.Service;

import com.practice.spring.entity.Member;
import com.practice.spring.repository.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public void minusMoney(int money) {
		Member kangjune = this.memberRepository.findByName("june");
		kangjune.minusMoney(money);
	}
}
