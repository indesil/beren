package pl.jlabs.beren.compilator.utils;

import com.squareup.javapoet.ClassName;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;

public class OperationUtils {
    private static final Pattern APOSTROPHE_PATTERN = Pattern.compile("'");

    public static ClassName extractClassName(String operationCall) {
        int lastDot = operationCall.lastIndexOf('.');
        String classWithPackage = operationCall.substring(0, lastDot);
        lastDot = classWithPackage.lastIndexOf('.');
        return ClassName.get(classWithPackage.substring(0, lastDot), classWithPackage.substring(lastDot + 1));
    }

    public static String strapFromParams(String operation) {
        int openingBracket = operation.indexOf("(");
        if(openingBracket > -1) {
            return operation.substring(0, openingBracket);
        }

        return operation;
    }

    public static List<String> parseParams(String entry) {
        String escapedEntry = APOSTROPHE_PATTERN.matcher(extractParams(entry)).replaceAll("\"");
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

    private static String createListArgs(String value, Scanner scanner) {
        //to moze doprowadzic do bledy jezeli ktos uzyje swojego asList ale raczej maly priorytet
        StringBuilder sb = new StringBuilder(value);
        while(scanner.hasNext() && !value.endsWith("]")) {
            value = scanner.next();
            sb.append("," + value);
        }
        return sb.toString().replace("[","(").replace("]", ")");
    }

    public static List<String> getParams(String entry) {
        String paramsString = extractParams(entry);
        return Stream.of(split(paramsString, ",")).map(String::trim).collect(toList());
    }

    private static String extractParams(String entry) {
        int openingBracket = entry.indexOf("(");
        int closingBracket = entry.lastIndexOf(")");

        if(openingBracket == -1 && closingBracket == -1) {
            return "";
        }

        if(openingBracket == -1 || closingBracket == -1 && openingBracket > closingBracket) {
            throw new IllegalArgumentException("Invalid param entry " + entry);
        }
        return entry.substring(openingBracket + 1, closingBracket);
    }

    public static String extractOperationRef(String entry) {
        int openingBracket = entry.indexOf("(");
        if(openingBracket == -1) {
            return entry;
        }

        return entry.substring(0, openingBracket);
    }

    public static String extractMethodName(String entry) {
        int openingBracket = entry.indexOf("(");
        int lastDot = entry.lastIndexOf('.');
        return entry.substring(lastDot + 1, openingBracket);
    }
}
