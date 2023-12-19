package io.github.shimmer.core.jackson.mask;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;


/**
 * 对内置脱敏类型的支持实现。
 */
public class MaskSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏类型
     */
    private MaskEnum type;


    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (this.type) {
            case CHINESE_NAME: {
                jsonGenerator.writeString(MaskUtils.chineseName(s));
                break;
            }
            case ID_CARD: {
                jsonGenerator.writeString(MaskUtils.idCardNum(s));
                break;
            }
            case FIXED_PHONE: {
                jsonGenerator.writeString(MaskUtils.fixedPhone(s));
                break;
            }
            case MOBILE_PHONE: {
                jsonGenerator.writeString(MaskUtils.mobilePhone(s));
                break;
            }
            case ADDRESS: {
                jsonGenerator.writeString(MaskUtils.address(s, 4));
                break;
            }
            case EMAIL: {
                jsonGenerator.writeString(MaskUtils.email(s));
                break;
            }
            case BANK_CARD: {
                jsonGenerator.writeString(MaskUtils.bankCard(s));
                break;
            }
            case API_SECRET: {
                jsonGenerator.writeString(MaskUtils.apiSecret(s));
                break;
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 为空直接跳过
        if (beanProperty == null) {
            return serializerProvider.findNullValueSerializer(beanProperty);
        }
        // 非String类直接跳过
        if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            JsonMask jsonMask = beanProperty.getAnnotation(JsonMask.class);
            if (jsonMask == null) {
                jsonMask = beanProperty.getContextAnnotation(JsonMask.class);
            }
            if (jsonMask != null) {
                // 如果能得到注解，就将注解的 value 传入 MaskSerialize
                return new MaskSerializer(jsonMask.value());
            }
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }

    public MaskSerializer() {
    }

    public MaskSerializer(final MaskEnum type) {
        this.type = type;
    }
}
