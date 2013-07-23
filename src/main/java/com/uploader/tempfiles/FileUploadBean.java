package com.uploader.tempfiles;

import javax.validation.constraints.Max;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author iosipov
 */
public class FileUploadBean {

    private CommonsMultipartFile file;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}
