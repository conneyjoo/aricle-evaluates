package com.xuehai.ae.utils;

public class ObjectUtils {

    public ObjectUtils() {
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String toString(Object obj, String nullStr) {
        return obj == null ? nullStr : obj.toString();
    }
}
