package org.eagleinvsys.test.converters;

import lombok.SneakyThrows;
import org.eagleinvsys.test.converters.impl.ConvertibleCollectionImpl;
import org.eagleinvsys.test.converters.impl.ConvertibleMessageImpl;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.eagleinvsys.test.converters.util.TestUtils.AGE;
import static org.eagleinvsys.test.converters.util.TestUtils.CITY;
import static org.eagleinvsys.test.converters.util.TestUtils.FIRST_NAME;
import static org.eagleinvsys.test.converters.util.TestUtils.LAST_NAME;

@SpringBootTest
class CsvConverterTests {

    @Autowired
    private CsvConverter converter;
    @Value("${fullFileName}")
    private String fullFileName;

    private static ConvertibleCollection convertableCollection;
    private static Set<String> headers;
    private static ConvertibleMessageImpl message1;
    private static ConvertibleMessageImpl message2;

    @BeforeAll
    static void beforeAll() {
        headers = Set.of(FIRST_NAME, LAST_NAME, AGE, CITY);

        //FirstMessage
        message1 = new ConvertibleMessageImpl(Map.of(
                FIRST_NAME, "firstName1",
                LAST_NAME, "lastName1",
                AGE, "age1",
                CITY, "city1"));

        //SecondMessage
        message2 = new ConvertibleMessageImpl(Map.of(
                FIRST_NAME, "firstName2",
                LAST_NAME, "lastName2",
                AGE, "age2",
                CITY, "city2"
        ));
        convertableCollection = new ConvertibleCollectionImpl(headers, List.of(message1, message2));
    }

    @Test
    @SneakyThrows
    void shouldConvert() {
        File file = new File(fullFileName);

        converter.convert(convertableCollection, new FileOutputStream(file));
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(Files.readAllBytes(file.toPath()).length > 0);

        List<List<String>> listFromCsv = TestUtils.getListFromCsv(file, converter.getSeparator());
        Assertions.assertTrue(headers.containsAll(listFromCsv.get(0)));
        Assertions.assertTrue(message1.getElements().values().containsAll(listFromCsv.get(1)));
        Assertions.assertTrue(message2.getElements().values().containsAll(listFromCsv.get(2)));
    }
}