package com.practice.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Apple implements Fruit{
    private String color;
    private int weight;
    private List<String> list = new ArrayList<>();

    public Apple(int weight) {
        this.weight = weight;
    }

    public Apple(String color) {
        this.color = color;
    }

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T t : list) {
            if(p.test(t)) {
                results.add(t);
            }
        }
        return results;
    }

    public <T> void filterTest(Predicate<T> t) {
        System.out.println(t);
    }

    public void filterTest1(IntPredicate t) {
        System.out.println(t);
    }

    public <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

    public <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            R r = f.apply(t);
            result.add(r);
        }

        return result;
    }

    public List<String> filterMethod() {
        return list.stream()
//                .filter(s -> isValidNumber(s))
                .filter(this::isValidNumber) // good
                .collect(Collectors.toList());
    }

    private boolean isValidNumber(String s) {
        return Character.isUpperCase(s.charAt(0));
    }
}