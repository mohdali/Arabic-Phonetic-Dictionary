package com.mohdali.apd.lib;

import java.util.TreeMap;
import java.util.Map;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CharacterClassParser {
    private static Map<String, String> classes = new TreeMap<String, String>();
    private static Map<String, Character> charMap;

    private static Pattern comment = Pattern.compile("^[ \\t]*;");
    private static Pattern className = Pattern.compile("<([a-zA-Z]+)>");
    private static Pattern classDef = Pattern.compile("[^\\s].+");
    private static Pattern letter = Pattern.compile("[A-Z_]+");
    private static Pattern defLine = Pattern
            .compile("^[ \\t]*" + className.pattern() + "[ \\t]*(" + classDef.pattern() + ")$");

    public static Map<String, String> get() {
        charMap = RuleEngine.getCharMap();
        InputStream fin = FileManager.read("classes.list");
        parseFile(fin);
        return classes;
    }

    private static void parseFile(InputStream fin) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String str = reader.readLine();
            while (str != null) {
                if (!str.matches(comment.pattern())) {
                    Matcher m1 = defLine.matcher(str);
                    if (m1.matches()) {
                        String cName = m1.group(1);
                        String cDef = m1.group(2);
                        Matcher m2 = letter.matcher(cDef);
                        StringBuffer sb = new StringBuffer();
                        while (m2.find()) {
                            String c;
                            String k = m2.group();
                            if (charMap.containsKey(k))
                                c = charMap.get(k) + "";
                            else
                                c = "";

                            m2.appendReplacement(sb, c);
                        }
                        m2.appendTail(sb);
                        String def = sb.toString();
                        def = def.replaceAll(" ", "");
                        classes.put(cName, def);
                    }
                }
                str = reader.readLine();
            }
        } catch (IOException e) {
        }
    }
}