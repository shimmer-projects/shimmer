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
import java.util.Objects;

/**
 * <p>时间间隔人性化</p>
 * 计算时间间隔时通常得到的是是一个秒值，看起来并不友好，而且不能够一眼看出具体间隔多久。
 * 通常会转换成 x小时y分n秒
 * <br>
 * 该注解应该指定在一个数值型的FIELD上，这个值应该是一个秒值。
 * <br>
 * Created on 2024-01-18 19:41
 *
 * @author yu_haiyang
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(using = TimeIntervalHumanReadable.TimeIntervalHumanReadableSerializer.class)
public @interface TimeIntervalHumanReadable {

    /**
     * 指定间隔的单位是毫秒，还是秒
     */
    boolean millisecond();

    class TimeIntervalHumanReadableSerializer extends JsonSerializer<Number> implements ContextualSerializer {

        private boolean millisecond;

        public TimeIntervalHumanReadableSerializer() {
        }

        public TimeIntervalHumanReadableSerializer(boolean millisecond) {
            this.millisecond = millisecond;
        }

        /**
         * 最终实现的格式为：x天y小时j分q秒；
         * 最后实现以天为最大单位，因为每个月时间不一样，不好推算。
         *
         * @param number        需要序列化的值
         * @param jsonGenerator json生成器
         */
        @Override
        public void serialize(Number number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Utils.useNullables(number).isNotNull()) {
                long value = number.longValue();
                long ns = millisecond ? 1000 : 1;// 一秒钟的毫秒数
                long nm = ns * 60;// 一分钟的毫秒数
                long nh = ns * 60 * 60;// 一小时的毫秒数
                long nd = ns * 24 * 60 * 60;// 一天的毫秒数

                long day = value / nd;// 计算差多少天
                long hour = value % nd / nh + day * 24;// 计算差多少小时
                long min = value % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
                long sec = value % nd % nh % nm / ns;// 计算差多少秒

                jsonGenerator.writeString(day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟" + sec + "秒");
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
            if (Objects.equals(rawClass, long.class) || Objects.equals(rawClass, Number.class)) {
                TimeIntervalHumanReadable annotation = beanProperty.getAnnotation(TimeIntervalHumanReadable.class);
                if (annotation == null) {
                    annotation = beanProperty.getContextAnnotation(TimeIntervalHumanReadable.class);
                }
                if (annotation != null) {
                    // 如果能得到注解，就将注解 传入 EnumSplitSerializer
                    return new TimeIntervalHumanReadableSerializer(annotation.millisecond());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
    }
}
