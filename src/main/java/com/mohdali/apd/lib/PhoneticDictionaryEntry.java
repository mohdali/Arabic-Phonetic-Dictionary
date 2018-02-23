package com.mohdali.apd.lib;

import java.util.ArrayList;

import java.util.Map;
import java.util.Map.Entry;

public class PhoneticDictionaryEntry implements Comparable<PhoneticDictionaryEntry>{
    private String key;
    private String original;
    private ArrayList<String> defs;

    private Map<String,Character> charMap=(Map<String,Character>)ConfigManager.getProperty("CharMap");
    private Map<String,String> classes=(Map<String,String>)ConfigManager.getProperty("Classes");
    private Map<String,ArrayList<Rule>> rules=(Map<String,ArrayList<Rule>>)ConfigManager.getProperty("Rules");

    public PhoneticDictionaryEntry(String key){
        this(key,false);
    }

    public PhoneticDictionaryEntry(String key,boolean generateDefs){
        this.key=key;
        generateOriginal();
        defs=new ArrayList<String>();
        if(generateDefs)
            generateDefs();
    }

    public String getKey(){
        return key;
    }

    public String getOriginal(){
        return original;
    }

    public ArrayList<String> getDefs(){
        return defs;
    }

    public void setDefs(ArrayList<String> defs){
        this.defs=defs;
    }

    public boolean isValid(){
        String c=key.charAt(0)+"";
        return (original.length() >0 && !c.matches(classes.get("D")));
    }

    public String generateOriginal(){
        String o="";
        for(int i=0;i<key.length();i++){
            String c=key.charAt(i)+"";
            if(!c.matches(classes.get("D")))
                o = o + c;
        }
        this.original=o;
        return o;
    }

    /*public int[] generateTashkeel(){
        if(original==null)
            generateOriginal();
        if(isValid()){
            tashkeel= new int[original.length()];
            int j=1;
            for(int i=0;i<original.length();i++){
                tashkeel[i]=0;
                String c;
                while( j<key.length()){
                    c=key.charAt(j)+"";
                    if(c.matches(ArabicCharacterSet.letters))
                        break;
                    for(ArabicCharacterSet.TashkeelMap t: ArabicCharacterSet.TashkeelMap.values()){
                        if(c.matches(t.key))
                            tashkeel[i]|=t.value;
                    }
                    j++;
                }
                j++;
            }
            return tashkeel;
        }
        return null;
    }*/

    public void addDef(String def){
        defs.add(def.trim());
    }

    public ArrayList<String> generateDefs(){
        String[][] map = new String[key.length()][];
        for(int i=0;i<key.length();i++){
            char c=key.charAt(i);
            for(Entry<String,Character> e: charMap.entrySet()){
                if(c==e.getValue()){
                    ArrayList<Rule> p =rules.get(e.getKey());
                    //System.out.println(p.name());
                    ArrayList<String> r = new ArrayList<String>();
                    for(Rule rule:p){
                        if(rule.applies(key,i)){
                            //System.out.println(i+" "+rule);
                            r.add(rule.replacement);
                        }
                    }
                    Object[] o=r.toArray();
                    if(o.length>0){
                        map[i]=new String[o.length];
                        int j=0;
                        for(Object s:o)
                            map[i][j++]=(String)s;
                    }else
                        map[i]=new String[]{""};
                    break;
                }
            }
        }
        searchMap(map);
        return defs;
    }

    private void searchMap(String[][] map){
        int l=key.length();
        String[] v = new String[l];
        int[] i = new int[l];
        for(int j=0;j<l;j++)
            i[j]=0;
        int k=0;
        while(k>=0){
            while(i[k]<map[k].length){
                v[k]=map[k][i[k]];
                i[k]++;
                if(k==l-1){
                    StringBuffer buff=new StringBuffer();
                    for(String m:v)
                        buff.append(m+(!m.equals("")? " ":""));
                    defs.add(buff.toString().trim());
                }else
                    k++;
            }
            i[k]=0;
            k--;
        }
    }

    public int compareTo(PhoneticDictionaryEntry o){
        return key.compareTo(o.getKey());
    }

    public boolean equals(Object o){
        if(o instanceof PhoneticDictionaryEntry){
            PhoneticDictionaryEntry e = (PhoneticDictionaryEntry) o;
            return key.equals(e.getKey());
        }else
            return false;
    }

    public String toString(){
        int count=0;
        String out="";
        for(String def:defs){
            count++;
            out=out+key+((count==1)? "" : "("+count+")")+" "+def+"\n"; 
        }
        /*for(int i:tashkeel)
            out=out+i+",";
        out+='\n';*/
        return out;
    }
}
