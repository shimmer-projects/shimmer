package io.github.shimmer.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 字符串工具类
 * <br/>
 * Created on 2023-12-17 10:34
 *
 * @author yu_haiyang
 */
public class Strings extends Nullables<String> {

    Strings(String str) {
        super.source = str;
    }

    /**
     * 字符串在输出时的对齐
     * 输出width个字符，S左对齐，不足部分用fillChar填充，默认的为空格。
     */
    public Strings leftJust(int width, char fillChar) {
        if (isNotNull()) {
            int length = source.length();
            int fillLength = width - length;
            String fill = String.valueOf(fillChar).repeat(Math.max(0, fillLength));
            source = fill + source;
        }
        return this;
    }

    /**
     * 输出width个字符，S右对齐，不足部分用fillChar填充，默认的为空格。
     */
    public Strings rightJust(int width, char fillChar) {
        if (isNotNull()) {
            int length = source.length();
            int fillLength = width - length;
            String fill = String.valueOf(fillChar).repeat(Math.max(0, fillLength));
            source = source + fill;
        }
        return this;
    }

    /**
     * 输出width个字符，S居中对齐，不足部分用fillChar填充，默认的为空格。
     */
    public Strings centerJust(int width, char fillChar) {
        if (isNotNull()) {
            int length = source.length();
            int fillLength = width - length;
            int leftLength = 0;
            int rightLength = 0;
            if (fillLength > 0) {
                rightLength = fillLength / 2;
                leftLength = fillLength - rightLength;
            }

            String left = String.valueOf(fillChar).repeat(leftLength);
            String right = String.valueOf(fillChar).repeat(rightLength);
            source = left + source + right;
        }
        return this;
    }

    /**
     * 返回指定长度的字符串，使原字符串右对齐，前面用0填充到指定字符串长度。
     */
    public Strings zfill(int width) {
        if (isNotNull()) {
            int length = source.length();
            int fillLength = width - length;
            String fill = String.valueOf(0).repeat(fillLength);
            source = fill + source;
        }
        return this;
    }

    /**
     * 将字符串中的所有大写字母转换为小写字母。
     */
    public Strings lower() {
        if (isNotBlank()) {
            source = source.toLowerCase();
        }
        return this;
    }


    /**
     * 将字符串中的所有小写字母转换为大写字母。
     */
    public Strings upper() {
        if (isNotBlank()) {
            source = source.toUpperCase();
        }
        return this;
    }


