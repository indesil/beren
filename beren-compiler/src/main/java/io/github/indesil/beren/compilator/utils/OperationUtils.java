package io.github.indesil.beren.compilator.utils;

import com.squareup.javapoet.ClassName;
import io.github.indesil.beren.compilator.parser.IterationType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.tuple.Pair.of;

public class OperationUtils {
    private static final Pattern APOSTROPHE_PATTERN = Pattern.compile("((?<!\\\\)['])");
    private static final String SHARP = "#";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";

    public static ClassName extractClassName(String operationCall) {
        int lastDot = operationCall.lastIndexOf('.');
        String classWithPackage = operationCall.substring(0, lastDot);
        lastDot = classWithPackage.lastIndexOf('.');
        return ClassName.get(classWithPackage.substring(0, lastDot), classWithPackage.substring(lastDot + 1));
    }

    public static Pair<IterationType, String> parseOperation(String operation) {
        if(operation.startsWith(SHARP)) {
            return parseIterationOperation(operation);
        }

        return of(IterationType.NONE, operation);
    }

    private static Pair<IterationType, String> parseIterationOperation(String operation) {
        int openingBracket = operation.indexOf(LEFT_BRACKET);
        if(openingBracket == -1 || !operation.endsWith(RIGHT_BRACKET) || operation.length() <= 3) {
            return null;
        }

        String operationPrefix = operation.substring(0, openingBracket);
        IterationType iterationType = IterationType.ofPrefix(operationPrefix);
        if(iterationType != null) {
            return Pair.of(iterationType, operation.substring(openingBracket + 1, operation.length() - 1));
        }

        return null;
    }

    public static String strapFromParams(String operation) {
        int openingBracket = operation.indexOf(LEFT_BRACKET);
        if(openingBracket > -1) {
            return operation.substring(0, openingBracket);
        }

        return operation;
    }

    public static List<String> parseParams(String entry, ProcessingFacade processingFacade) {
        String extractedParams = extractParams(entry);
        if(extractedParams == null) {
            processingFacade.error(ErrorMessages.INVALID_ITERATION_OPERATION, entry);
            return emptyList();
        }
        String escapedEntry = APOSTROPHE_PATTERN.matcher(extractedParams)
                .replaceAll("\"")
                .replaceAll("\\\\'", "'");
        Scanner scanner = new Scanner(escapedEntry);
        scanner.useDelimiter(",");

        List<String> args = new ArrayList<>();
        while (scanner.hasNext()) {
            String value = scanner.next().trim();
            if(value.startsWith("[")) {
                args.add(createListArgs(value, scanner));
            } else {
                args.add(value);
            }
        }
        return args;
    }

    public static List<String> getParams(String entry) {
        String paramsString = extractParams(entry);
        if(paramsString == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.INVALID_ITERATION_OPERATION,  entry));
        }
        return Stream.of(split(paramsString, ",")).map(String::trim).collect(toList());
    }

    public static String extractMethodName(String entry) {
        int openingBracket = entry.indexOf(LEFT_BRACKET);
        int lastDot = entry.lastIndexOf('.');
        return entry.substring(lastDot + 1, openingBracket);
    }

    private static String createListArgs(String value, Scanner scanner) {
        StringBuilder sb = new StringBuilder(value);
        while(scanner.hasNext() && !value.endsWith("]")) {
            value = scanner.next();
            sb.append(",").append(value);
        }
        return sb.toString().replace("[",LEFT_BRACKET).replace("]", RIGHT_BRACKET);
    }

    private static String extractParams(String entry) {
        Pair<IterationType, String> operationEntry = parseOperation(entry);
        if(operationEntry == null) {
            return null;
        }

        String extractedEntry = operationEntry.getValue();
        int openingBracket = extractedEntry.indexOf(LEFT_BRACKET);
        int closingBracket = extractedEntry.lastIndexOf(RIGHT_BRACKET);

        if(openingBracket == -1 && closingBracket == -1) {
            return "";
        }

        if(openingBracket == -1 || closingBracket == -1 || openingBracket > closingBracket) {
            return null;
        }
        return extractedEntry.substring(openingBracket + 1, closingBracket);
    }
}
