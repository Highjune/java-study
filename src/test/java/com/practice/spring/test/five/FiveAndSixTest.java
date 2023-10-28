package com.practice.spring.test.five;

import com.practice.spring.test.five.domain.Trader;
import com.practice.spring.test.five.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.SecondaryTable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
        List<String> result = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        for (String city : result) {
            System.out.println(city);
        }
    }

    @Test
    public void test_3() {
        String targetCity = "Cambridge";

        List<String> result = transactions.stream()
                .filter(transaction -> targetCity.equals(transaction.getTrader().getCity()))
                .map(transaction -> transaction.getTrader().getPartnerName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void test_4() {
        List<String> result = transactions.stream()
                .map(transaction -> transaction.getTrader().getPartnerName())
                .distinct() // 없어도 될 수도
                .sorted()
                .collect(Collectors.toList());

        for (String s : result) {
            System.out.println(s);
        }
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
                .forEach(System.out::println);
    }

    @Test
    public void test_7() {
        transactions.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .ifPresent(System.out::println);
    }

    @Test
    public void test_8() {
        transactions.stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .ifPresent(System.out::println);
    }


}
