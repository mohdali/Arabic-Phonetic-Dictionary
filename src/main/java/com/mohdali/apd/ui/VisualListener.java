/*
 * VisualEvent.java
 *
 * Created on February 6, 2006, 7:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mohdali.apd.ui;
import com.mohdali.apd.lib.*;
/**
 *
 * @author Ali
 */
public class VisualListener implements ProgressEventListener,StatusEventListener{
    private MainFrame frame;
    private int maximum;
    
    public VisualListener(MainFrame frame){
        this.frame=frame;
    }
    
    public void actionPerformed(String message){
        frame.statusLabel.setText(message);    
    }
    
    public void actionPerformed(String message,int count){
        frame.statusLabel.setText(message);
        frame.progressBar.setValue(count);
        if(count==maximum){
            frame.progressBar.setVisible(false);
        }else
            frame.progressBar.setVisible(true);
    }
    
    public void setMaximum(int count){
       this.maximum=count;
       frame.progressBar.setMaximum(count);
    }
}
