package io.github.shimmer.filestore.config;

import lombok.Getter;
import lombok.Setter;
import org.dromara.x.file.storage.core.file.FileWrapper;
import org.dromara.x.file.storage.core.file.FileWrapperAdapter;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>MultipartFile 文件包装适配器</p>
 * Created on 2024-01-26 11:04
 *
 * @author yu_haiyang
 */
@Getter
@Setter
public class MultipartFileWrapperAdapter implements FileWrapperAdapter {

    @Override
    public boolean isSupport(Object source) {
        return source instanceof MultipartFile || source instanceof MultipartFileWrapper;
    }

    @Override
    public FileWrapper getFileWrapper(Object source, String name, String contentType, Long size) {
        if (source instanceof MultipartFileWrapper) {
            return updateFileWrapper((MultipartFileWrapper) source, name, contentType, size);
        } else {
            MultipartFile file = (MultipartFile) source;
            if (name == null) name = file.getOriginalFilename();
            if (contentType == null) contentType = file.getContentType();
            if (size == null) size = file.getSize();
            return new MultipartFileWrapper(file, name, contentType, size);
        }
    }
}
