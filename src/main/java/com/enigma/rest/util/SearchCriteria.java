package com.enigma.rest.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public void setValue(Object value) {
        this.value = value;
    }
}
