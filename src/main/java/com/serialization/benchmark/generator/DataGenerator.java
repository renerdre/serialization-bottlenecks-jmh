package com.serialization.benchmark.generator;

import com.serialization.benchmark.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final long SEED = 12345L;

    // ==========================================
    // GENERATOR LEVEL 1: FLAT (100.000 Data)
    // ==========================================
    public static List<JsonFlat> generateJsonFlat(int count) {
        Random random = new Random(SEED);
        List<JsonFlat> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            JsonFlat obj = new JsonFlat();
            obj.id = i;
            obj.name = "User_" + random.nextInt(10000);
            obj.email = "user" + i + "@example.com";
            obj.isActive = random.nextBoolean();
            obj.balance = random.nextDouble() * 10000;
            list.add(obj);
        }
        return list;
    }

    public static List<UserFlat> generateProtoFlat(int count) {
        Random random = new Random(SEED);
        List<UserFlat> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(UserFlat.newBuilder()
                    .setId(i)
                    .setName("User_" + random.nextInt(10000))
                    .setEmail("user" + i + "@example.com")
                    .setIsActive(random.nextBoolean())
                    .setBalance(random.nextDouble() * 10000)
                    .build());
        }
        return list;
    }

    // ==========================================
    // GENERATOR LEVEL 2: SHALLOW (100.000 Data)
    // ==========================================
    public static List<JsonShallow> generateJsonShallow(int count) {
        Random random = new Random(SEED);
        List<JsonShallow> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            JsonShallow obj = new JsonShallow();
            obj.id = i;
            obj.name = "User_" + random.nextInt(10000);
            obj.addresses = new ArrayList<>();
            for(int j = 0; j < 5; j++) {
                JsonShallow.Address addr = new JsonShallow.Address();
                addr.street = "Street " + random.nextInt(1000);
                addr.city = "City " + random.nextInt(100);
                addr.zipCode = "Zip" + random.nextInt(99999);
                addr.country = "Country " + random.nextInt(50);
                obj.addresses.add(addr);
            }
            list.add(obj);
        }
        return list;
    }

    public static List<UserShallow> generateProtoShallow(int count) {
        Random random = new Random(SEED);
        List<UserShallow> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserShallow.Builder userBuilder = UserShallow.newBuilder()
                    .setId(i)
                    .setName("User_" + random.nextInt(10000));
            
            for(int j = 0; j < 5; j++) {
                userBuilder.addAddresses(Address.newBuilder()
                        .setStreet("Street " + random.nextInt(1000))
                        .setCity("City " + random.nextInt(100))
                        .setZipCode("Zip" + random.nextInt(99999))
                        .setCountry("Country " + random.nextInt(50))
                        .build());
            }
            list.add(userBuilder.build());
        }
        return list;
    }

    // ==========================================
    // GENERATOR LEVEL 3: DEEP (100.000 Data)
    // ==========================================
    public static List<JsonDeep> generateJsonDeep(int count) {
        Random random = new Random(SEED);
        List<JsonDeep> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            JsonDeep obj = new JsonDeep();
            obj.id = i;
            obj.name = "User_" + random.nextInt(10000);
            obj.deepAddresses = new ArrayList<>();
            for(int j = 0; j < 3; j++) { 
                JsonDeep.DeepAddress addr = new JsonDeep.DeepAddress();
                addr.street = "Street " + random.nextInt(1000);
                addr.city = "City " + random.nextInt(100);
                addr.transactions = new ArrayList<>();
                for(int k = 0; k < 5; k++) { 
                    JsonDeep.TransactionHistory trx = new JsonDeep.TransactionHistory();
                    trx.transactionId = "TRX-" + random.nextInt(100000);
                    trx.amount = random.nextDouble() * 5000;
                    trx.date = "2026-06-0" + (random.nextInt(9) + 1);
                    
                    JsonDeep.Currency curr = new JsonDeep.Currency();
                    curr.code = "USD";
                    curr.symbol = "$";
                    trx.currency = curr;
                    
                    addr.transactions.add(trx);
                }
                obj.deepAddresses.add(addr);
            }
            list.add(obj);
        }
        return list;
    }

    public static List<UserDeep> generateProtoDeep(int count) {
        Random random = new Random(SEED);
        List<UserDeep> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserDeep.Builder userBuilder = UserDeep.newBuilder()
                    .setId(i)
                    .setName("User_" + random.nextInt(10000));
            
            for(int j = 0; j < 3; j++) {
                DeepAddress.Builder addrBuilder = DeepAddress.newBuilder()
                        .setStreet("Street " + random.nextInt(1000))
                        .setCity("City " + random.nextInt(100));
                
                for(int k = 0; k < 5; k++) {
                    addrBuilder.addTransactions(TransactionHistory.newBuilder()
                            .setTransactionId("TRX-" + random.nextInt(100000))
                            .setAmount(random.nextDouble() * 5000)
                            .setDate("2026-06-0" + (random.nextInt(9) + 1))
                            .setCurrency(Currency.newBuilder().setCode("USD").setSymbol("$").build())
                            .build());
                }
                userBuilder.addDeepAddresses(addrBuilder.build());
            }
            list.add(userBuilder.build());
        }
        return list;
    }
}