/*
 * ProgressEvent.java
 *
 * Created on February 6, 2006, 6:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mohdali.apd.lib;

import java.util.ArrayList;

/**
 *
 * @author Ali
 */
public class ProgressEvent {

    private ArrayList<ProgressEventListener> listeners;
    private int maximum;

    /** Creates a new instance of ProgressEvent */
    public ProgressEvent() {
        listeners = new ArrayList<ProgressEventListener>();
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public void addListener(ProgressEventListener s) {
        listeners.add(s);
    }

    public void fire(String message, int value) {
        for (ProgressEventListener o : listeners) {
            o.setMaximum(maximum);
            o.actionPerformed(message, value);
        }
    }
}
