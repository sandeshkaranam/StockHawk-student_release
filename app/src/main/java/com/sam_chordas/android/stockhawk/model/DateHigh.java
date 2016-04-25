package com.sam_chordas.android.stockhawk.model;

/**
 * Created by kssand on 23-Apr-16.
 */
public class DateHigh {
    private String quoteDate;
    private String quoteHighValue;

    public void setQuoteDate(String quoteDate) {
        this.quoteDate = quoteDate;
    }

    public String getQuoteHighValue() {
        return quoteHighValue;
    }

    public String getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteHighValue(String quoteHighValue) {
        this.quoteHighValue = quoteHighValue;
    }
}
