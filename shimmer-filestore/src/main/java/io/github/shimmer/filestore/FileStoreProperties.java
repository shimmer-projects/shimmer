package io.github.shimmer.filestore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.x.file.storage.core.FileStorageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>文件存储配置类</p>
 * Created on 2024-01-17 10:58
 *
 * @author yu_haiyang
 */

@Data
@ConfigurationProperties(prefix = "shimmer.filestore")
public class FileStoreProperties {

    /**
     * 是否启用文件存储模块
     */
    private boolean enabled;
    /**
     * 默认存储平台
     */
    private String defaultPlatform = "local-plus";
    /**
     * 缩略图后缀，例如【.min.jpg】【.png】
     */
    private String thumbnailSuffix = ".min.jpg";
    /**
     * 上传时不支持元数据时抛出异常
     */
    private Boolean uploadNotSupportMetadataThrowException = true;
    /**
     * 上传时不支持 ACL 时抛出异常
     */
    private Boolean uploadNotSupportAclThrowException = true;
    /**
     * 复制时不支持元数据时抛出异常
     */
    private Boolean copyNotSupportMetadataThrowException = true;
    /**
     * 复制时不支持 ACL 时抛出异常
     */
    private Boolean copyNotSupportAclThrowException = true;
    /**
     * 移动时不支持元数据时抛出异常
     */
    private Boolean moveNotSupportMetadataThrowException = true;
    /**
     * 移动时不支持 ACL 时抛出异常
     */
    private Boolean moveNotSupportAclThrowException = true;
    /**
     * 启用 byte[] 文件包装适配器
     */
    private Boolean enableByteFileWrapper = true;
    /**
     * 启用 URI 文件包装适配器，包含 URL 和 String
     */
    private Boolean enableUriFileWrapper = true;
    /**
     * 启用 InputStream 文件包装适配器
     */
    private Boolean enableInputStreamFileWrapper = true;
    /**
     * 启用本地文件包装适配器
     */
    private Boolean enableLocalFileWrapper = true;
    /**
     * 启用 HttpServletRequest 文件包装适配器
     */
    private Boolean enableHttpServletRequestFileWrapper = true;
    /**
     * 启用 MultipartFile 文件包装适配器
     */
    private Boolean enableMultipartFileWrapper = true;
    /**
     * 本地存储
     */
    @Deprecated
    private List<? extends SpringLocalConfig> local = new ArrayList<>();
    /**
     * 本地存储
     */
    private List<? extends SpringLocalPlusConfig> localPlus = new ArrayList<>();
    /**
     * 华为云 OBS
     */
    private List<? extends SpringHuaweiObsConfig> huaweiObs = new ArrayList<>();
    /**
     * 阿里云 OSS
     */
    private List<? extends SpringAliyunOssConfig> aliyunOss = new ArrayList<>();
    /**
     * 七牛云 Kodo
     */
    private List<? extends SpringQiniuKodoConfig> qiniuKodo = new ArrayList<>();
    /**
     * 腾讯云 COS
     */
    private List<? extends SpringTencentCosConfig> tencentCos = new ArrayList<>();
    /**
     * 百度云 BOS
     */
    private List<? extends SpringBaiduBosConfig> baiduBos = new ArrayList<>();
    /**
     * 又拍云 USS
     */
    private List<? extends SpringUpyunUssConfig> upyunUss = new ArrayList<>();
    /**
     * MinIO USS
     */
    private List<? extends SpringMinioConfig> minio = new ArrayList<>();

    /**
     * Amazon S3
     */
    private List<? extends SpringAmazonS3Config> amazonS3 = new ArrayList<>();

    /**
     * FTP
     */
    private List<? extends SpringFtpConfig> ftp = new ArrayList<>();

    /**
     * FTP
     */
    private List<? extends SpringSftpConfig> sftp = new ArrayList<>();

    /**
     * WebDAV
     */
    private List<? extends SpringWebDavConfig> webdav = new ArrayList<>();

    /**
     * GoogleCloud Storage
     */
    private List<? extends SpringGoogleCloudStorageConfig> googleCloudStorage = new ArrayList<>();

    /**
     * FastDFS
     */
    private List<? extends SpringFastDfsConfig> fastdfs = new ArrayList<>();

    /**
     * Azure Blob Storage
     */
    private List<? extends SpringAzureBlobStorageConfig> azureBlob = new ArrayList<>();

