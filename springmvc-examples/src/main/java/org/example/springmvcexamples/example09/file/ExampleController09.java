package org.example.springmvcexamples.example09.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springmvcexamples.exception.Code;
import org.example.springmvcexamples.exception.XException;
import org.example.springmvcexamples.vo.ResultVO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@RestController
@RequestMapping("/api/example09/")
@RequiredArgsConstructor
public class ExampleController09 {
    // 测试用下载文件。包含空格/中文
    final Path filePath = Path.of("E:/video/Mr. Bean at Olympics - 副本.mp4");

    // springmvc底层基于传统IO 16K字节数组缓冲区transferTo()方法实现
    // 因此，不会一次性将文件加载到内存
    @GetMapping("files")
    public ResponseEntity<Resource> downloadFile() {
        // 自动处理编码，支持中文/空格的文件名称；可避免手动拼接
        // 封装了http header Content-Disposition
        var contentDisposition = ContentDisposition
                .attachment()
                .filename(filePath.getFileName().toString(), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(new FileSystemResource(filePath));
    }

    // 模拟预压缩的文件目录
    final Path dirPath = Path.of("E:/courses");
    @GetMapping("files-zip")
    public ResponseEntity<StreamingResponseBody> downloadZip() {
        // 响应附件文件信息
        var contentDisposition = ContentDisposition
                .attachment()
                .filename("压缩文件.zip", StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                // 声明MIME类型，可选
                .header(HttpHeaders.CONTENT_TYPE, "application/zip")
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                // 手动控制输出流
                .body(outputStream -> {
                    // 将输出流包装为压缩文件流。数据压缩为ZIP，并以流响应
                    try (var zip = new ZipOutputStream(outputStream, StandardCharsets.UTF_8);
                         var stream = Files.walk(dirPath)) {
                        // 仅读取文件，忽略目录
                        stream.filter(Files::isRegularFile)
                                .forEach(file -> {
                                    try {
                                        // 创建压缩包内一个条目，文件在压缩包内名称
                                        var entry = new ZipEntry(file.getFileName().toString());
                                        // 源文件大小
                                        entry.setSize(Files.size(file));
                                        zip.putNextEntry(entry);
                                        // 边压缩边写入输出流，无法知道压缩后总长度
                                        Files.copy(file, zip);
                                        // 关闭当前条目
                                        zip.closeEntry();
                                    } catch (IOException e) {
                                        throw XException.builder()
                                                .codeN(500)
                                                .message("Error adding file to zip: " + file)
                                                .build();
                                    }
                                });
                    }
                });
    }

    final Path uploadDirectory = Path.of("E:/");

    // 地址栏可传递参数
    // @RequestPart，从form表单获取基本参数
    // MultipartFile，封装上传的二进制文件
    @PostMapping("upload")
    public ResultVO postFile(@RequestPart String pname,
                             MultipartFile file) {
        var fileName = file.getOriginalFilename();
        if (!StringUtils.hasLength(fileName)) {
            throw XException.builder()
                    .codeN(Code.ERROR)
                    .message("上传文件为空")
                    .build();
        }
        // 清洗文件名，防止路径遍历攻击。`../../../`
        var cleanedNamePath = Path.of(fileName).getFileName();
        log.debug("filename: {}; pname: {}; size: {}", cleanedNamePath, pname, file.getSize());
        try {
            // 可忽略上传文件名称，按服务器端逻辑重命名保存
            file.transferTo(uploadDirectory.resolve(cleanedNamePath));
        } catch (IOException e) {
            throw XException.builder()
                    .codeN(Code.ERROR)
                    .message("上传错误")
                    .build();
        }
        return ResultVO.ok();
    }

    // 无法调用零拷贝方法
    // 手动基于NIO实现零拷贝
    /*@GetMapping("files-zerocopy")
    public ResponseEntity<StreamingResponseBody> downloadZeroCopy() throws IOException {
        var contentDisposition = ContentDisposition
                .attachment()
                .filename(filePath.getFileName().toString(), StandardCharsets.UTF_8)
                .build();
        long size = Files.size(filePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(size))
                .body(outputStream -> {
                    try (var fileChannel = FileChannel.open(filePath, StandardOpenOption.READ);
                         var outChannel = Channels.newChannel(outputStream)) {
                        // 防止因文件系统/TCP窗口等限制因素无法一次性传输
                        long position  = 0;
                        while (position  < size) {
                            position  += fileChannel.transferTo(position , size - position, outChannel);
                        }
                    } catch (IOException e) {
                        throw XException.builder()
                                .codeN(500)
                                .message("文件读取错误")
                                .build();
                    }
                });
    }*/
}
