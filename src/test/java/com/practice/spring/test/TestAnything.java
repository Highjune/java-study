package com.practice.spring.test;

import com.practice.spring.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.*;
import java.util.stream.Collectors;

@Slf4j
public class TestAnything {

    private ApplePredicate applePredicate;
    private TwoParameterInterface twoParameterInterface = (a, b) -> 42;
    private TwoParameterInterface twoParameterInterface2 = (a, b) -> {
        System.out.println(a);
        System.out.println(b);
        return 0;
    };
    private OneParameterInterface oneParameterInterface = a -> 42;
    private NoParameterInterface noParameterInterface = () -> 42;
    private NoParameterNoReturn noParameterNoReturn = () -> {};
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("hi");
        }
    };
    private Runnable runnable1 = () -> System.out.println("hi");
    private Runnable runnable2 = () -> {System.out.println("hi2");};
    private NoParameterNoReturn noParameterNoReturn1 = () -> {};
    private Callable<Integer> c = () -> 42;
    private PrivilegedAction<Integer> p = () -> 42;
    private List<String> list = new ArrayList<>();
    private Predicate<String> p1 = s -> list.add(s);
    private Consumer<String> p2 = s -> list.add(s);
    private VoidInterface<String> aa = a -> list.add(a);

    static Map<String, Function<Integer, Fruit>> map = new HashMap<>();
    static {
        map.put("apple", Apple::new);
        map.put("apple", Banana::new);
    }

    public static Fruit giveMeFruit(String fruitName, Integer weight) {
        return map.get(fruitName.toLowerCase())
                .apply(weight);
    }

    @Test
    public void test() {
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        Apple apple = new Apple();
        apple.filter(new ArrayList<String>(), nonEmptyStringPredicate);
//        apple.filter(new ArrayList<>(), (String s) -> !s.isEmpty());


        apple.forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer a) -> System.out.println(a));

        apple.map(Arrays.asList(1, 2, 3, 4, 5), (Integer a) -> a + 1);
        apple.map(Arrays.asList("hi", "hello", "june", "mijin", "min"), (String s) -> s.length());

        List<Integer> collect = Arrays.asList("hi", "hello")
                .stream()
                .map(s -> s.length())
                .collect(Collectors.toList());

        IntPredicate evenNumbers = (int i) -> i % 2 == 0;
        evenNumbers.test(1000);

        IntPredicate oddNumbers = (int i) -> i % 2 != 0;
        oddNumbers.test(1000);

        apple.filterTest((Integer a) -> a % 2 == 0);
        apple.filterTest1((int a) -> a % 2 == 0);
    }

    @Test
    public void test1() {
        int phoneNumber = 7913340;
        Runnable r = () -> System.out.println(phoneNumber);

        List<String> str22 = Arrays.asList("a", "b", "A", "B");
        str22.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        str22.sort(String::compareTo);

        Supplier<Apple> c1 = Apple::new;
        Apple apple = c1.get();

        Supplier<Apple> c2 = () -> new Apple();
        Apple apple1 = c2.get();

        Function<Integer, Apple> c3 = Apple::new;
        Apple apple2 = c3.apply(100);

        Function<Integer, Apple> c4 = (i) -> new Apple(i);
        Apple apple3 = c4.apply(100);

        List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        Apple applee = new Apple();
        applee.map(weights, c4);

        BiFunction<String, Integer, Apple> biFunction1 = Apple::new;
        Apple greenTenWeightApple = biFunction1.apply("GREEN", 10);

        BiFunction<String, Integer, Apple> biFunction2 = (str, i) -> new Apple(str, i);
        Apple greenTenWeightApple2 = biFunction2.apply("GREEN", 10);

        list.sort(Comparator.comparing(String::length)
                .reversed()
                .thenComparing(String::length));

    }

    @Test
    public void test2() {
        Predicate<Apple> greenApple = s -> s.getColor().equals("GREEN");
        System.out.println(greenApple.test(new Apple("RED")));

        Predicate<Apple> notGreenApple = greenApple.negate();
        System.out.println(notGreenApple.test(new Apple("RED")));

        Predicate<Apple> greenAppleGT150 = greenApple.and(s -> s.getWeight() >= 150);
        System.out.println(greenAppleGT150.test(new Apple("GREEN", 150)));

        Predicate<Apple> red = greenApple.and(apple -> apple.getWeight() > 170)
                .or(apple -> apple.getColor().equals("RED"));

        System.out.println(red.test(new Apple("RED", 150)));

    }

    @Test
    public void test3() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> plusAndMultiply = f.andThen(g);
        Integer result = plusAndMultiply.apply(1);
        System.out.println(result);

        Function<Integer, Integer> second = f.compose(g);
        System.out.println(second.apply(1));
        list.forEach((s) -> System.out.println(s));
    }




}