package com.practice.spring.test.five;

import com.practice.spring.domain.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Stream2Test {

    private List<Dish> menu = new ArrayList<>();
    private List<Dish> specialMenu = new ArrayList<>();
    private Map<String, List<String>> dishTags = new HashMap<>();

    public enum CalorieLevel {
        DIET, NORMAL, FAT
    }

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

        dishTags.put("pork", Arrays.asList("greasy", "salty" ));
        dishTags.put("beef", Arrays.asList("salty", "roasted" ));
        dishTags.put("chicken", Arrays.asList("fried", "crisp" ));
        dishTags.put("french fries", Arrays.asList("greasy", "fried" ));
        dishTags.put("rice", Arrays.asList("light", "natural" ));
        dishTags.put("season fruit", Arrays.asList("fresh", "natural" ));
        dishTags.put("pizza", Arrays.asList("tasty", "salty" ));
        dishTags.put("prawns", Arrays.asList("tasty", "roasted" ));
        dishTags.put("salmon", Arrays.asList("delicious", "fresh" ));
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        return primes.stream().noneMatch(i -> candidate % i == 0);
    }
}