    /**
     * 转换成 FileStorageProperties ，并过滤掉没有启用的存储平台
     */
    public FileStorageProperties toFileStorageProperties() {
        FileStorageProperties properties = new FileStorageProperties();
        properties.setDefaultPlatform(defaultPlatform);
        properties.setThumbnailSuffix(thumbnailSuffix);
        properties.setUploadNotSupportMetadataThrowException(uploadNotSupportMetadataThrowException);
        properties.setUploadNotSupportAclThrowException(uploadNotSupportAclThrowException);
        properties.setCopyNotSupportMetadataThrowException(copyNotSupportMetadataThrowException);
        properties.setCopyNotSupportAclThrowException(copyNotSupportAclThrowException);
        properties.setMoveNotSupportMetadataThrowException(moveNotSupportMetadataThrowException);
        properties.setMoveNotSupportAclThrowException(moveNotSupportAclThrowException);
        properties.setLocal(
                local.stream().filter(SpringLocalConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setLocalPlus(localPlus.stream()
                .filter(SpringLocalPlusConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setHuaweiObs(huaweiObs.stream()
                .filter(SpringHuaweiObsConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setAliyunOss(aliyunOss.stream()
                .filter(SpringAliyunOssConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setQiniuKodo(qiniuKodo.stream()
                .filter(SpringQiniuKodoConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setTencentCos(tencentCos.stream()
                .filter(SpringTencentCosConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setBaiduBos(
                baiduBos.stream().filter(SpringBaiduBosConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setUpyunUss(
                upyunUss.stream().filter(SpringUpyunUssConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setMinio(
                minio.stream().filter(SpringMinioConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setAmazonS3(
                amazonS3.stream().filter(SpringAmazonS3Config::getEnableStorage).collect(Collectors.toList()));
        properties.setFtp(ftp.stream().filter(SpringFtpConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setSftp(
                sftp.stream().filter(SpringSftpConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setWebdav(
                webdav.stream().filter(SpringWebDavConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setGoogleCloudStorage(googleCloudStorage.stream()
                .filter(SpringGoogleCloudStorageConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setFastdfs(
                fastdfs.stream().filter(SpringFastDfsConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setAzureBlob(azureBlob.stream()
                .filter(SpringAzureBlobStorageConfig::getEnableStorage)
                .collect(Collectors.toList()));

        return properties;
    }

    /**
     * 本地存储
     */
    @Deprecated
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringLocalConfig extends FileStorageProperties.LocalConfig {
        /**
         * 本地存储访问路径
         */
        private String[] pathPatterns = new String[0];
        /**
         * 启用本地存储
         */
        private Boolean enableStorage = false;
        /**
         * 启用本地访问
         */
        private Boolean enableAccess = false;
    }

    /**
     * 本地存储升级版
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringLocalPlusConfig extends FileStorageProperties.LocalPlusConfig {
        /**
         * 本地存储访问路径
         */
        private String[] pathPatterns = new String[0];
        /**
         * 启用本地存储
         */
        private Boolean enableStorage = false;
        /**
         * 启用本地访问
         */
        private Boolean enableAccess = false;
    }

    /**
     * 华为云 OBS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringHuaweiObsConfig extends FileStorageProperties.HuaweiObsConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 阿里云 OSS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringAliyunOssConfig extends FileStorageProperties.AliyunOssConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 七牛云 Kodo
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringQiniuKodoConfig extends FileStorageProperties.QiniuKodoConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 腾讯云 COS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringTencentCosConfig extends FileStorageProperties.TencentCosConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 百度云 BOS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringBaiduBosConfig extends FileStorageProperties.BaiduBosConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 又拍云 USS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringUpyunUssConfig extends FileStorageProperties.UpyunUssConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * MinIO
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringMinioConfig extends FileStorageProperties.MinioConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * Amazon S3
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringAmazonS3Config extends FileStorageProperties.AmazonS3Config {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * FTP
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringFtpConfig extends FileStorageProperties.FtpConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * SFTP
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringSftpConfig extends FileStorageProperties.SftpConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * WebDAV
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringWebDavConfig extends FileStorageProperties.WebDavConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * GoogleCloud Storage
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringGoogleCloudStorageConfig extends FileStorageProperties.GoogleCloudStorageConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * FastDFS Storage
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringFastDfsConfig extends FileStorageProperties.FastDfsConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * AzureBlob Storage
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringAzureBlobStorageConfig extends FileStorageProperties.AzureBlobStorageConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }
}
