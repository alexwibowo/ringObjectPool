package org.isolution.objectpool;

import org.isolution.domain.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RingObjectPoolTest {

    private RingObjectPool<Quote> quotePool;

    @BeforeEach
    void beforeEach() {
        quotePool = new RingObjectPool<>(Quote.class, 10);
    }

    @Test
    void pool_initial_index() {
        assertThat(quotePool.nextRead()).isEqualTo(-1);
        assertThat(quotePool.nextWrite()).isEqualTo(0);
    }

}