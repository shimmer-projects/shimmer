package io.github.shimmer.core.validator.multipart.resolver;

import io.github.shimmer.core.validator.multipart.MultipartFileVerify;
import io.github.shimmer.core.validator.multipart.constant.FileType;
import io.github.shimmer.core.validator.multipart.util.FileTypeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 校验 MultipartFile 的文件类型是否符合要求，抽象类
 */

@Slf4j
public class MultipartFileValidatorResolver {

    /**
     * 校验 MultipartFile 的文件类型是否符合 {@code multipartFileValid} 的要求，只要有一个符合就返回 true
     *
     * @param multipartFileValid 注解
     * @param value              MultipartFile
     * @return true 校验通过, false 校验不通过
     */
    @SneakyThrows
    public static boolean isFileValid(MultipartFileVerify multipartFileValid, MultipartFile value) {

        // 1. 如果没有配置文件大小限制，则不校验
        // 2. 如果没有超过限制，则校验通过；千字节转字节 * 1024
        if (value.getSize() > multipartFileValid.maxSize() * 1024L) {
            return false;
        }

        // 获取文件类型
        String sourceFileType = FileTypeUtil.getFileType(value.getInputStream());

        // 获取不允许的类型
        FileType[] notAllowTypes = multipartFileValid.notAllow();
        // 如果文件的类型存在于不允许列表中，返回false
        if (!ObjectUtils.isEmpty(notAllowTypes) && contains(notAllowTypes, sourceFileType)) {
            return false;
        }

        // 获取允许的类型
        FileType[] allowTypes = multipartFileValid.value();
        if (!ObjectUtils.isEmpty(allowTypes) && !contains(allowTypes, sourceFileType)) {
            return false;
        }

        return true;
    }


    @SneakyThrows
    private static boolean contains(FileType[] fileTypes, String sourceFileType) {
        return Arrays.stream(fileTypes).anyMatch(fileType -> fileType.getName().equals(sourceFileType));
    }
}
