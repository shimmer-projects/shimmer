package io.github.shimmer.codegen.controller;

import io.github.shimmer.codegen.handle.GenCodeHandle;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * <p>代码生成</p>
 * Created on 2024-02-04 15:49
 *
 * @author yu_haiyang
 */

@RestController
@RequestMapping(path = "gen")
@Tag(name = "代码生成")
@Validated
public class GenController {

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam(value = "filename", required = false) String filename) throws Exception {
        byte[] handle = new GenCodeHandle().handle();
        //指定预下载文件的位置
        //FileSystemResource file = new FileSystemResource("C:\\Users\\yu_haiyang\\Desktop\\files\\" + filename);
        filename = "demo.zip";
        HttpHeaders httpHeaders = new HttpHeaders();
        //指定下载后的文件名等，new String(filename.getBytes("UTF-8"),"ISO8859-1")很重要，不然会下载中文名的时候名字为空
        httpHeaders.add("Content-Disposition", "attachment; filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        /* 设置该请求头后,js能够获取content-disposition请求头数据,从中获取文件名称 */
        httpHeaders.add("Access-Control-Expose-Headers", "Content-Disposition");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                //.contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(handle);
        //.body(new InputStreamResource(file.getInputStream()));
    }
}
