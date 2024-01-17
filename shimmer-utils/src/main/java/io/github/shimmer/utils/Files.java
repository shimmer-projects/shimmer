package io.github.shimmer.utils;


import jakarta.activation.MimetypesFileTypeMap;

import java.io.File;
import java.io.IOException;

/**
 * 文件操作工具类
 * <br/>
 * Created on 2023-12-17 10:34
 *
 * @author yu_haiyang
 */
public class Files extends Nullables<File> {

    Files(File file) {
        super(file);
    }

    public Strings contentType() {
        String contentType = null;
        try {
            contentType = java.nio.file.Files.probeContentType(source.toPath());
        } catch (IOException ignore) {
        }
        if (Utils.useString(contentType).isBlank()) {
            contentType = new MimetypesFileTypeMap().getContentType(source);
        }
        if (Utils.useString(contentType).isBlank()) {
            contentType = "application/octet-stream";
        }
        return Utils.useString(contentType);
    }

}
