/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import systree.EventAdapter;

/**
 *
* @author Abed MAATALLA
 */

public class EventStartEndAdapter {
    public EventAdapter start;
    public EventAdapter end;
    public double startTime;
    public double endTime;
    public EventStartEndAdapter() {
    }

    public EventStartEndAdapter(EventAdapter start, EventAdapter end) {
        this.start = start;
        this.end = end;
    }

    public EventStartEndAdapter(EventAdapter start) {
        this.start = start;
    }

    public EventAdapter getStart() {
        return start;
    }

    public void setStart(EventAdapter start) {
        this.start = start;
    }

    public EventAdapter getEnd() {
        return end;
    }

    public void setEnd(EventAdapter end) {
        this.end = end;
    }

}