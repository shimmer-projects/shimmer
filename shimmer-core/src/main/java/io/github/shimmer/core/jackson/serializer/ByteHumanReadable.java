package io.github.shimmer.core.jackson.serializer;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.shimmer.utils.Utils;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>内存、磁盘等存储设备字节大小转换为 GB MB 等单位</p>
 * Created on 2024-01-18 16:21
 *
 * @author yu_haiyang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(using = ByteHumanReadable.ByteHumanReadableSerializer.class)
public @interface ByteHumanReadable {

    class ByteHumanReadableSerializer extends JsonSerializer<Long> {

        @Override
        public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Utils.useNullables(value).isNotNull()) {
                jsonGenerator.writeString(byteHumanReadable(value));
            }
        }

        private String byteHumanReadable(long size) {
            long kb = 1024;
            long mb = kb * 1024;
            long gb = mb * 1024;
            if (size >= gb) {
                return String.format("%.1f GB", (float) size / gb);
            } else if (size >= mb) {
                float f = (float) size / mb;
                return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
            } else if (size >= kb) {
                float f = (float) size / kb;
                return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
            } else {
                return String.format("%d B", size);
            }
        }
    }
}
