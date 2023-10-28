package com.practice.spring.test;


import com.practice.spring.domain.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamTest {

    private List<Dish> menu = new ArrayList<>();
    private List<Dish> specialMenu = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        menu = Arrays.asList(
          new Dish("pork", false, 800, Dish.Type.MEAT),
          new Dish("beef", false, 700, Dish.Type.MEAT),
          new Dish("chicken", false, 400, Dish.Type.MEAT),

          new Dish("pizza", true, 550, Dish.Type.OTHER),
          new Dish("rice", true, 350, Dish.Type.OTHER),
          new Dish("french fries", true, 530, Dish.Type.OTHER),
          new Dish("season fruit", true, 120, Dish.Type.OTHER),

          new Dish("prawns", false, 300, Dish.Type.FISH),
          new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        specialMenu = Arrays.asList(
            new Dish("seasonable fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER)
        );
    }

    @Test
    public void checkOrder() {
        List<String> threadHighCaloricDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());

        List<String> names = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering: " + dish.getName());
                    return dish.getCalories() > 30;
                })
                .map(dish -> {
                    System.out.println("mapping: " + dish.getName());
                    return dish.getName();
                })
                .limit(4)
                .collect(toList());

        System.out.println(names);
    }
    
    @Test
    public void distinct() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void takeWhile() {
        List<Dish> specialMenu1 = specialMenu.stream()
                .takeWhile(s -> s.getCalories() < 320)
                .collect(toList());

        System.out.println(specialMenu1);
    }

    @Test
    public void dropWhile() {
        List<Dish> specialMenu1 = specialMenu.stream()
                .dropWhile(s -> s.getCalories() < 320)
                .collect(toList());

        System.out.println(specialMenu1);
    }

    @Test
    public void skip() {
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());

        System.out.println(dishes);
    }

    @Test
    public void quiz5_1() {
        List<Dish> dishes = menu.stream()
                .filter(m -> m.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(toList());

        System.out.println(dishes);
    }

    @Test
    public void map1() {
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());

        List<Integer> dishNameLengths = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());

        List<Integer> dishNameLengths2 = menu.stream()
                .map(menu -> menu.getName().length())
                .collect(toList());
    }

    @Test
    public void map2() {
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

        List<String[]> collect = words.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(toList());
    }

    @Test
    public void map3() {
        String[] arrayOfWords = {"GoodBye", "World"};
        Stream<String> words = Arrays.stream(arrayOfWords);

        List<Stream<String>> collect = words
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());
    }

    @Test
    public void map4() {
        String[] arrayOfWords = {"GoodBye", "World"};
        Stream<String> words = Arrays.stream(arrayOfWords);

        words.map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
    }

    @Test
    public void map5() {
        List<Integer> collect = Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .map(num -> num * num)
                .collect(toList());

        for (Integer integer : collect) {
            System.out.println(integer);
        }
    }
    @Test
    public void map6() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4);

        List<Stream<int[]>> collect1 = list1.stream()
                .map(i -> list2.stream()
                        .map(j -> new int[]{i, j})
                )
                .collect(toList());

        List<int[]> collect2 = list1.stream()
                .flatMap(i -> list2.stream()
                        .map(j -> new int[]{i, j})
                )
                .collect(toList());

        List<int[]> pairs = list1.stream()
                .flatMap(i -> list2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j})
                )
                .collect(toList());
    }
    @Test
    public void anyMatch1() {
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("어느정도는 채식주의자 친화적이다");
        }
    }
    @Test
    public void allMatch() {
        boolean isHealth = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000);
        System.out.println(isHealth);
    }
    @Test
    public void noneMatch() {
        boolean isHealth = menu.stream()
                .noneMatch(dish -> dish.getCalories() >= 1000);
        System.out.println(isHealth);
    }

    @Test
    public void findAny0() {
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();

        dish.ifPresent(item -> System.out.println(item.getName()));
    }

    @Test
    public void findAny() {
        Optional<Dish> any = menu.stream()
                .findAny();

        System.out.println(any.get().getName());
    }
    @Test
    public void findAny2() {
        menu.stream()
                .filter(Dish::isVegetarian)
                .findFirst()
                .ifPresent(dish -> System.out.println(dish.getName()));
    }
    @Test
    public void findFirst() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);

        // 1
        Optional<Integer> first1 = someNumbers.stream()
                .filter(num -> num % 3 == 0)
                .map(num -> num * num)
                .findFirst();

        // 2
        Optional<Integer> first2 = someNumbers.stream()
                .map(num -> num * num)
                .filter(num -> num % 3 == 0)
                .findFirst();
    }

    @Test
    public void reduce0() {
        List<Integer> numbers = Arrays.asList(4, 5 ,3, 9);
        int sum = 0;
        for (int x : numbers) {
            sum += x;
        }
        System.out.println(sum);

        Integer result = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println(result);

        Integer result1 = numbers.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println(result1);

        Integer result2 = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println(result2);

        Optional<Integer> result3 = numbers.stream()
                .reduce((a, b) -> a + b);

        // 내 방식
        Integer max1 = numbers.stream()
                .reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
        System.out.println(max1);

        Optional<Integer> max2 = numbers.stream()
                .reduce(Integer::max);
        System.out.println(max2);

        Optional<Integer> min0 = numbers.stream()
                .reduce(Integer::min);
        System.out.println(min0);
    }

    @Test
    public void quiz_5_3() {
        // map-reduce (쉽게 병렬화해서 구글이 웹 검색에 적용하면서 유명해짐)
        Integer menuCount = menu.stream()
                .map(m -> 1)
                .reduce(0, (a, b) -> a + b);
        System.out.println(menuCount);

        // 바로 count
        long count = menu.stream()
                .count();
        System.out.println(count);
    }

    @Test
    public void page_181() {
        Integer sumCalories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);    // Boxing 이 이루어짐
        System.out.println(">> " + sumCalories);

        menu.stream()
                .map(Dish::getCalories)
                .reduce((d1, d2) -> d1 + d2)
                .ifPresent(System.out::println);

        // Boxing 없이 sum()? -> 안됨.
//        menu.stream()
//                .map(Dish::getCalories) // return 값이 Stream<T> 이므로.
//                .sum();


    }


}
