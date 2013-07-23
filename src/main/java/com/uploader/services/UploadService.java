
package com.uploader.services;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public interface UploadService {
    
    public String upload(CommonsMultipartFile file);
    
    public void delete(String hash);
    
    public void downloadUploadedFile(String hash, HttpServletResponse response);
    
    public void downloadSharedFile(String hash, HttpServletResponse response);
}
