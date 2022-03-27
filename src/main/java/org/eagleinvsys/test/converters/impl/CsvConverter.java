package org.eagleinvsys.test.converters.impl;

import lombok.Getter;
import lombok.SneakyThrows;
import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.stream.Collectors;

@Component
@Getter
public class CsvConverter implements Converter {
    @Value("${separator}")
    private String separator;

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    @SneakyThrows
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            writer.write(collectionToConvert.getHeaders().stream().collect(Collectors.joining(separator)));
            writer.newLine();
            for (ConvertibleMessage msg : collectionToConvert.getRecords()) {
                writer.write(collectionToConvert.getHeaders().stream().map(msg::getElement).collect(Collectors.joining(separator)));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}