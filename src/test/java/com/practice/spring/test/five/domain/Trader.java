package com.practice.spring.test.five.domain;

public class Trader {

    private final String partnerName;
    private final String city;

    public Trader(String partnerName, String city) {
        this.partnerName = partnerName;
        this.city = city;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "partnerName='" + partnerName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
