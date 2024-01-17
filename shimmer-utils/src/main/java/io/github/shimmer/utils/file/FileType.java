package io.github.shimmer.utils.file;

import lombok.Getter;

/**
 * <p>定义文件类型以及类型对应的content-type</p>
 * Created on 2024-01-17 13:04
 *
 * @author yu_haiyang
 */
@Getter
public enum FileType {

    DOC(".doc", "application/msword"),
    DOCX(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    XLS(".xls", "application/vnd.ms-excel"),
    XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    CSV(".csv", "text/csv"),
    XML(".xml", "text/xml"),
    PDF(".pdf", "application/pdf"),
    GIF(".gif", "image/gif"),
    JPG(new String[]{".jpeg", ".jpg"}, "image/jpeg"),
    PNG(".png", "image/png"),
    ZIP(".zip", "application/zip"),
    RAR(".rar", "application/x-compressed"),
    ISO(".iso", "application/x-cd-image"),
    X7Z(".7z", "application/x-7z-compressed"),
    BZ(new String[]{".bz", ".bz2"}, "application/x-bzip"),
    TAR(".tar", "application/x-tar"),
    TBZ(new String[]{".tar.bz", ".tar.bz2"}, "application/x-bzip-compressed-tar"),
    TGZ(new String[]{".tgz", ".tar.gz"}, "application/x-compressed-tar"),
    JSON(".json", "application/json"),

    PEM(".pem", "application/x-x509-ca-cert"),
    PROPERTIES(".properties", "application/octet-stream"),
    JAVA(".java", "text/plain", "text/x-java"),
    JAR(".jar", "application/java-archive"),
    WAR(".war", "application/octet-stream"),
    MARKDOWN(new String[]{".md", ".markdown"}, "text/plain"),
    CSS(".css", "text/css"),
    DTD(".dtd", "application/xml-dtd"),
    JSONP(".jsonp", "application/jsonp"),
    GPG(".gpg", "application/pgp-encrypted"),
    HTML(new String[]{".htm", ".html"}, "text/html"),
    FLV(".flv", "video/x-flv"),
    M4A(".m4a", "audio/mp4"),
    M4V(".m4v", "video/mp4"),
    MKA(".mka", "audio/x-matroska"),
    MKV(".mkv", "video/x-matroska"),
    MP3(".mp3", "audio/mpeg"),
    MP4(".mp4", "video/mp4"),
    ;

    private final String[] suffix;
    private final String[] contentType;

    FileType(String suffix, String... contentType) {
        this.suffix = new String[]{suffix};
        this.contentType = contentType;
    }

    FileType(String[] suffix, String... contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public static FileType findFileTypeWithContentType(String type) {
        for (FileType value : FileType.values()) {
            String[] contentType1 = value.getContentType();
            for (String s : contentType1) {
                if (s.equals(type)) {
                    return value;
                }
            }
        }
        return null;
    }
}
