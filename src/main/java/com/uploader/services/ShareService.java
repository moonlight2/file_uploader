
package com.uploader.services;

import com.uploader.models.SharedFile;
import com.uploader.models.UploadedFile;


public interface ShareService {
    
    public void shareLink(Integer numberOfDays, String hashName, UploadedFile file);
    
    public void deleteExpiredLinks();
    
    public SharedFile setSharedDate(Integer numberOfDays, String hashName);
    
}
