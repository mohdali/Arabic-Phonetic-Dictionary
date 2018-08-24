package com.mohdali.apd.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class PhoneticDictionary extends TreeMap<String, PhoneticDictionaryEntry> {

    public PhoneticDictionaryEntry put(PhoneticDictionaryEntry entry) {
        return this.put(entry.getKey(), entry);
    }

    public void writeTo(Writer writer) throws IOException {
        for (PhoneticDictionaryEntry entry : values()) {
            writer.write(entry.toString());
        }
    }

    public static void ParseInput(BufferedReader reader, PhoneticDictionary dict) throws IOException {
        Map<String, String> classes = RuleEngine.getCharClasses();
        
        Pattern p = Pattern
                .compile("^([" + classes.get("D") + classes.get("L") + "]+)(?:\\([0-9]{1,2}\\))?[ \t]+(.*)$");

        String str = reader.readLine();

        while (str != null) {
            Matcher matcher = p.matcher(str);
            while (matcher.find()) {
                String s = matcher.group(1);
                String def = matcher.group(2);
                PhoneticDictionaryEntry e = new PhoneticDictionaryEntry(s);
                if (dict.containsKey(s))
                    e = dict.get(s);
                else
                    dict.put(e);
                e.addDefinition(def);
            }

            str = reader.readLine();
        }
    }
}