    /**
     * 将字符串中的大小写字母同时进行互换。即将字符串中的大写字母转换为小写字母，将小写字母转换为大写字母。
     */
    public Strings swapcase() {
        if (isNotBlank()) {
            char[] chars = source.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (Character.isLowerCase(c)) {
                    chars[i] = Character.toUpperCase(c);
                    continue;
                }
                if (Character.isUpperCase(c)) {
                    chars[i] = Character.toLowerCase(c);
                }
            }
            source = new String(chars);
        }
        return this;
    }


    /**
     * 将字符串的第一个字母变成大写，其余字母变为小写。
     */
    public Strings capitalize() {
        if (isNotBlank()) {
            source = capitalize(source);
        }
        return this;
    }

    private String capitalize(String word) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            char distCase;
            if (i == 0) {
                distCase = Character.toUpperCase(c);
            } else {
                distCase = Character.toLowerCase(c);
            }
            chars[i] = distCase;
        }
        return new String(chars);
    }

    /**
     * 返回一个满足标题格式的字符串。即所有英文单词首字母大写，其余英文字母小写。
     * TODO 下划线风格  驼峰风格  空格风格，三周风格处理转换
     * 默认风格是空格
     */
    public Strings title() {
        if (isNotNull()) {
            String[] words = source.split(" ");
            StringJoiner joiner = new StringJoiner(" ");
            for (String word : words) {
                String capitalize = capitalize(word);
                joiner.add(capitalize);
            }
            source = joiner.toString();
        }
        return this;
    }

    /**
     * 去掉不需要的字符（默认为空格）
     */
    public Strings strip() {
        source = source.trim();
        return this;
    }

    public void lstrip() {
        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            boolean whitespace = Character.isWhitespace(chars[i]);
            if (!whitespace && i != 0) {
                source = source.substring(i);
                break;
            }
        }
    }

    public void rstrip() {
        char[] chars = source.toCharArray();
        int lastIndex = chars.length - 1;
        for (int i = lastIndex; i >= 0; i--) {
            boolean whitespace = Character.isWhitespace(chars[i]);
            if (!whitespace && i != lastIndex) {
                source = source.substring(0, i + 1);
                break;
            }
        }
    }

    public boolean startsWithAny(String... prefix) {
        return startsWithAny(false, prefix);
    }

    public boolean startsWithAny(boolean ignoreCase, String... prefix) {
        if (isNotNull()) {
            String sourceFinal = ignoreCase ? source.toLowerCase() : source;
            for (String pre : prefix) {
                String prefixFinal = ignoreCase ? pre.toLowerCase() : pre;
                if (sourceFinal.startsWith(prefixFinal)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean endsWithAny(String... suffix) {
        return endsWithAny(false, suffix);
    }

    public boolean endsWithAny(boolean ignoreCase, String... suffix) {
        if (isNotNull()) {
            String sourceFinal = ignoreCase ? source.toLowerCase() : source;
            for (String pre : suffix) {
                String suffixFinal = ignoreCase ? pre.toLowerCase() : pre;
                if (sourceFinal.endsWith(suffixFinal)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 切片
     */
    public Strings slice(int from, int to) {
        if (isNotNull()) {
            source = source.substring(from, to);
        }
        return this;
    }


    public boolean containsAny(String... seq) {
        for (String s : seq) {
            if (source.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * " " 不算空
     */
    public boolean isBlank() {
        return isNull() || source.isBlank();
    }

    public boolean isNotBlank() {
        return !isBlank();
    }


    /**
     * null 、 "" 或 “ ” 为 empty，
     */
    public boolean isEmpty() {
        return isNull() || source.isEmpty();
    }

    /**
     * null 或者 "" 为 empty
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * 检测字符串是否由字母和数字组成。字符串中至少有一个字符且所有字符都是字母或数字则返回 True,否则返回 False
     */
    public boolean isalnum() {
        if (isNotNull()) {
            char[] chars = source.toCharArray();
            boolean hasLetter = false;
            for (char c : chars) {
                boolean digit = Character.isDigit(c);
                boolean alphabetic = Character.isAlphabetic(c);
                if (alphabetic) {
                    hasLetter = true;
                }
                if (!digit && !alphabetic) {
                    return false;
                }
            }
            return hasLetter;
        }
        return false;
    }

    /**
     * 检测字符串是否只由字母组成。字符串中至少有一个字符且所有字符都是字母则返回 True,否则返回 False。
     */
    public boolean isalpha() {
        if (isNotNull()) {
            char[] chars = source.toCharArray();
            for (char c : chars) {
                if (!Character.isAlphabetic(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检测字符串是否只由数字组成，字符串中至少有一个字符且所有字符都是数字则返回 True,否则返回 False。
     */
    public boolean isnumeric() {
        if (isNotNull()) {
            char[] chars = source.toCharArray();
            for (char c : chars) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isidentifier() {
        return false;
    }


//    public NumericTool<Double> digitizing() {
//        double v = Double.parseDouble(source);
//        NumericTool<Double> numericTool = NumericTool.newInstance(v);
//        return numericTool;
//    }

    public boolean isspace(CharSequence sequence) {
        if (isNotNull()) {
            char[] chars = source.toCharArray();
            for (char c : chars) {
                if (!Character.isWhitespace(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean islower(CharSequence sequence) {
        if (isNotNull()) {
            char[] chars = source.toCharArray();
            for (char c : chars) {
                if (!Character.isLowerCase(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isupper(CharSequence sequence) {
        if (isNotNull()) {
            char[] chars = source.toCharArray();
            for (char c : chars) {
                if (!Character.isUpperCase(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Arrays<String> split(String regex) {
        String[] array = isNull() ? new String[0] : source.split(regex);
        return new Arrays<>(array);
    }

    public Strings replace(String old, String seq) {
        return this.split(old).join(seq);
    }

    public Strings replace(int startIndex, int endIndex, char ch) {
        String prefix = source.substring(0, startIndex);
        String suffix = source.substring(endIndex);
        int cellLength = source.length() - prefix.length() - suffix.length();
        StringJoiner joiner = new StringJoiner("", prefix, suffix);
        for (int i = 0; i < cellLength; i++) {
            joiner.add(String.valueOf(ch));
        }
        source = joiner.toString();
        return this;
    }

    // TODO 暂未实现的想法
    public void sub() {
    }

    /**
     * 字符串中的搜索
     */
    public void find(CharSequence sequence) {
    }

    public int index(String sequence) {
        return source.indexOf(sequence);
    }

    public void rightIndex(CharSequence sequence) {
    }

    public int count(String seq) {
        int size = 0;
        if (Objects.nonNull(seq)) {
            String replace = source.replace(seq, "");
            size = (source.length() - replace.length()) / seq.length();
        }
        return size;
    }

    public int length() {
        return isNull() ? 0 : source.length();
    }

    /**
     * 最大字符
     */
    public char max() {
        char[] charArray = source.toCharArray();
        char a = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            char t = charArray[i];
            if (t > a) {
                a = t;
            }
        }
        return a;
    }

    /**
     * 最小字符
     */
    public char min() {
        char[] charArray = source.toCharArray();
        char a = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            char t = charArray[i];
            if (t < a) {
                a = t;
            }
        }
        return a;
    }

    /**
     * 最多的字符
     */
    public char most() {
        Map<Character, Integer> counts = new HashMap<>();
        char[] charArray = source.toCharArray();
        for (char c : charArray) {
            Integer i = counts.get(c);
            Integer count = Objects.isNull(i) ? 1 : i + 1;
            counts.put(c, count);
        }

        AtomicReference<Character> mostChar = new AtomicReference<>();
        AtomicReference<Integer> charCount = new AtomicReference<>(0);
        counts.forEach((character, count) -> {
            if (count > charCount.get()) {
                mostChar.set(character);
                charCount.set(count);
            }
        });
        return mostChar.get();
    }

    /**
     * 最少的字符
     */
    public char least() {
        Map<Character, Integer> counts = new HashMap<>();
        char[] charArray = source.toCharArray();
        for (char c : charArray) {
            Integer i = counts.get(c);
            Integer count = Objects.isNull(i) ? 1 : i + 1;
            counts.put(c, count);
        }

        AtomicReference<Character> leastChar = new AtomicReference<>();
        AtomicReference<Integer> charCount = new AtomicReference<>(Integer.MAX_VALUE);
        counts.forEach((character, count) -> {
            if (count < charCount.get()) {
                leastChar.set(character);
                charCount.set(count);
            }
        });
        return leastChar.get();
    }

    public Booleans parseBoolean() {
        boolean res = Boolean.parseBoolean(source);
        return new Booleans(res);
    }

//    public DateTimeTool toDateWithPattern(String pattern) {
//        return DateTimeTool.newInstance(source, pattern);
//    }

    public Strings encodeBase64() {
        if (isNotNull()) {
            super.source = new Base64s(source).encode().finish();
        }
        return this;
    }

    public Strings decodeBase64() {
        if (isNotNull()) {
            super.source = new Base64s(source).decode().finish();
        }
        return this;
    }
}
