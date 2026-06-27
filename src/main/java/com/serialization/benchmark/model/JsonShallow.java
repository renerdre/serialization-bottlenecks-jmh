package com.serialization.benchmark.model;
import java.util.List;

public class JsonShallow {
    public int id;
    public String name;
    public List<Address> addresses;

    public static class Address {
        public String street;
        public String city;
        public String zipCode;
        public String country;
    }
}