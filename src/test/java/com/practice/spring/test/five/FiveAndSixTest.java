package com.practice.spring.test.five;

import com.practice.spring.test.five.domain.Trader;
import com.practice.spring.test.five.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.SecondaryTable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 5.6 실전연습
public class FiveAndSixTest {
    
    private List<Transaction> transactions;
    private Trader raoul;
    private Trader mario;
    private Trader alan;
    private Trader brian;

    @BeforeEach
    public void setUp() {
        raoul = new Trader("Raoul", "Cambridge");
        mario = new Trader("Mario", "Milan");
        alan = new Trader("Alan", "Cambridge");
        brian = new Trader("Brian", "Cambridge");
        
         transactions = Arrays.asList(
                 new Transaction(brian, 2011, 300),
                 new Transaction(raoul, 2012, 1000),
                 new Transaction(raoul, 2011, 400),
                 new Transaction(mario, 2012, 710),
                 new Transaction(mario, 2012, 700),
                 new Transaction(alan, 2012, 950)
        );
    } 

    @Test
    public void test_1() {
        List<Transaction> result = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .collect(Collectors.toList());

        for (Transaction transaction : result) {
            System.out.println(transaction.toString());
        }
    }

    @Test
    public void test_2() {
        // 1
        List<String> result = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        for (String city : result) {
            System.out.println(city);
        }

        System.out.println("-------");

        // 2 (Set) 으로
        Set<String> result2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(Collectors.toSet());

        for (String s : result2) {
            System.out.println(s);
        }
    }

    @Test
    public void test_3() {
        String targetCity = "Cambridge";

        // 1
        List<String> result = transactions.stream()
                .filter(transaction -> targetCity.equals(transaction.getTrader().getCity()))
                .map(transaction -> transaction.getTrader().getPartnerName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (String s : result) {
            System.out.println(s);
        }

        System.out.println("-----------------");

        // map 으로 먼저 변환
        List<Trader> result2 = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals(targetCity))
                .distinct()
                .sorted(Comparator.comparing(Trader::getPartnerName))
                .collect(Collectors.toList());

        System.out.println(result2.size());

        for (Trader trader : result2) {
            System.out.println(trader.toString());
        }
    }

    @Test
    public void test_4() {
        // 1
        List<String> result = transactions.stream()
                .map(transaction -> transaction.getTrader().getPartnerName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (String s : result) {
            System.out.println(s);
        }

        System.out.println("-----------");

        // 2
        String result2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getPartnerName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(result2);

        System.out.println("-----------");

        // 3
        String result3 = transactions.stream()
                .map(transaction -> transaction.getTrader().getPartnerName())
                .distinct()
                .sorted()
//                .collect(Collectors.joining()); // 위처럼 그냥 연결. AlanBrianMarioRaoul
                .collect(Collectors.joining(","));
        System.out.println(result3);
    }

    @Test
    public void test_5() {
        String targetCity = "Milan";

        boolean isThereMilanPartner = transactions.stream()
                .anyMatch(transaction -> targetCity.equals(transaction.getTrader().getCity()));

        if (isThereMilanPartner) {
            System.out.println("밀라노에 거래자가 존재합니다.");
            return;
        }
        System.out.println("밀라노에 거래자가 존재하지 않습니다.");
    }

    @Test
    public void test_6() {
        String targetCity = "Cambridge";

        transactions.stream()
                .filter(transaction -> targetCity.equals(transaction.getTrader().getCity()))
                .map(Transaction::getAmount)
                .forEach(System.out::println);
    }

    @Test
    public void test_7() {
        // 1
        transactions.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .ifPresent(System.out::println);

        // 2
        transactions.stream()
                .map(Transaction::getAmount)
                .reduce(Integer::max);

    }

    @Test
    public void test_8() {
        // 1
        transactions.stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .ifPresent(System.out::println);

        // 2
        transactions.stream()
                .reduce((t1, t2) -> t1.getAmount() < t2.getAmount() ? t1 : t2);
    }


}
