package io.github.shimmer.utils.mail;

import java.io.ByteArrayInputStream;

/**
 * 附件
 * Created on 2024-02-01 14:58
 *
 * @author yu_haiyang
 */
public class Attached {

    private String fileName;
    private ByteArrayInputStream inputStream;

    public Attached(String fileName, ByteArrayInputStream inputStream) {
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }
}
