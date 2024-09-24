package tq.tyd.knows.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tq.tyd.knows.portal.exception.ServiceException;
import tq.tyd.knows.portal.service.IUserService;
import tq.tyd.knows.portal.vo.RegisterVo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class SystemController {
    @Value("${knows.resource.path}")
    private File resourcePath;
    @Value("${knows.resource.host}")
    private String resourceHost;
    @Autowired
    IUserService iUserService;
    @PostMapping("/register")
    public String register(@Validated RegisterVo registerVo, BindingResult result){
        log.debug("接收到表单信息:{}",registerVo);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        try{
            iUserService.registerStudent(registerVo);
            return "ok";
        }catch (ServiceException e){
            log.error("注册失败", e);
            return e.getMessage();
        }
    }

    @PostMapping("/upload/file")
    public String upload(MultipartFile imageFile) throws IOException{
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
