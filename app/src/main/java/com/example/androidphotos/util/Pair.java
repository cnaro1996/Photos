package com.example.androidphotos.util;

import java.io.Serializable;

/**
 * Utility class, a Pair is an object that holds a pair of key and value attributes. A key:value pair.
 * @param <K> The key.
 * @param <V> The value.
 */
public class Pair <K, V> implements Serializable {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public java.lang.String toString() {
        return "[" + key + " , " + value + "]";
    }
}
