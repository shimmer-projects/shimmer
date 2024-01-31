package io.github.shimmer.core.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import io.github.shimmer.utils.Utils;

import java.io.IOException;
import java.util.Objects;

/**
 * <pre>
 * 序列化注解自定义实现
 * JsonSerializer<String>：指定String类型，serialize()方法用于将修改后的数据载入
 * Jackson使用ContextualSerializer在序列化时获取字段注解的属性
 * </pre>
 * Created on 2024-01-31 15:57
 *
 * @author yu_haiyang
 */
public class EncryptorSerializer extends JsonSerializer<String> implements ContextualSerializer {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 数据不为空时加密数据
        if (Utils.useString(value).isNotBlank()) {
            String encrypt = Utils.useBase64(value).encode().finish();
            jsonGenerator.writeString(encrypt);
            return;
        }
        jsonGenerator.writeString(value);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty property) throws JsonMappingException {
        Encryptor encryptor = property.getAnnotation(Encryptor.class);
        // 只针对String类型
        if (Objects.nonNull(encryptor) && Objects.equals(String.class, property.getType().getRawClass())) {
            return this;
        }
        return serializerProvider.findValueSerializer(property.getType(), property);
    }
}
