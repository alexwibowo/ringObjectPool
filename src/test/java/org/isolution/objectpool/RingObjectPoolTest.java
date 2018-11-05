package org.isolution.objectpool;

import org.isolution.domain.Quote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RingObjectPoolTest {

    private RingObjectPool<Quote> quotePool;

    @BeforeEach
    void beforeEach() {
        quotePool = new RingObjectPool<>(Quote.class,
                Quote::new,
                10);
    }

    @Test
    void pool_initial_index() {
        assertThat(quotePool.nextRead()).isEqualTo(-1);
        assertThat(quotePool.nextWrite()).isEqualTo(-1);
    }

    @Test
    void should_not_be_able_to_read_an_empty_pool() {
        final NoSuchElementException noSuchElementException = Assertions.assertThrows(NoSuchElementException.class, () ->
            quotePool.nextAvailableForRead()
        );
        assertThat(noSuchElementException.getMessage()).isEqualTo("Not enough element to be read.");
    }

    @Test
    void test_write_to_empty_pool() {
        quotePool.nextAvailableForWrite()
            .setBid(0.75)
            .setAsk(0.76)
            .setSymbol("AUDUSD");

        final Quote quote = quotePool.nextAvailableForRead();
        assertThat(quote.getSymbol()).isEqualTo("AUDUSD");
        assertThat(quote.getBid()).isEqualTo(0.75);
        assertThat(quote.getAsk()).isEqualTo(0.76);
    }

    @Test
    void test_write_several_to_pool() {
        quotePool.nextAvailableForWrite()
                .setBid(0.75)
                .setAsk(0.76)
                .setSymbol("AUDUSD");
        quotePool.nextAvailableForWrite()
                .setBid(0.74)
                .setAsk(0.77)
                .setSymbol("CADUSD");

        final Quote firstQuote = quotePool.nextAvailableForRead();
        assertThat(firstQuote.getSymbol()).isEqualTo("AUDUSD");
        assertThat(firstQuote.getBid()).isEqualTo(0.75);
        assertThat(firstQuote.getAsk()).isEqualTo(0.76);

        final Quote secondQuote = quotePool.nextAvailableForRead();
        assertThat(secondQuote.getSymbol()).isEqualTo("CADUSD");
        assertThat(secondQuote.getBid()).isEqualTo(0.74);
        assertThat(secondQuote.getAsk()).isEqualTo(0.77);
    }

    @Test
    void should_not_be_able_to_read_more_than_what_is_available() {
        quotePool.nextAvailableForWrite()
                .setBid(0.75)
                .setAsk(0.76)
                .setSymbol("AUDUSD");
        assertNotNull(quotePool.nextAvailableForRead());

        final NoSuchElementException noSuchElementException = Assertions.assertThrows(NoSuchElementException.class, () ->
                quotePool.nextAvailableForRead()
        );
        assertThat(noSuchElementException.getMessage()).isEqualTo("Not enough element to be read.");
    }

}