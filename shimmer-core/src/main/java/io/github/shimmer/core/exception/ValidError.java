package io.github.shimmer.core.exception;


import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 校验错误的字段返回值，将jakarta.validation 验证失败的字段转换成该格式返回给前端，便于处理
 * </p>
 * Created on 2023-12-18 19:59
 *
 * @author yu_haiyang
 */

@Data
@Builder
public class ValidError {

    private String field;
    private Object value;
    private String message;
}
