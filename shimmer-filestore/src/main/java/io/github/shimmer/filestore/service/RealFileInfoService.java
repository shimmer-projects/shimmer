package io.github.shimmer.filestore.service;

import io.github.shimmer.filestore.entity.RealFileInfoEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>物理文件元信息服务接口</p>
 * Created on 2024-01-26 10:49
 *
 * @author yu_haiyang
 */
public interface RealFileInfoService {

    RealFileInfoEntity save(MultipartFile file);
}
