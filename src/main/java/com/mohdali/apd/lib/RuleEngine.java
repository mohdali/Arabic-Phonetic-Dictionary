package com.mohdali.apd.lib;

import java.util.ArrayList;
import java.util.Map;

public class RuleEngine
{
    private static Map<String, String> classes;
    private static Map<String, Character> charMap;
    private static Map<String, ArrayList<Rule>> rules;
    private static PhoneticDictionary dictionary;

    private RuleEngine() {

    }

    public static PhoneticDictionary getDictionary() {
        return dictionary;
    }

    public static void setDictionary(PhoneticDictionary dictionary) {
        RuleEngine.dictionary = dictionary;
    }

    public static Map<String, ArrayList<Rule>> getRules() {
        return rules;
    }

    public static void setRules(Map<String, ArrayList<Rule>> rules) {
        RuleEngine.rules = rules;
    }

    public static Map<String,String> getCharClasses() {
        return classes;
    }

    public static void setCharClasses(Map<String, String> classes) {
        RuleEngine.classes = classes;
    }

    public static Map<String, Character> getCharMap() {
        return charMap;
    }

    public static void setCharMap(Map<String, Character> map) {
        charMap = map;
    }
}
