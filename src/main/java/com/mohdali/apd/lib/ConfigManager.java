package com.mohdali.apd.lib;

import java.util.HashMap;

public class ConfigManager
{
    private static HashMap<String,Object> config = new HashMap<String,Object>();

    public static void setProperty(String name,Object obj){
        config.put(name,obj);
    }

    public static Object getProperty(String name){
        return config.get(name);
    }
}
