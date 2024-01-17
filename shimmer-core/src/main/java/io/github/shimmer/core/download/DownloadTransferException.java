package io.github.shimmer.core.download;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

/**
 * <p>下载中转异常</p>
 * Created on 2024-01-17 15:15
 *
 * @author yu_haiyang
 */
@Getter
@RequiredArgsConstructor
public class DownloadTransferException extends RuntimeException {
    private final File file;
    private final String filename;

}
