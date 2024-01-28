package io.github.shimmer.filestore.controller;

import io.github.shimmer.core.annotation.Rest;
import io.github.shimmer.core.annotation.Upload;
import io.github.shimmer.filestore.entity.RealFileInfoEntity;
import io.github.shimmer.filestore.service.RealFileInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>文件管理控制器</p>
 * Created on 2024-01-17 10:42
 *
 * @author yu_haiyang
 */

@Rest(path = "file", name = "文件管理")
public class FileController {


    @Resource
    private RealFileInfoService realFileInfoService;

    @Upload(path = "upload")
    public RealFileInfoEntity uploadFile(MultipartFile file) {
        return realFileInfoService.save(file);
    }
}
