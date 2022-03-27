package org.eagleinvsys.test.converters.impl;

import lombok.Getter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;
import org.eagleinvsys.test.converters.StandardConverter;
import org.eagleinvsys.test.converters.exception.NotEqualsHeadersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Getter
public class StandardCsvConverter implements StandardConverter {

    private final CsvConverter csvConverter;

    @Autowired
    public StandardCsvConverter(CsvConverter csvConverter) {
        this.csvConverter = csvConverter;
    }

    /**
     * Converts given {@link List<Map>} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format. All maps must have the same set of keys
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(List<Map<String, String>> collectionToConvert, OutputStream outputStream) {
        List<ConvertibleMessage> records = collectionToConvert.stream().map(record -> new ConvertibleMessageImpl(record)).collect(Collectors.toList());
        ConvertibleCollection convertableCollection = new ConvertibleCollectionImpl(getHeaders(collectionToConvert), records);
        csvConverter.convert(convertableCollection, outputStream);
    }

    private Set<String> getHeaders(List<Map<String, String>> collectionToConvert) {
        if (!(collectionToConvert.stream().map(record -> record.keySet()).distinct().count() <= 1)) {
            throw new NotEqualsHeadersException();
        }
        return collectionToConvert.get(0).keySet();
    }
}