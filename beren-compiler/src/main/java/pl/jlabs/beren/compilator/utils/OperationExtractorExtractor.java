package pl.jlabs.beren.compilator.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;

public class OperationExtractorExtractor {

    public static List<String> getParams(String entry) {
        int openingBracket = entry.indexOf("(");
        int closingBracket = entry.lastIndexOf(")");

        if(openingBracket == -1 && closingBracket == -1) {
            return new ArrayList<>();
        }

        if(openingBracket == -1 || closingBracket == -1 && openingBracket > closingBracket) {
            throw new IllegalArgumentException("Invalid param entry " + entry);
        }

        String paramsString = entry.substring(openingBracket + 1, closingBracket);
        return Stream.of(split(paramsString, ",")).map(String::trim).collect(toList());
    }

    public static String extractOperationRef(String entry) {
        int openingBracket = entry.indexOf("(");
        if(openingBracket == -1) {
            return entry;
        }

        return entry.substring(0, openingBracket);
    }
}
