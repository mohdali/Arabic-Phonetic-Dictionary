package com.mohdali.apd.lib;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CharacterMapParser{
    
    private static Map<String,Character> map= new HashMap<String,Character>();
    private static Pattern pattern = Pattern.compile("^[ \\t]*([A-Z_]+)[ \\t]+0x([0-9a-f]+)$");

    public static Map<String,Character> get(){
        InputStream fin = FileManager.read("alphabet.list");
        parseFile(fin);
        return map;
    }

    private static void parseFile(InputStream fin){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String str=reader.readLine();
            while(str!=null){
                Matcher m = pattern.matcher(str);
                if(m.matches()){
                    String k = m.group(1);
                    char v = (char) Integer.parseInt(m.group(2),16);
                    map.put(k,v);
                }
                str=reader.readLine();
            }
        }catch(IOException e){}
    }    
}