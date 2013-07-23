package com.uploader.dao;

import com.uploader.models.UploadedFile;
import com.uploader.models.SharedFile;
import java.util.Date;


public interface ShareDao extends GenericDao<SharedFile, Long> {

    public void shareFile(String hash, Date expireDate,  UploadedFile file);

    public SharedFile getFileByHash(String hash);
    
    public void setLinkLive(Date expiredDate, SharedFile sharedFile);
}
