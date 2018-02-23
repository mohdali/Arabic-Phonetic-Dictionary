package com.mohdali.apd.lib;

import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
public class FileManager
{
    public static String conf="/conf/";
    
    public static InputStream read(String file){
       // try{
            return FileManager.class.getResourceAsStream(conf+file);
        //}catch(URISyntaxException e){return null;}
    }
}