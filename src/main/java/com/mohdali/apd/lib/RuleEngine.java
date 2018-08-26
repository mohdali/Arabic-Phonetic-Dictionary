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
        ArrayList<String> definitions = new ArrayList<String>();

        String[][] map = new String[key.length()][];
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            for (Entry<String, Character> e : charMap.entrySet()) {
                if (c == e.getValue()) {
                    ArrayList<Rule> p = rules.get(e.getKey());
                    ArrayList<String> r = new ArrayList<String>();
                    for (Rule rule : p) {
                        if (rule.applies(key, i)) {
                            r.add(rule.replacement);
                        }
                    }
                    Object[] o = r.toArray();
                    if (o.length > 0) {
                        map[i] = new String[o.length];
                        int j = 0;
                        for (Object s : o)
                            map[i][j++] = (String) s;
                    } else
                        map[i] = new String[] { "" };
                    break;
                }
            }
        }
        
        searchMap(key, definitions, map);

        return definitions;
    }

    private static void searchMap(String key, ArrayList<String> definitions, String[][] map) {
        int l = key.length();
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
                } else
                    k++;
            }
            i[k] = 0;
            k--;
        }
    }
}
