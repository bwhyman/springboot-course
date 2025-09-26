package org.example.springmvcexamples.example09.file;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springmvcexamples.exception.XException;
import org.example.springmvcexamples.vo.ResultVO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@RestController
@RequestMapping("/api/example09/")
@RequiredArgsConstructor
public class ExampleController09 {
    // 测试用下载文件。包含空格/中文
    final Path filePath = Path.of("E:/video/Mr. Bean at Olympics - 副本.mp4");

    // springmvc底层基于原生支持零拷贝技术的transferTo()方法实现，默认16K字节数组缓冲区
    // 因此，不会一次性将文件加载到内存
    @GetMapping("files/{fileparam}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileparam) {
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
    @GetMapping("files/{fileparam}/zip")
    public ResponseEntity<StreamingResponseBody> getZip(@PathVariable String fileparam) {
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
                    try (var zip = new ZipOutputStream(outputStream);
                         var stream = Files.walk(dirPath)) {
                        // 仅读取文件，忽略目录
                        stream.filter(Files::isRegularFile)
                                .forEach(file -> {
                                    try {
                                        // 创建压缩包内一个条目，文件在压缩包内名称
                                        ZipEntry entry = new ZipEntry(file.getFileName().toString());
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


    // 模拟传入文件获取的ID/名称等参数
    // 注入response对象，以获取response headers
    // 以二进制字节流+缓冲区下载，避免大文件内存溢出
    @GetMapping("files2/{fileparam}")
    public void getFile(@PathVariable String fileparam, HttpServletResponse response) {
        // 编码以支持中文文件名称；使用spring工具类可避免`空格`被编码为`+`
        var fileName = UriUtils.encode(filePath.getFileName().toString(), StandardCharsets.UTF_8);
        // 声明响应类型为文件附件，浏览器以指定文件名保存
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        // 以NIO Channel读取
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            // 声明响应体长度，便于前端展示进度条等
            response.setContentLength((int) Files.size(filePath));
            // 缓冲区，500K
            ByteBuffer buffer = ByteBuffer.allocate(5 * 1024 * 100);
            int len;
            while ((len = fileChannel.read(buffer)) != -1) {
                // 缓冲区转为读模式
                buffer.flip();
                // 写入输出流
                response.getOutputStream().write(buffer.array(), 0, len);
                // 重置缓冲区
                buffer.clear();
            }
        } catch (IOException e) {
            throw XException.builder().message("文件读取错误").build();
        }
    }

    final Path uploadDirectory = Path.of("E:/");

    // 地址栏可传递参数
    // @RequestPart，从form表单获取基本参数
    // MultipartFile，封装上传的二进制文件
    @PostMapping("upload/{fileparam}")
    public ResultVO postFile(@PathVariable String fileparam,
                             @RequestPart String pname,
                             MultipartFile file) {
        String fileName;
        // 上传文件不存在，或文件名称不存在
        if (file == null || (fileName = file.getOriginalFilename()) == null) {
            throw XException.builder().message("上传错误").build();
        }
        log.debug("filename: {}; pname: {}; size: {}", fileName, pname, file.getSize());
        try {
            // 可忽略上传文件名称，按服务器端逻辑重命名保存
            file.transferTo(uploadDirectory.resolve(Path.of(fileName)));
        } catch (IOException e) {
            throw XException.builder().message("上传错误").build();
        }
        return ResultVO.ok();
    }
}
