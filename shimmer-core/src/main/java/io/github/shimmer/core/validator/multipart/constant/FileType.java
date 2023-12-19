package io.github.shimmer.core.validator.multipart.constant;

import lombok.AllArgsConstructor;

/**
 * 文件类型
 **/
@SuppressWarnings("all")
@AllArgsConstructor
public enum FileType {
    PS("ps"),
    JAVA("java"),
    DBX("dbx"),
    QDF("qdf"),
    WMV("wmv"),
    PDF("pdf"),
    INI("ini"),
    MF("mf"),
    JAR("jar"),
    RAR("rar"),
    AVI("avi"),
    BMP("bmp"),
    CLASS("class"),
    MOV("mov"),
    JPG("jpg"),
    ZIP("zip"),
    EML("eml"),
    TORRENT("torrent"),
    PSD("psd"),
    EXE("exe"),
    GZ("gz"),
    RAM("ram"),
    PWL("pwl"),
    MPG("mpg"),
    RTF("rtf"),
    CHM("chm"),
    MP4("mp4"),
    PNG("png"),
    MID("mid"),
    MXP("mxp"),
    TIF("tif"),
    BAT("bat"),
    WPD("wpd"),
    PST("pst"),
    FLV("flv"),
    XLS("xls"),
    XLSX(ZIP.name),
    DWG("dwg"),
    WAV("wav"),
    GIF("gif"),
    RMVB("rmvb"),
    MP3("mp3"),
    MDB("mdb"),
    JSP("jsp");

    private final String name;

    public String getName() {
        return name;
    }
}
