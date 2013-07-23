package com.uploader.dao;

import com.uploader.models.Account;
import com.uploader.models.UploadedFile;
import java.util.List;

/**
 *
 * @author iosipov
 */
public interface FileDao extends GenericDao<UploadedFile, Long> {

    public void addFile(String fileName, String filePath, String hash, Account account, Long fileSize);

    public UploadedFile getFileByHash(String hash);
    
    public UploadedFile getFileByName(String name);
    
    public List<UploadedFile> getOwnFiles();
    
    public List<UploadedFile> getPartOfFiles(int start, int limit);
    
    public Long getTotalSize();
    
    public long getSizeLimit();
    
    public int getCount();
}
