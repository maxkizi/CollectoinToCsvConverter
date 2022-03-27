package org.eagleinvsys.test.converters.impl;

import lombok.AllArgsConstructor;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.Collection;

@AllArgsConstructor
public class ConvertibleCollectionImpl implements ConvertibleCollection {

    private Collection<String> headers;
    private Iterable<ConvertibleMessage> records;

    @Override
    public Collection<String> getHeaders() {
        return headers;
    }

    @Override
    public Iterable<ConvertibleMessage> getRecords() {
        return records;
    }


}
