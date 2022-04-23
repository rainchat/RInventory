package com.rainchat.rinventory.utils.storage;

import java.util.*;

public interface  Config {

    /**
     * Get all normalized values from the path
     *
     * @param path the path
     * @param deep should we go deeper from the path?
     *
     * @return the values
     */
    default Map<String, Object> getNormalizedValues(String path, boolean deep) {
        Map<String, Object> normalized = new LinkedHashMap<>();
        getValues(path, deep).forEach((k, v) -> normalized.put(k, normalizeObject(v)));
        return normalized;
    }

    /**
     * Get all values from the root path
     *
     * @param deep should we go deeper from the path?
     *
     * @return the values
     */
    default Map<String, Object> getValues(boolean deep) {
        return getValues("", deep);
    }

    /**
     * Get all values from the root path
     *
     * @param deep should we go deeper from the path?
     *
     * @return the values
     */
    default Map<String, Object> getNormalizedValues(boolean deep) {
        return getNormalizedValues("", deep);
    }

    String getName();


    Map<String, Object> getValues(String path, boolean deep);

    /**
     * Normalize the object and its elements if it is a map or a collection
     *
     * @param object the object
     *
     * @return the normalized object
     */
    default Object normalizeObject(Object object) {
        Object normalizedValue = isNormalizable(object) ? normalize(object) : object;
        if (normalizedValue instanceof Map) {
            // noinspection unchecked
            ((Map) normalizedValue).replaceAll((k, v) -> normalizeObject(v));
        } else if (normalizedValue instanceof Collection) {
            List<Object> normalizedList = new ArrayList<>();
            // noinspection unchecked
            ((Collection) normalizedValue).forEach(v -> normalizedList.add(normalizeObject(v)));
            normalizedValue = normalizedList;
        }
        return normalizedValue;
    }

    /**
     * Check if the object is normalizable
     *
     * @param object the object
     *
     * @return true if it is
     */
    boolean isNormalizable(Object object);


    /**
     * Normalize the library-specific object
     *
     * @param object the object
     *
     * @return the normalized object
     */
    Object normalize(Object object);

}
