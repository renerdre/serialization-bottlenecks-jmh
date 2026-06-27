package com.serialization.benchmark.model;
import java.util.List;

public class JsonDeep {
    public int id;
    public String name;
    public List<DeepAddress> deepAddresses;

    public static class DeepAddress {
        public String street;
        public String city;
        public List<TransactionHistory> transactions;
    }

    public static class TransactionHistory {
        public String transactionId;
        public double amount;
        public String date;
        public Currency currency;
    }

    public static class Currency {
        public String code;
        public String symbol;
    }
}