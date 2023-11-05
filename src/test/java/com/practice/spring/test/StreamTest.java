package com.practice.spring.test;


import com.practice.spring.collector.ToListCollector;
import com.practice.spring.domain.Dish;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.TestComponent;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

public class StreamTest {

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
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action" );
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
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action" );

        List<String[]> collect = words.stream()
                .map(word -> word.split("" ))
                .distinct()
                .collect(toList());
    }

    @Test
    public void map3() {
        String[] arrayOfWords = {"GoodBye", "World"};
        Stream<String> words = Arrays.stream(arrayOfWords);

        List<Stream<String>> collect = words
                .map(word -> word.split("" ))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());
    }

    @Test
    public void map4() {
        String[] arrayOfWords = {"GoodBye", "World"};
        Stream<String> words = Arrays.stream(arrayOfWords);

        words.map(word -> word.split("" ))
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
            System.out.println("어느정도는 채식주의자 친화적이다" );
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
        List<Integer> numbers = Arrays.asList(4, 5, 3, 9);
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

    @Test
    public void page_207() {
        int totalCalories = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));

        Integer totalCalories2 = menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::sum).get();

        int totalCalories3 = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
    }

    @Test
    public void reducingCollect() {
        // 사실 이것이 제일 가독성, 성능 좋음
        String shortMenu = menu.stream()
                .map(Dish::getName)
                .collect(joining()); // joining()은 내부적으로 StringBuilder을 사용.

        // 1
        String shortMenu2 = menu.stream()
                .map(Dish::getName)
                .collect(reducing((s1, s2) -> s1 + s2)).get();  // 누산자 함수 사용. 문자열 합치는 BinaryOperator 사용.

        // 2 - 컴파일 에러
//        menu.stream()
//                .collect(Collectors.reducing((d1, d2) -> d1.getName() + d2.getName()));

        // 3
        String shortMenu3 = menu.stream()
                .collect(reducing("", Dish::getName, (s1, s2) -> s1 + s2));
    }

    @Test
    public void grouping() {
        // 사실 grouping로 넘겨주는 컬렉터의 형식은 제안이 없다. 아래와 같이 두 번째 인수로 counting 컬렉터를 전달할 수도 있다.
        // 분류 함수 한 개의 인수를 갖는 groupingBy(f) 는 사실 groupingBy(f, toList()) 의 축약형

        Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                .collect(groupingBy(menu -> menu.getType()));

        Map<Dish.Type, List<Dish>> dishesByType2 = menu.stream()
                .collect(groupingBy(Dish::getType));

        System.out.println(dishesByType2);
    }

    @Test
    public void grouping2() {
        Map<CalorieLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CalorieLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CalorieLevel.NORMAL;
                    } else {
                        return CalorieLevel.FAT;
                    }
                }));
    }

    @Test
    public void grouping3() {
        Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream()
                .filter(d -> d.getCalories() > 500)
                .collect(groupingBy(Dish::getType));

        System.out.println(caloricDishesByType); // Fish 타입 자체가 없어지므로 키 자체가 맵에서 사라진다.
    }

    @Test
    public void grouping4() {
        // 위 문제점의 해결책
        Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream()
                .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList()))); // grouping 인자 2개

        System.out.println(caloricDishesByType);
    }

    @Test
    public void grouping5() {
        Map<Dish.Type, List<String>> collect = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList()))); // grouping 인자 2개

        System.out.println(collect);
    }

    @Test
    public void grouping6() {
        Map<Dish.Type, Set<String>> collect = menu.stream()
                .collect(groupingBy(Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet()))); // grouping 인자 2개

        System.out.println(collect);
    }

    @Test
    public void grouping7() {
        // grouping 안의 grouping

        Map<Dish.Type, Map<CalorieLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(groupingBy(Dish::getType,  // groupingBy 인수 2개
                        groupingBy(dish -> {
                            if (dish.getCalories() <= 400) {
                                return CalorieLevel.DIET;
                            } else if (dish.getCalories() <= 700) {
                                return CalorieLevel.NORMAL;
                            } else {
                                return CalorieLevel.FAT;
                            }
                        })
                ));

        System.out.println(dishesByTypeCaloricLevel);
    }

    @Test
    public void grouping8() {
        // 사실 grouping로 넘겨주는 컬렉터의 형식은 제안이 없다. 아래와 같이 두 번째 인수로 counting 컬렉터를 전달할 수도 있다.
        // 분류 함수 한 개의 인수를 갖는 groupingBy(f) 는 사실 groupingBy(f, toList()) 의 축약형
        Map<Dish.Type, Long> typesCount = menu.stream()
                .collect(groupingBy(Dish::getType, counting())); // groupingBy 인수 2개

        System.out.println(typesCount);
    }

    @Test
    public void grouping9() {
        // 종류별 메뉴에서 가장 높은 칼로리를 가진 요리 찾기(Optional 로 반환)
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        maxBy(Comparator.comparingInt(Dish::getCalories))));

        System.out.println(mostCaloricByType);
    }

    @Test
    public void grouping10() {
        // 종류별 메뉴에서 가장 높은 칼로리를 가진 요리 찾기(Optional 풀기)
        // 리듀싱 컬렉터는 절대 Optional.empty()를 반환하지 않으므로 안전한 코드다. 그래서 Optional::get 으로 꺼내서 반환

        // 3개의 컬렉터(groupingBy, collectingAndThen, maxBy)
        Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
                // 분류 함수로 원래 스트림을 각각의 서브 스트림으로 분할
                // groupingBy 컬렉터는 CollectingAndThen 컬렉터를 감싼다. 그래서 두 번째 컬렉터(CollectingAndThen) 는 그룹화된 세 개의 서브스트림에 적용
                .collect(groupingBy(Dish::getType,
                        // collectingAndThen 컬렉터는 세 번쟤 컬렉터 maxBy를 감싼다.
                        // 리듀싱 컬렉터(CollectingAndThen) 가 각각의 서브스트림에 연산(maxBy) 를 한 결과에 Optional::get 적용
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));

        System.out.println(mostCaloricByType);
    }

    @Test
    public void groupingEtc() {
        Map<Dish.Type, Integer> collect1 = menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));

        System.out.println(collect1);

        Map<Dish.Type, Set<CalorieLevel>> collect2 = menu.stream().collect(
                groupingBy(Dish::getType, mapping(dish -> {
                            if (dish.getCalories() <= 400) {
                                return CalorieLevel.DIET;
                            } else if (dish.getCalories() <= 700) {
                                return CalorieLevel.NORMAL;
                            } else {
                                return CalorieLevel.FAT;
                            }
                        },
                        toSet()
                )));
        System.out.println(collect2);

        Map<Dish.Type, HashSet<CalorieLevel>> collect3 = menu.stream().collect(
                groupingBy(Dish::getType, mapping(dish -> {
                            if (dish.getCalories() <= 400) {
                                return CalorieLevel.DIET;
                            } else if (dish.getCalories() <= 700) {
                                return CalorieLevel.NORMAL;
                            } else {
                                return CalorieLevel.FAT;
                            }
                        }, toCollection(HashSet::new)
                )));

        System.out.println(collect3);
    }

    @Test
    public void partition1() {
        Map<Boolean, List<Dish>> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        System.out.println(collect);

        List<Dish> veterianTrue = collect.get(true);
        List<Dish> veterianFalse = collect.get(false);

        System.out.println(veterianTrue);
        System.out.println(veterianFalse);

        // partitionBy 말고 그냥 filter로 해도 같은 결과 나온다.
        List<Dish> trueVegetarian = menu.stream()
                .filter(Dish::isVegetarian) // .filter(dish -> !dish.getIsvegetarian())
                .collect(toList());
        System.out.println(trueVegetarian);
    }

    @Test
    public void partition2() {
        // partitionBy 두번째 인자에 컬렉터 전달
        Map<Boolean, Map<Dish.Type, List<Dish>>> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));

        System.out.println(collect);
    }

    @Test
    public void partition3() {
        // 채식 요리와 채식이 아닌 요리 각각의 그룹에서 가장 칼로리가 높은 요리 찾기
        Map<Boolean, Dish> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian
                        , collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));

        System.out.println(collect);
    }

    @Test
    public void quiz6_2_1() {
        // 채식주의 t/f 안에서 calories 500이상의 t/f 로 나뉨. 총 4개 그룹
        Map<Boolean, Map<Boolean, List<Dish>>> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        partitioningBy(dish -> dish.getCalories() > 500)));
        System.out.println(collect);
    }

    @Test
    public void quiz6_2_2() {
//        menu.stream()
//                .collect(partitioningBy(Dish::isVegetarian,
//                        partitioningBy(Dish::getType))); // 컴파일 에러임. predicate를 인수로 받아야 함
    }

    @Test
    public void quiz6_2_3() {
        // 채식주의 true / false 의 각각 수
        Map<Boolean, Long> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, counting()));

        System.out.println(collect);
    }

    @Test
    public void partitionPrimes() {
        // n개의 숫자를 소수와 비소수로 분류
        Map<Boolean, List<Integer>> booleanListMap = partitionPrimes(50);
        System.out.println(booleanListMap);
    }

    // 소수인지 판별하는 것
    public boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    // n개의 숫자를 소수와 비소수로 분류
    public Map<Boolean, List<Integer>> partitionPrimes(int number) {
        return IntStream.rangeClosed(2, number).boxed()
                .collect(partitioningBy(this::isPrime));
    }

    @Test
    public void collectBreifing() {
        List<Dish> collect = menu.stream()
                .collect(toList());

        Set<Dish> collect1 = menu.stream()
                .collect(toSet());

        Collection<Dish> dishes = menu.stream()
                .collect(Collectors.toCollection(ArrayList::new));

        menu.stream()
                .collect(counting());

        menu.stream()
                .collect(summingInt(Dish::getCalories));

        menu.stream()
                .collect(averagingInt(Dish::getCalories));

        IntSummaryStatistics summary = menu.stream()
                .collect(summarizingInt(Dish::getCalories));
        summary.getMax();
        summary.getSum();
        summary.getMin();
        summary.getMin();
        summary.getCount();

        menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining(", " ));

        Optional<Dish> collect2 = menu.stream()
                .collect(maxBy(Comparator.comparingInt(Dish::getCalories)));

        Integer collect3 = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));

        Integer collect6 = menu.stream()
                .collect(collectingAndThen(toList(), List::size));

        Map<Dish.Type, List<Dish>> collect5 = menu.stream()
                .collect(groupingBy(Dish::getType));

        Map<Boolean, List<Dish>> collect4 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
    }

    @Test
    public void customToListCollector() {
        // 기존1 -> 싱글턴 Collections.empty(); 로 빈 리스트를 반환한다.
        List<Dish> dishes1 = menu.stream()
                .collect(toList());

        List<Dish> dishes2 = menu.stream()
                .collect(new ToListCollector<Dish>()); // new로 인스턴스화
    }

    @Test
    public void originToListCollector() {
        // 컬렉턴 구현 만들지 않고도 커스텀 수집 수행하기
        menu.stream().collect(
                ArrayList::new,  // supplier (발행)
                List::add,      // accumulator (누적)
                List::addAll    // combiner (합침)
        );
    }




}