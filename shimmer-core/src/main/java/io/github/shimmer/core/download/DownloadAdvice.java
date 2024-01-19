package io.github.shimmer.core.download;

import io.github.shimmer.core.annotation.Download;
import io.github.shimmer.utils.Utils;
import io.github.shimmer.utils.file.FileType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * <p>下载功能AOP拦截</p>
 * Created on 2024-01-17 14:02
 *
 * @author yu_haiyang
 */
@Component
@Aspect
@Order(Integer.MAX_VALUE)
@Slf4j
public class DownloadAdvice {

    @Pointcut("@annotation(io.github.shimmer.core.annotation.Download)")
    public void pointcut() {
    }

    /**
     * 返回类型支持四种，void string file InputStream;
     * string 支持一下几种前缀，classpath:, file:, http(s):, store:
     * void 支持的是通过注解指定 string这几种方式
     */
    @AfterReturning(pointcut = "pointcut()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Download download = method.getAnnotation(Download.class);
        String source = download.source();
        String filename = download.filename();

        File file;
        Class<?> returnType = method.getReturnType();
        if (void.class.isAssignableFrom(returnType)) {
            if (Utils.useString(source).startsWithAny("http:", "https:")) {
                URI uri = URI.create(source);
                HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
                HttpResponse.BodyHandler<InputStream> bodyHandler = HttpResponse.BodyHandlers.ofInputStream();
                HttpResponse<InputStream> send = HttpClient.newHttpClient().send(request, bodyHandler);
                InputStream body = send.body();
                String s = send.headers().firstValue("content-type").orElse("");
                String suffix = null;
                FileType fileTypeWithContentType = FileType.findFileTypeWithContentType(s);
                if (Utils.useNullables(fileTypeWithContentType).isNotNull()) {
                    suffix = fileTypeWithContentType == null ? null : fileTypeWithContentType.getSuffix()[0];

                }
                if (Utils.useNullables(suffix).isNull()) {
                    String path = uri.getPath();
                    suffix = path.substring(path.lastIndexOf("."));
                }
                suffix = Utils.useNullables(suffix).isNull() ? ".tmp" : suffix;

                file = File.createTempFile("tmp", suffix);
                body.transferTo(new FileOutputStream(file));
                body.close();
            } else {
                file = ResourceUtils.getFile(source);
            }
        } else if (File.class.isAssignableFrom(returnType)) {
            file = (File) returnVal;
        } else if (InputStream.class.isAssignableFrom(returnType)) {
            file = new File(filename);
            ((InputStream) returnVal).transferTo(new FileOutputStream(file));
        } else {
            throw new RuntimeException("@Download annotation only supports returned void 、File and InputStream.");
        }

        if (Utils.useString(filename).isBlank()) {
            filename = file.getName();
        }
        throw new DownloadTransferException(file, filename);
    }
}
