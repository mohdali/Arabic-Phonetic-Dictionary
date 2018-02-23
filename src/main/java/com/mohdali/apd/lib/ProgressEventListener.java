/*
 * ProgressEventListener.java
 *
 * Created on February 6, 2006, 6:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mohdali.apd.lib;

/**
 *
 * @author Ali
 */
public interface ProgressEventListener {
    public void actionPerformed(String message, int value);

    public void setMaximum(int value);
}
