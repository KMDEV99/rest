package com.enigma.rest.util;

import com.enigma.rest.exception.InvalidSearchQueryException;
import org.springframework.data.domain.Sort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortHandler {

    public static Sort validateSortCriteria(String sortCriteria) throws InvalidSearchQueryException {
        Pattern pattern = Pattern.compile("(\\w+)(:)(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);
        Sort sortBy = null;

        if (sortCriteria != null) {
            Matcher matcher = pattern.matcher(sortCriteria);

            if (!matcher.find()) {
                throw new InvalidSearchQueryException(String.format("Proper query syntax: %s", "?sort=name:asc"));
            }
            if (matcher.group(3).equalsIgnoreCase("asc")) {
                sortBy = Sort.by(Sort.Direction.ASC, matcher.group(1));
            } else if (matcher.group(3).equalsIgnoreCase("desc")) {
                sortBy = Sort.by(Sort.Direction.DESC, matcher.group(1));
            }
        }
        return sortBy;
    }
}
