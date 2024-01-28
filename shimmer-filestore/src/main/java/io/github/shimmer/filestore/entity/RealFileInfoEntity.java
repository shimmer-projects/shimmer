package io.github.shimmer.filestore.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>真实物理文件的信息</p>
 * Created on 2024-01-26 10:39
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "fs_realfile_info")
public class RealFileInfoEntity extends BaseEntity<Long> {

    /**
     * 文件的原始名称
     */
    private String filename;
    /**
     * 文件的真实物理路径
     */
    private String realPath;
    /**
     * 文件的大小 字节数
     */
    private Long fileSize;
    /**
     * 文件的后缀名称
     */
    private String suffix;

    /**
     * 文档的响应头类型
     */
    private String contentType;

    /**
     * 文件的MD5 校验码
     */
    private String identifier;
}
