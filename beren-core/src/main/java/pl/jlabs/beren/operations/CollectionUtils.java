package pl.jlabs.beren.operations;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;

public class CollectionUtils {

    public static final String COLLECTION_OR_EMPTY = "collectionOrEmpty";
    public static <E> Collection<E> collectionOrEmpty(Collection<E> col) {
        return col != null ? col : emptyList();
    }

    public static final String MAP_OR_EMPTY = "mapOrEmpty";
    public static <K,V> Map<K, V> mapOrEmpty(Map<K, V> map) {
        return map != null ? map : new HashMap<>();
    }
}
