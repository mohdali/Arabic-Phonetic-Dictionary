package com.mohdali.apd.lib;

import java.util.Map;
import java.util.ArrayList;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    private static PhoneticDictionary dict;
    private static Pattern pattern;
    public static StatusEvent statusEvent = new StatusEvent();
    public static ProgressEvent progressEvent = new ProgressEvent();
    public static UpdateEvent updateEvent = new UpdateEvent();
    public static boolean stopThreads = false;

    public static void importFiles(File dir, String encoding) {
        dict = RuleEngine.getDictionary();
        File[] files = dir.listFiles();
        statusEvent.fire("Loading Files");
        progressEvent.setMaximum(getNumFiles(dir));
        int count = 0;
        for (File f : files) {
            if (!stopThreads) {
                progressEvent.fire("Loading " + f.getName(), ++count);
                parseFile(f, encoding);
            }
        }
        RuleEngine.setDictionary(dict);
        statusEvent.fire("Done");
        updateEvent.fire();

    }

    public static void loadConfig() {
        Map<String, Character> map = CharacterMapParser.get();
        RuleEngine.setCharMap(map);
        Map<String, String> classes = CharacterClassParser.get();
        pattern = Pattern.compile("(" + classes.get("D") + "|" + classes.get("L") + ")+");
        RuleEngine.setCharClasses(classes);
        Map<String, ArrayList<Rule>> rules = RuleParser.get();
        RuleEngine.setRules(rules);
        dict = new PhoneticDictionary();
        RuleEngine.setDictionary(dict);
    }

    public static int getNumFiles(File dir) {
        return dir.listFiles().length;
    }

    public static void parseFile(File f, String encoding) {
        dict = RuleEngine.getDictionary();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), encoding));
            String str = reader.readLine();
            while (str != null) {
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    String s = matcher.group();
                    PhoneticDictionaryEntry e = new PhoneticDictionaryEntry(s);
                    if (e.isValid()) {
                        e.setDefinitions(RuleEngine.generateDefinitions(s));
                        dict.put(s, e);
                    }
                }
                str = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readDict(File f, String encoding) {
        dict = RuleEngine.getDictionary();
        try {
            statusEvent.fire("Loading Dictionary: " + f.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), encoding));

            PhoneticDictionary.ParseInput(reader, dict);
            
            reader.close();
            statusEvent.fire("Done, loaded " + dict.size() + " entries");
            updateEvent.fire();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateList() {
        updateEvent.fire();
    }

    public static void clearDict() {
        RuleEngine.clearDictionary();
    }

    public static void writeToFile(File f, String encoding) {
        dict = RuleEngine.getDictionary();
        try {

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), encoding));

            dict.writeTo(writer);

            writer.close();
        } catch (IOException e) {
        }

    }
}
