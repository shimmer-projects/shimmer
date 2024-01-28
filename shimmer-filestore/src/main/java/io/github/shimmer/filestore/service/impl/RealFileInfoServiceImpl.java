package io.github.shimmer.filestore.service.impl;

import io.github.shimmer.filestore.entity.RealFileInfoEntity;
import io.github.shimmer.filestore.repository.RealFileInfoRepository;
import io.github.shimmer.filestore.service.RealFileInfoService;
import jakarta.annotation.Resource;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>物理文件元信息服务类实现</p>
 * Created on 2024-01-26 10:49
 *
 * @author yu_haiyang
 */
@Service
public class RealFileInfoServiceImpl implements RealFileInfoService {

    @Resource
    private FileStorageService fileStorageService;//注入实列

    @Resource
    private RealFileInfoRepository realFileInfoRepository;


    @Override
    public RealFileInfoEntity save(MultipartFile file) {
        FileInfo upload = fileStorageService.of(file).upload();
        RealFileInfoEntity realFileInfoEntity = new RealFileInfoEntity();
        realFileInfoEntity.setFileSize(upload.getSize());
        realFileInfoEntity.setFilename(upload.getOriginalFilename());
        realFileInfoEntity.setContentType(upload.getContentType());
        realFileInfoEntity.setRealPath(upload.getUrl());
        realFileInfoEntity.setSuffix(upload.getExt());
        realFileInfoEntity.setIdentifier(upload.getId());
        return realFileInfoRepository.save(realFileInfoEntity);
    }
}
