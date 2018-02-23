/*
 * DictionaryUpdateListener.java
 *
 * Created on February 7, 2006, 3:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mohdali.apd.ui;
import com.mohdali.apd.lib.*;
import java.util.Map;

/**
 *
 * @author Ali
 */
public class DictionaryUpdateListener implements UpdateEventListener{
    
    private MainFrame frame;
    /** Creates a new instance of DictionaryUpdateListener */
    public DictionaryUpdateListener(MainFrame frame) {
        this.frame=frame;
    }
    
    public void actionPerformed(){
        Map<String,PhoneticDictionaryEntry> dict = (Map<String,PhoneticDictionaryEntry>) ConfigManager.getProperty("Dictionary");
        if(dict!=null){
            frame.entryDefField.setText("");
            frame.dictionaryEntryField.setText("");
            frame.statusLabel.setText("Loading dictionary, please wait");
            frame.dictionaryList.setModel(new DictionaryListModel(dict));
            frame.statusLabel.setText("Done;"+ dict.size()+" Elements are in the dictionary");
        }
    }
    
}
