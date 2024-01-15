package com.practice.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.spring.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByName(String Name);
}
