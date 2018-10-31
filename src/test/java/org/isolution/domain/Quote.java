package org.isolution.domain;

public class Quote {

    private double bid;

    private double ask;

    private String symbol;

    public double getBid() {
        return bid;
    }

    public Quote setBid(double bid) {
        this.bid = bid;
        return this;
    }

    public double getAsk() {
        return ask;
    }

    public Quote setAsk(double ask) {
        this.ask = ask;
        return this;
    }

    public Quote setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String symbol() {
        return symbol;
    }
}
