package com.uploader.response.ajax;

import com.uploader.models.UploadedFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class AjaxFilesResponse {

    private boolean success = false;
    
    List<UploadedFile> filesList = null;
    
    CommonsMultipartFile uploadedFile = null;
    
    Long totalSize = null;

    private int count;
    
    String link = null;
    
    Long sizeLimit = null;
    
    String responceText;
    
    protected final Log logger = LogFactory.getLog(getClass());

    public void setSizeLimit(Long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AjaxFilesResponse() {
    }

    public AjaxFilesResponse(CommonsMultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public AjaxFilesResponse(CommonsMultipartFile uploadedFile, Long totalSize, List<UploadedFile> filesList) {
        this.uploadedFile = uploadedFile;
        this.totalSize = totalSize;
        this.filesList = filesList;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public void setUploadedFile(CommonsMultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void setFilesList(List<UploadedFile> filesList) {
        this.filesList = filesList;
    }

    private double getBalanse(long size) {
        return (size * 100.0f) / sizeLimit;
    }

    private double sizeToMB(long size) {
        long MB = 1000000;
        return (double) size / MB;
    }

    private double roundSize(double d, int scale) {
        return new BigDecimal(d).setScale(scale, RoundingMode.UP).doubleValue();
    }

    public void setResponceText(String responceText) {
        this.responceText = responceText;
    }

    @Override
    public String toString() {

        String filesString = null;

        if (null != filesList) {
            StringBuilder sb = new StringBuilder();
            for (UploadedFile file : filesList) {
                boolean isShared = (!file.getSharedFiles().isEmpty())? true : false;
                sb.append("{'filename': '" + file.getFileName()
                        + "', 'hash': '" + file.getHashName()
                        + "', 'isShared': " + isShared
                        + ", 'size' : '" + roundSize(sizeToMB(file.getFileSize()), 2) + "'},");
            }
            filesString = sb.toString();
        }
        if (null != totalSize) {
            if (null != uploadedFile) {
                return "{'success':'" + success + "', 'responceText':'" + responceText + "', 'sizeLimit':'" + sizeToMB(sizeLimit) + "', 'totalInMB': '" + roundSize(sizeToMB(totalSize), 2) + "', 'totalInPercent': '" + (int) roundSize(getBalanse(totalSize), 0) + "', 'total':" + count + ", 'filename': '" + uploadedFile.getOriginalFilename() + "' ,'files':[" + filesString + "]}";
            } else {
                return "{'success':'" + success + "', 'responceText':'" + responceText + "', 'sizeLimit':'" + sizeToMB(sizeLimit) + "', 'totalInMB': '" + roundSize(sizeToMB(totalSize), 2) + "', 'totalInPercent': '" + (int) roundSize(getBalanse(totalSize), 0) + "', 'total':" + count + ",  'files':[" + filesString + "], 'link' : '" + link + "'}";
            }
        }
        if (null != sizeLimit ) {
            return "{'success':'" + success + "', 'responceText':'" + responceText + "', 'sizeLimit':'" + sizeToMB(sizeLimit) + "', 'totalInMB': '0' }";
        } else {
             return "{'success':'" + success + "', 'responceText':'" + responceText + "'}";
        }
    }
}
