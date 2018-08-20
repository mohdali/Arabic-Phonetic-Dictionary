package com.mohdali.apd.lib;

import java.util.TreeMap;

@SuppressWarnings("serial")
public class PhoneticDictionary extends TreeMap<String, PhoneticDictionaryEntry> {

    public PhoneticDictionaryEntry put(PhoneticDictionaryEntry entry) {
        return this.put(entry.getKey(), entry);
    }
}