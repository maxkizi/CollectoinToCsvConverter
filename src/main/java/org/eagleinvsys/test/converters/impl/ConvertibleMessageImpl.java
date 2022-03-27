package org.eagleinvsys.test.converters.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConvertibleMessageImpl implements ConvertibleMessage {

    private Map<String, String> elements = new HashMap<>();


    @Override
    public String getElement(String elementId) {
        return elements.get(elementId);
    }
}
