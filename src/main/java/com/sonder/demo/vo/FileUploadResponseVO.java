package com.sonder.demo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("文件上传响应字段")
@Data
public class FileUploadResponseVO implements Serializable {
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private String rcrdUdtUsrName;
    private Long rcrdUdtUsrId;
    private Date rcrdUdtTm;

    public FileUploadResponseVO(String fileName, String filePath, Long fileSize, String fileType, Date rcrdUdtTm) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.rcrdUdtUsrName = rcrdUdtUsrName;
        this.rcrdUdtUsrId = rcrdUdtUsrId;
        this.rcrdUdtTm = rcrdUdtTm;
    }
}
