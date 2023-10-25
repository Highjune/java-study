package com.practice.spring.domain;

@FunctionalInterface
public interface ApplePredicate {
    boolean test(Apple apple);
}
