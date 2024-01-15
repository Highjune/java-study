package com.practice.spring.remote;

import javax.swing.tree.ExpandVetoException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.spring.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteService {

	public void netCancelPoint() {

		try {
			log.info("망 취소 진행중, Exception 발생");
			throw new Exception(); // 고의 에러 발생
		} catch (Exception e) {
			log.error("네이버 포인트 망 취소 오류 : {}", e);
			// throw new CommonException(); // 없애야 됨. 그래야 여기서 안 끝남.
		}
	}
}
