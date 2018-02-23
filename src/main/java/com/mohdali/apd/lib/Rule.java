/*
 * Rule.java
 *
 * Created on February 3, 2006, 5:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mohdali.apd.lib;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Ali
 */
public class Rule {
    public Pattern cond;
    public String replacement;

    public Rule(Pattern cond, String rep) {
        this.cond = cond;
        this.replacement = rep;
    }

    public boolean applies(String str, int index) {
        Matcher matcher = cond.matcher(str);
        return matcher.find(index) && (matcher.start() == index);
    }

    public String toString() {
        return cond.pattern() + "\t" + replacement + "\n";
    }
}
