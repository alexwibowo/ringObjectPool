package org.isolution.objectpool;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

public final class RingObjectPool<T> {

    private T[] pool;

    private int nextRead;

    private int nextWrite;

    public RingObjectPool(final @NotNull Class<T> klazz,
                          final int poolSize) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException("Object pool size has to be greater than 0");
        }
        //noinspection unchecked
        pool = (T[]) Array.newInstance(klazz, poolSize);
        nextWrite = 0;
        nextRead = -1;
    }

    int nextRead() {
        return nextRead;
    }

    int nextWrite() {
        return nextWrite;
    }

}
