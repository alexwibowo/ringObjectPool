package org.isolution.objectpool;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public final class RingObjectPool<T> {

    private T[] pool;

    private int readIndex;

    private int writeIndex;

    public RingObjectPool(final @NotNull Class<T> klazz,
                          Supplier<T> factory,
                          final int poolSize) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException("Object pool size has to be greater than 0");
        }
        //noinspection unchecked
        pool = (T[]) Array.newInstance(klazz, poolSize);
        for (int i = 0; i < poolSize; i++) {
            pool[i] = factory.get();
        }
        writeIndex = -1;
        readIndex = -1;
    }

    int nextRead() {
        return readIndex;
    }

    int nextWrite() {
        return writeIndex;
    }

    /**
     * @return next object that can be written to, from the pool
     */
    public T nextAvailableForWrite() {
        return pool[++writeIndex];
    }

    /**
     * @return next object available for reading
     * @throws NoSuchElementException when there is not enough element to be read
     */
    public T nextAvailableForRead() {
        if (readIndex < writeIndex) {
            readIndex++;
            return pool[readIndex];
        } else {
            throw new NoSuchElementException("Not enough element to be read.");
        }
    }
}
