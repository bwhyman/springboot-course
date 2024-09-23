package com.example.springmvcexamples.example09.file;

import com.example.springmvcexamples.exception.XException;
import com.example.springmvcexamples.vo.ResultVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
@RestController
@RequestMapping("/api/example09/")
@RequiredArgsConstructor
public class ExampleController09 {
    // 测试下载文件
    final Path filePath = Path.of("E:/video/Mr. Bean at Olympics.mp4");

    // 模拟传入文件获取的ID/名称等参数
    // 注入response对象，以获取response headers
    // 以二进制字节流+缓冲区下载，避免大文件内存溢出
    @GetMapping("files/{fileparam}")
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
