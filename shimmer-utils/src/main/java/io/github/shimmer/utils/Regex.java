package io.github.shimmer.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 正则表达式工具类
 * <br/>
 * Created on 2023-12-17 10:34
 *
 * @author yu_haiyang
 */
public class Regex {
    private final Pattern pattern;

    private Matcher matcher;

    Regex(String regex) {
        pattern = Pattern.compile(regex);
    }


    public Regex matcher(String str) {
        matcher = pattern.matcher(str);
        return this;
    }

    public boolean match() {
        return matcher.matches();
    }

    public boolean find(String str) {
        return matcher.find();
    }

    public boolean find(String str, int start) {
        return matcher.find(start);
    }

    public String group(int i) {
        return matcher.group(i);
    }

    public int groups() {
        return matcher.groupCount();
    }

    public Map<Integer, String> groupsMap() {
        Map<Integer, String> groupMap = new HashMap<>(16);
        int groupCount = matcher.groupCount();
        for (int i = 1; i < groupCount; i++) {
            String group = matcher.group(i);
            groupMap.put(i, group);
        }
        return groupMap;
    }
}
