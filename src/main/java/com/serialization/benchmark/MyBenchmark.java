package com.serialization.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serialization.benchmark.generator.DataGenerator;
import com.serialization.benchmark.model.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;

// ==========================================
// (BENCHMARK CONFIG)
// ==========================================
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) 
@Fork(1)
@State(Scope.Benchmark)
public class MyBenchmark {

    private List<JsonFlat> jsonFlatList;
    private List<UserFlat> protoFlatList;
    
    private List<JsonShallow> jsonShallowList;
    private List<UserShallow> protoShallowList;
    
    private List<JsonDeep> jsonDeepList;
    private List<UserDeep> protoDeepList;

    private ObjectMapper jsonMapper;

    private int index = 0;
    private final int DATA_SIZE = 100000;

    // ==========================================
    // PREPARATION STAGE (Not counting the time)
    // ==========================================
    @Setup(Level.Trial)
    public void setupData() {
        System.out.println("\n[INFO] Menyiapkan 100.000 data palsu... Mohon tunggu...");
        jsonMapper = new ObjectMapper();

        jsonFlatList = DataGenerator.generateJsonFlat(DATA_SIZE);
        protoFlatList = DataGenerator.generateProtoFlat(DATA_SIZE);

        jsonShallowList = DataGenerator.generateJsonShallow(DATA_SIZE);
        protoShallowList = DataGenerator.generateProtoShallow(DATA_SIZE);

        jsonDeepList = DataGenerator.generateJsonDeep(DATA_SIZE);
        protoDeepList = DataGenerator.generateProtoDeep(DATA_SIZE);
        System.out.println("[INFO] Data berhasil disiapkan! Memulai Benchmark...\n");
    }

    private int getNextIndex() {
        index = (index + 1) % DATA_SIZE;
        return index;
    }

    // ==========================================
    // ROUND 1: LEVEL FLAT
    // ==========================================
    @Benchmark
    public void testJsonFlat(Blackhole blackhole) throws Exception {
        JsonFlat data = jsonFlatList.get(getNextIndex());

        String jsonString = jsonMapper.writeValueAsString(data);
        blackhole.consume(jsonString);
    }

    @Benchmark
    public void testProtoFlat(Blackhole blackhole) {
        UserFlat data = protoFlatList.get(getNextIndex());

        byte[] protoBytes = data.toByteArray();
        blackhole.consume(protoBytes);
    }

    // ==========================================
    // ROUND 2: LEVEL SHALLOW
    // ==========================================
    @Benchmark
    public void testJsonShallow(Blackhole blackhole) throws Exception {
        JsonShallow data = jsonShallowList.get(getNextIndex());
        String jsonString = jsonMapper.writeValueAsString(data);
        blackhole.consume(jsonString);
    }

    @Benchmark
    public void testProtoShallow(Blackhole blackhole) {
        UserShallow data = protoShallowList.get(getNextIndex());
        byte[] protoBytes = data.toByteArray();
        blackhole.consume(protoBytes);
    }

    // ==========================================
    // ROUND 3: LEVEL DEEP
    // ==========================================
    @Benchmark
    public void testJsonDeep(Blackhole blackhole) throws Exception {
        JsonDeep data = jsonDeepList.get(getNextIndex());
        String jsonString = jsonMapper.writeValueAsString(data);
        blackhole.consume(jsonString);
    }

    @Benchmark
    public void testProtoDeep(Blackhole blackhole) {
        UserDeep data = protoDeepList.get(getNextIndex());
        byte[] protoBytes = data.toByteArray();
        blackhole.consume(protoBytes);
    }
}