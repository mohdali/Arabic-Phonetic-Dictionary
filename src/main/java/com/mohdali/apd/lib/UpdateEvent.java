/*
 * UpdateEvent.java
 *
 * Created on February 7, 2006, 3:06 PM
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
public class UpdateEvent {
    
    private ArrayList<UpdateEventListener> listeners;
    /** Creates a new instance of UpdateEvent */
    public UpdateEvent() {
        listeners=new ArrayList<UpdateEventListener>();
    }
    
    public void addListener(UpdateEventListener s){
        listeners.add(s);
    }
    
    public void fire(){
        for(UpdateEventListener o: listeners){
            o.actionPerformed();
        }
    }
    
}
