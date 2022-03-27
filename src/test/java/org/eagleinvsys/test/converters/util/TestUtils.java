package org.eagleinvsys.test.converters.util;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TestUtils {
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String AGE = "Age";
    public static final String CITY = "City";

    private TestUtils() {
    }


    @SneakyThrows
    public static List<List<String>> getListFromCsv(File file, String separator) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(separator);
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }
}
