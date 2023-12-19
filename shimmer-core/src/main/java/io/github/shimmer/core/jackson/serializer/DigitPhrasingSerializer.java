package io.github.shimmer.core.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 自定义对Number 类型json数据序列化返回
 */
public class DigitPhrasingSerializer extends JsonSerializer<Number> {

    /**
     * 科学计算三位逗号分割显示,四舍五入保留2位小数
     */
    private final DecimalFormat format = new DecimalFormat("###,###.##");

    @Override
    public void serialize(Number value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        if (value != null) {
            jsonGenerator.writeString(format.format(value));
        }
    }

}
