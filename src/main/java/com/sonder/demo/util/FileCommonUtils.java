package com.sonder.demo.util;

import com.sonder.demo.vo.FileUploadResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @Author likun
 * @Date 2020-12-08
 **/
@Slf4j
public class FileCommonUtils {

    public static FileUploadResponseVO uploadFile(MultipartFile file, String basePath) throws IOException {
        long startTime = System.currentTimeMillis();
        String fileTypeName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = RandomUtils.generateRandomFileName() + fileTypeName;
        String fileFullName = fileName;
        FileUtils.forceMkdir(new File(basePath));
        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(basePath + fileFullName));
        long endTime = System.currentTimeMillis();
        log.info("上传文件耗时：{} ms", (endTime - startTime));
        return new FileUploadResponseVO(file.getOriginalFilename(), fileFullName, file.getSize(), fileTypeName, new Date());
    }

}
