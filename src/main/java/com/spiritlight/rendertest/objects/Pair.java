package com.spiritlight.rendertest.objects;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface Pair<K, V> {
    K getKey();

    V getValue();

    @Contract(value = "_, _ -> new", pure = true) @Unmodifiable
    static <K, V> @NotNull Pair<K, V> of(K key, V value) {
        return new Pair<>() {
            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }
        };
    }
}
