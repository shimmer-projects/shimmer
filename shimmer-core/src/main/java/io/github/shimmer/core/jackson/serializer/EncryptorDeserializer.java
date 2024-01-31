package io.github.shimmer.core.jackson.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import io.github.shimmer.utils.Utils;

import java.io.IOException;
import java.util.Objects;

/**
 * <pre>
 * 序列化注解自定义实现
 * JsonDeserializer<String>：指定String类型，deserialize()方法用于将修改后的数据载入
 * Jackson使用ContextualSerializer在序列化时获取字段注解的属性
 * </pre>
 * Created on 2024-01-31 15:56
 *
 * @author yu_haiyang
 */
public class EncryptorDeserializer extends JsonDeserializer<String> implements ContextualDeserializer {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String valueAsString = jsonParser.getValueAsString();
        // 数据不为空时解密数据
        if (Utils.useString(valueAsString).isNotBlank()) {
            // EncryUtil为封装的加解密工具
            return Utils.useBase64(valueAsString).decode().finish();
        }
        return valueAsString;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty property) throws JsonMappingException {
        Encryptor encryptor = property.getAnnotation(Encryptor.class);
        // 只针对String类型
        if (Objects.nonNull(encryptor) && Objects.equals(String.class, property.getType().getRawClass())) {
            return this;
        }
        return deserializationContext.findContextualValueDeserializer(property.getType(), property);
    }
}