package org.eagleinvsys.test.converters;


import lombok.SneakyThrows;
import org.eagleinvsys.test.converters.exception.NotEqualsHeadersException;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.eagleinvsys.test.converters.util.TestUtils.AGE;
import static org.eagleinvsys.test.converters.util.TestUtils.FIRST_NAME;
import static org.eagleinvsys.test.converters.util.TestUtils.LAST_NAME;
import static org.eagleinvsys.test.converters.util.TestUtils.getListFromCsv;

@SpringBootTest
class StandardCsvConverterTests {


    @Autowired
    private StandardCsvConverter standardCsvConverter;
    @Value("${fullFileName}")
    private String fullFileName;


    @Test
    @SneakyThrows
    void shouldConvert() {
        List<Map<String, String>> records = List.of(
                Map.of(FIRST_NAME, "firstName1", LAST_NAME, "lastName1", AGE, "age1"),
                Map.of(FIRST_NAME, "firstName2", LAST_NAME, "lastName2", AGE, "age2")
        );

        File file = new File(fullFileName);
        standardCsvConverter.convert(records, new FileOutputStream(file));
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(Files.readAllBytes(file.toPath()).length > 0);

        List<List<String>> listFromCsv = getListFromCsv(file, standardCsvConverter.getCsvConverter().getSeparator());
        List<String> headers = listFromCsv.get(0);
        Assertions.assertTrue(headers.containsAll(records.get(0).keySet()) && headers.containsAll(records.get(0).keySet()));
        Assertions.assertTrue(records.get(0).values().containsAll(listFromCsv.get(1)));
        Assertions.assertTrue(records.get(1).values().containsAll(listFromCsv.get(2)));
    }

    @Test
    @SneakyThrows
    void shouldThrowNotEqualsHeadersException() {
        List<Map<String, String>> records = List.of(
                Map.of(FIRST_NAME, "firstName1", LAST_NAME, "lastName1"),
                Map.of(FIRST_NAME, "firstName2", LAST_NAME, "lastName2", AGE, "age2")
        );
        File file = new File(fullFileName);
        Assertions.assertThrows(NotEqualsHeadersException.class, () -> standardCsvConverter.convert(records, new FileOutputStream(file)));
    }
}