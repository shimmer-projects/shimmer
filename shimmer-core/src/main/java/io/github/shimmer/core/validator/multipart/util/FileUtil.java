package io.github.shimmer.core.validator.multipart.util;

import io.github.shimmer.utils.Constant;
import io.github.shimmer.utils.Utils;

import java.io.File;

public class FileUtil {
    /**
     * 类Unix路径分隔符
     */
    private static final String UNIX_SEPARATOR = "/";
    /**
     * Windows路径分隔符
     */
    private static final String WINDOWS_SEPARATOR = "\\";

    /**
     * 获取文件扩展名，扩展名不带“.”
     *
     * @param file 文件
     * @return 扩展名
     */
    public static String extName(File file) {
        if (null == file) {
            return null;
        }
        if (file.isDirectory()) {
            return null;
        }
        return extName(file.getName());
    }

    /**
     * 获得文件的扩展名，扩展名不带“.”
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String extName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return Constant.EMPTY_STR;
        } else {
            String ext = fileName.substring(index + 1);
            // 扩展名中不能包含路径相关的符号
            return Utils.useString(ext).containsAny(UNIX_SEPARATOR, WINDOWS_SEPARATOR) ? Constant.EMPTY_STR : ext;
        }
    }
}
