package com.mohdali.apd.lib;

import java.io.InputStream;

public class FileManager {
    public static String conf = "/conf/";

    public static InputStream read(String file) {       
        return FileManager.class.getResourceAsStream(conf + file);        
    }
}