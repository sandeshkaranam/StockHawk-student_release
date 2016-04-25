package com.sam_chordas.android.stockhawk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kssand on 23-Apr-16.
 */
public class DateHighMain implements Serializable {
    public List<DateHigh> datehigh = new ArrayList<DateHigh>();
    public List<DateHigh> getDatehigh() {
        return datehigh;
    }
}
