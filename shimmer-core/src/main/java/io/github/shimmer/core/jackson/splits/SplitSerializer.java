package io.github.shimmer.core.jackson.splits;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>属性拆分扩展的序列化实现</p>
 *
 * @author yu_haiyang
 */
public class SplitSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private Split split;

    public SplitSerializer() {
    }

    public SplitSerializer(final Split split) {
        this.split = split;
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(s);
        if (Objects.nonNull(s)) {
            String[] values = s.split(split.separator());
            String[] fields = split.fields();
            int length = fields.length;
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (i >= length) {
                    break;
                }
                String field = fields[i];
                jsonGenerator.writeStringField(field, value);
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 为空直接跳过
        if (beanProperty == null) {
            return serializerProvider.findNullValueSerializer(null);
        }
        // 非String类直接跳过
        if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            Split split = beanProperty.getAnnotation(Split.class);
            if (split == null) {
                split = beanProperty.getContextAnnotation(Split.class);
            }
            if (split != null) {
                // 如果能得到注解，就将注解 传入 EnumSplitSerializer
                return new SplitSerializer(split);
            }
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
