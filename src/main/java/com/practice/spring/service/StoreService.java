package com.practice.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.spring.exception.CommonException;
import com.practice.spring.remote.RemoteService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class StoreService {

	private final RemoteService remoteService;
	private final MemberService memberService;


	@Transactional
	public String convert() {
		String result = "끝";

		try {
			// 결제 처리
			this.memberService.minusMoney(100);
			log.info("포인트 전환 중 에러 발생");
			throw new CommonException(); // 고의 에러 발생
		} catch (Exception e) {
			log.error("결제 처리중 오류 발생 : {} ", e.getMessage(), e);

			// 망 취소 호출
			this.remoteService.netCancelPoint();
			// throw e; // 없애야 됨. @Transactional 을 건드리면 안됨
		}

		return result;
	}
}
