package io.github.indesil.beren.test.objects;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.StringUtils.repeat;

class ObjectUtils {
    private static final char ANY_CHAR = 'X';
    static final int WORD_SIZE = 3;

    static CharSequence charSequence(int size) {
        return repeat(ANY_CHAR, size);
    }

    static Map<String, String> generateMap(int collectionsSize) {
        return IntStream.range(0, collectionsSize).mapToObj(String::valueOf).limit(collectionsSize).collect(toMap(identity(), identity()));
    }

    static List<Integer> generateCollection(int collectionsSize) {
        return IntStream.range(0, collectionsSize).boxed().collect(Collectors.toList());
    }

    static Double[] randomDoubleWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, RandomUtils::nextDouble, Double[]::new);
    }

    static Float[] randomFloatWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, RandomUtils::nextFloat, Float[]::new);
    }

    static Long[] randomLongWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, RandomUtils::nextLong, Long[]::new);
    }

    static Integer[] randomIntWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, RandomUtils::nextInt, Integer[]::new);
    }

    static Byte[] randomByteWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, () -> (byte) nextInt(0, Byte.MAX_VALUE), Byte[]::new);
    }

    static Boolean[] randomBooleanWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, RandomUtils::nextBoolean, Boolean[]::new);
    }

    static Short[] randomShortWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, () -> (short) nextInt(0, Short.MAX_VALUE), Short[]::new);
    }

    static Character[] randomCharWrapperArray(int collectionsSize) {
        return randomArray(collectionsSize, () -> ANY_CHAR, Character[]::new);
    }

    static Object[] randomObjectArray(int collectionsSize) {
        return randomArray(collectionsSize, Object::new, Object[]::new);
    }

    static String[] randomStringArray(int collectionsSize) {
        return randomArray(collectionsSize, () -> randomAlphabetic(WORD_SIZE), String[]::new);
    }

    private static <E> E[] randomArray(int size, Supplier<E> generator, IntFunction<E[]> arrayConstructor) {
        return Stream.generate(generator).limit(size).toArray(arrayConstructor);
    }
}
