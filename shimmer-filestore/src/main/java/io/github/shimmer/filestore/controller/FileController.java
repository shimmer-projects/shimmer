package io.github.shimmer.filestore.controller;

import io.github.shimmer.core.annotation.Rest;
import io.github.shimmer.core.annotation.Upload;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>文件管理控制器</p>
 * Created on 2024-01-17 10:42
 *
 * @author yu_haiyang
 */

@Rest(path = "file", name = "文件管理")
public class FileController {

    @Upload(path = "upload")
    public String uploadFile(MultipartFile file) {
        System.out.println(file);
        //file-store
        return "success";
    }
}
