package com.mohdali.apd.lib;

import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * A PhoneticDictionaryEntry consists of a Key and one or more Definitions. The
 * Key is a word from the text corpus, while each definition is a phonetic
 * pronuncation generated by the rules.
 * </p>
 * <p>
 * The list of entries and their definitions are expanded to generate the
 * dictionary file used by CMUSphinx.
 * </p>
 * <p>
 * For further information please check the CMUSphinx Tutorial
 * <a href="https://cmusphinx.github.io/wiki/tutorialdict/">Building a Phonetic
 * Dictionary</a>.
 * </p>
 * 
 * @author Mohamed Ali
 */
public class PhoneticDictionaryEntry implements Comparable<PhoneticDictionaryEntry> {

    private String key;
    private ArrayList<String> definitions;

    private Map<String, Character> charMap = (Map<String, Character>) RuleEngine.getProperty("CharMap");
    private Map<String, String> classes = (Map<String, String>) RuleEngine.getProperty("Classes");
    private Map<String, ArrayList<Rule>> rules = (Map<String, ArrayList<Rule>>) RuleEngine.getProperty("Rules");

    public PhoneticDictionaryEntry(String key) {
        this.key = key;
        definitions = new ArrayList<String>();
    }

    public String getKey() {
        return key;
    }

    public ArrayList<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<String> defs) {
        this.definitions = defs;
    }

    public boolean isValid() {
        String c = key.substring(0, 1);
        return (removeDiacritics().length() > 0 && !c.matches(classes.get("D")));
    }

    private String removeDiacritics() {
        return key.replaceAll(classes.get("D"), "");
    }

    public void addDefinition(String def) {
        definitions.add(def.trim());
    }

    public ArrayList<String> generateDefinitions() {
        String[][] map = new String[key.length()][];
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            for (Entry<String, Character> e : charMap.entrySet()) {
                if (c == e.getValue()) {
                    ArrayList<Rule> p = rules.get(e.getKey());
                    // System.out.println(p.name());
                    ArrayList<String> r = new ArrayList<String>();
                    for (Rule rule : p) {
                        if (rule.applies(key, i)) {
                            // System.out.println(i+" "+rule);
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
        searchMap(map);
        return definitions;
    }

    private void searchMap(String[][] map) {
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

    @Override
    public int compareTo(PhoneticDictionaryEntry o) {
        return key.compareTo(o.getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PhoneticDictionaryEntry) {
            PhoneticDictionaryEntry e = (PhoneticDictionaryEntry) o;
            return key.equals(e.getKey());
        } else
            return false;
    }

    @Override
    public String toString() {
        int count = 0;
        String out = "";

        for (String def : definitions) {
            count++;
            if (count == 1)
                out += String.format("%s %s\n", key, def);
            else
                out += String.format("%s(%d) %s\n", key, count, def);
        }

        return out;
    }
}
