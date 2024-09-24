package tq.tyd.knows.resource.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@Slf4j
//跨域注解
@CrossOrigin
public class ImageController {
    @Value("${knows.resource.path}")
    private File resourcePath;

    @Value("${knows.resource.host}")
    private String resourceHost;

    @PostMapping
    public String upload(MultipartFile imageFile) throws IOException {
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        File folder = new File(resourcePath,path);
        folder.mkdirs();
        String fileName = imageFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String name = UUID.randomUUID().toString()+ext;
        File file = new File(folder, name);
        log.debug("文件上传路径:{}", file.getAbsolutePath());
        imageFile.transferTo(file);
        String url = resourceHost+"/"+path+"/"+name;
        log.debug("回显上传图片的url:{}", url);
        return url;
    }
}
