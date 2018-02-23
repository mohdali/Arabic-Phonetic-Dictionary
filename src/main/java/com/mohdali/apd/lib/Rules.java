package com.mohdali.apd.lib;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Rules{
    private static Map<String,ArrayList<Rule>> rules= new TreeMap<String,ArrayList<Rule>>();
    private static Map<String,Character> charMap;
    private static Map<String,String> classes;

    private static Pattern rule = Pattern.compile("^[ \\t]*(?!;)(.*)->(.*)$");
    private static Pattern letter = Pattern.compile("(?<!<)[A-Z_]+(?!>)");
    private static Pattern className = Pattern.compile("<([a-zA-Z]+)>");
    private static Pattern ruleLetter= Pattern.compile("^("+letter.pattern()+"):");

    public static Map<String,ArrayList<Rule>> get(){
        charMap= (Map<String,Character>) ConfigManager.getProperty("CharMap");
        classes= (Map<String,String>) ConfigManager.getProperty("Classes");
        InputStream fin = FileManager.read("rules.list");
        parseFile(fin);
        return rules;
    }

    public static void parseFile(InputStream fin){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String str=reader.readLine();
            ArrayList<Rule> currentRule=new ArrayList<Rule>();;
            while(str!=null){
                Matcher m = ruleLetter.matcher(str);
                Matcher m1 = rule.matcher(str);
                if(m.matches()){
                    String k =m.group(1);
                    if(rules.containsKey(k)){
                        currentRule=rules.get(k);
                    }else{
                        currentRule=new ArrayList<Rule>();
                        rules.put(k,currentRule);
                    }
                }
                else if(m1.matches()){
                    String p = m1.group(1);
                    String r = m1.group(2);
                    Matcher m2=letter.matcher(p);
                    StringBuffer sb = new StringBuffer();
                    while(m2.find()){
                        String c;
                        String k=m2.group();
                        if(charMap.containsKey(k))
                            c = charMap.get(k)+"";
                        else
                            c="";
                        m2.appendReplacement(sb,c);
                    }
                    m2.appendTail(sb);
                    p= sb.toString();
                    Matcher m3=className.matcher(p);
                    sb = new StringBuffer();
                    while(m3.find()){
                        String c;
                        String t = m3.group(1);
                        if(classes.containsKey(t))
                            c = new String(classes.get(t));
                        else
                            c="";
                        m3.appendReplacement(sb,c);
                    }
                    m3.appendTail(sb);
                    p=sb.toString();
                    p=p.replaceAll(" ","");
                    r=r.replaceAll("[\\*]","");
                    r=r.trim();
                    Rule rule=new Rule(Pattern.compile(p),r);
                    currentRule.add(rule);
                }
                str=reader.readLine();
            }
        }catch(IOException e){}
    }
}

