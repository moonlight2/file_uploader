package com.uploader.tempfiles;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author iosipov
 */
public class UploadedFiles {

    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
