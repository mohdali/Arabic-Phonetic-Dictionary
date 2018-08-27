package com.mohdali.apd.lib;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class RuleEngine {
    private static Map<String, String> classes;
    private static Map<String, Character> charMap;
    private static Map<String, ArrayList<Rule>> rules;
    private static PhoneticDictionary dictionary;

    private RuleEngine() {
        dictionary = new PhoneticDictionary();
    }

    public static PhoneticDictionary getDictionary() {
        return dictionary;
    }

    public static void setDictionary(PhoneticDictionary dictionary) {
        if (dictionary != null) {
            RuleEngine.dictionary = dictionary;
        }
    }

    public static void clearDictionary() {
        if (dictionary != null) {
            dictionary.clear();
        }
    }

    public static Map<String, ArrayList<Rule>> getRules() {
        return rules;
    }

    public static void setRules(Map<String, ArrayList<Rule>> rules) {
        RuleEngine.rules = rules;
    }

    public static Map<String, String> getCharClasses() {
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

    public static ArrayList<String> generateDefinitions(String key) {
        String[][] map = new String[key.length()][];

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            for (Entry<String, Character> e : charMap.entrySet()) {
                if (c == e.getValue()) {

                    ArrayList<Rule> charRules = rules.get(e.getKey());
                    ArrayList<String> replacements = new ArrayList<String>();

                    for (Rule rule : charRules) {
                        if (rule.applies(key, i)) {
                            replacements.add(rule.replacement);
                        }
                    }

                    if (replacements.size() > 0) {
                        map[i] = new String[replacements.size()];
                        int j = 0;
                        for (String s : replacements)
                            map[i][j++] = s;
                    } else {
                        map[i] = new String[] { "" };
                    }
                    break;
                }
            }
        }
        return searchMap(map);
    }

    private static ArrayList<String> searchMap(String[][] map) {

        ArrayList<String> definitions = new ArrayList<String>();

        int l = map.length;

        String[] v = new String[l];

        int[] i = new int[l];

        for (int j = 0; j < l; j++)
            i[j] = 0;

        int k = 0;

        while (k >= 0) {
            while (i[k] < map[k].length) {
                v[k] = map[k][i[k]];
                i[k]++;

                if (k == l - 1) {
                    StringBuffer buff = new StringBuffer();

                    for (String m : v)
                        buff.append(m + (!m.equals("") ? " " : ""));

                    definitions.add(buff.toString().trim());
                } else {
                    k++;
                }
            }

            i[k] = 0;

            k--;
        }

        return definitions;
    }
}
