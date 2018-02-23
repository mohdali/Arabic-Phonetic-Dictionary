/*
 * StatusEvent.java
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
public class StatusEvent {
    private ArrayList<StatusEventListener> listeners;

    /** Creates a new instance of StatusEvent */
    public StatusEvent() {
        listeners = new ArrayList<StatusEventListener>();
    }

    public void addListener(StatusEventListener s) {
        listeners.add(s);
    }

    public void fire(String message) {
        for (StatusEventListener o : listeners) {
            o.actionPerformed(message);
        }
    }
}
