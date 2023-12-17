package io.github.shimmer.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * Base64工具类
 * <br/>
 * Created on 2023-12-17 10:34
 *
 * @author yu_haiyang
 */
public class Base64s {

    private byte[] source;

    Base64s(String source) {
        this.source = source.getBytes(StandardCharsets.UTF_8);
    }

    Base64s(byte[] source) {
        this.source = source;
    }

    public Base64s encode() {
        source = Base64.getEncoder().encode(source);
        return this;
    }

    public Base64s decode() {
        source = Base64.getDecoder().decode(source);
        return this;
    }


    public String finish() {
        return new String(source);
    }
}
