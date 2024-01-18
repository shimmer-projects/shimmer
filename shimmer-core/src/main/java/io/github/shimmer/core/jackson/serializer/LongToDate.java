package io.github.shimmer.core.jackson.serializer;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import io.github.shimmer.utils.Utils;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * <p>long型数据转换为时间格式</p>
 * Created on 2024-01-18 16:39
 *
 * @author yu_haiyang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(using = LongToDate.LongToDateSerializer.class)
public @interface LongToDate {

    String pattern() default "yyyy-MM-dd HH:mm:ss";

    class LongToDateSerializer extends JsonSerializer<Long> implements ContextualSerializer {

        private String pattern;

        public LongToDateSerializer() {
        }

        public LongToDateSerializer(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            if (Utils.useNullables(value).isNotNull()) {
                jsonGenerator.writeString(format.format(new Date(value)));
            }
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            // 为空直接跳过
            if (beanProperty == null) {
                return serializerProvider.findNullValueSerializer(null);
            }
            // 非String类直接跳过
            Class<?> rawClass = beanProperty.getType().getRawClass();
            if (Objects.equals(rawClass, long.class) || Objects.equals(rawClass, Long.class)) {
                LongToDate longToDate = beanProperty.getAnnotation(LongToDate.class);
                if (longToDate == null) {
                    longToDate = beanProperty.getContextAnnotation(LongToDate.class);
                }
                if (longToDate != null) {
                    // 如果能得到注解，就将注解 传入 EnumSplitSerializer
                    return new LongToDateSerializer(longToDate.pattern());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
    }
}
