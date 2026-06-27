package com.serialization.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serialization.benchmark.generator.DataGenerator;
import com.serialization.benchmark.model.*;

public class SizeMeasurer {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("=========================================");
        System.out.println(" PENGUKURAN UKURAN PAYLOAD (BYTES) ");
        System.out.println("=========================================\n");

        JsonFlat jsonFlat = DataGenerator.generateJsonFlat(1).get(0);
        UserFlat protoFlat = DataGenerator.generateProtoFlat(1).get(0);

        JsonShallow jsonShallow = DataGenerator.generateJsonShallow(1).get(0);
        UserShallow protoShallow = DataGenerator.generateProtoShallow(1).get(0);

        JsonDeep jsonDeep = DataGenerator.generateJsonDeep(1).get(0);
        UserDeep protoDeep = DataGenerator.generateProtoDeep(1).get(0);

        // Level 1 (Flat)
        byte[] jsonFlatBytes = mapper.writeValueAsBytes(jsonFlat);
        byte[] protoFlatBytes = protoFlat.toByteArray();
        System.out.println("LEVEL 1 (FLAT):");
        System.out.println("- JSON Size     : " + jsonFlatBytes.length + " bytes");
        System.out.println("- Protobuf Size : " + protoFlatBytes.length + " bytes");
        System.out.println("- Protobuf menghemat : " + hitungPersen(jsonFlatBytes.length, protoFlatBytes.length) + "%\n");

        // Level 2 (Shallow)
        byte[] jsonShallowBytes = mapper.writeValueAsBytes(jsonShallow);
        byte[] protoShallowBytes = protoShallow.toByteArray();
        System.out.println("LEVEL 2 (SHALLOW):");
        System.out.println("- JSON Size     : " + jsonShallowBytes.length + " bytes");
        System.out.println("- Protobuf Size : " + protoShallowBytes.length + " bytes");
        System.out.println("- Protobuf menghemat : " + hitungPersen(jsonShallowBytes.length, protoShallowBytes.length) + "%\n");

        // Ukur Level 3 (Deep)
        byte[] jsonDeepBytes = mapper.writeValueAsBytes(jsonDeep);
        byte[] protoDeepBytes = protoDeep.toByteArray();
        System.out.println("LEVEL 3 (DEEP):");
        System.out.println("- JSON Size     : " + jsonDeepBytes.length + " bytes");
        System.out.println("- Protobuf Size : " + protoDeepBytes.length + " bytes");
        System.out.println("- Protobuf menghemat : " + hitungPersen(jsonDeepBytes.length, protoDeepBytes.length) + "%\n");
        
        System.out.println("=========================================");
    }

    private static int hitungPersen(int jsonSize, int protoSize) {
        double selisih = jsonSize - protoSize;
        return (int) ((selisih / jsonSize) * 100);
    }
}