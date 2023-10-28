package com.practice.spring.test.five.domain;

public class Transaction {

    private final Trader trader;
    private final int year;
    private final int amount;

    public Transaction(Trader trader, int year, int amount) {
        this.trader = trader;
        this.year = year;
        this.amount = amount;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", amount=" + amount +
                '}';
    }
}
