package com.uploader.services.impl;

import com.uploader.dao.FileDao;
import com.uploader.dao.ShareDao;
import com.uploader.models.Account;
import com.uploader.models.SharedFile;
import com.uploader.models.UploadedFile;
import com.uploader.services.AccountService;
import com.uploader.services.CryptService;
import com.uploader.services.UploadService;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    @Resource(name = "accountService")
    private AccountService accountService;
    
    @Resource(name = "cryptService")
    private CryptService cryptService;
    
    @Resource(name = "fileDao")
    private FileDao fileDao;
    
    @Resource(name = "shareDao")
    private ShareDao shareDao;
    
    @Value("${uploadDir}")
    private String DIR_NAME;
    
    @Value("${separator}")
    private String SEPARATOR;
    
    @Value("${maxFileSize}")
    private long MAX_FILE_SIZE;
    
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public String upload(CommonsMultipartFile file) {

        Account account = accountService.getLoggedInAccount();
        String accountName = account.getEmail();
        String fileName = file.getOriginalFilename();
        makeDir(accountName);

        if (null != fileDao.getFileByName(fileName)) {
            return "File is already on the server!";
        }  else if (file.getSize() > MAX_FILE_SIZE) {
            return "File is to large!";
        } else if (null != fileDao.getTotalSize() && (file.getSize() + fileDao.getTotalSize()) > account.getSizeLimit()) {
            return "Yo have been expired free space!";
        } else {
            String filePath = DIR_NAME + accountName + SEPARATOR + fileName;
            String hashName = cryptService.cryptFilename(fileName);
            try {
                file.transferTo(new File(filePath));
            } catch (IOException ex) {
                Logger.getLogger(UploadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(UploadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            fileDao.addFile(fileName, filePath, hashName, account, file.getSize());
            return null;
        }

    }

    private void makeDir(String accountName) {
        File dir = new File(DIR_NAME + accountName);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void delete(String hash) {

        Account account = accountService.getLoggedInAccount();
        if (null != hash) {
            UploadedFile file = fileDao.getFileByHash(hash);

            if (null != account && account.equals(file.getAcc())) {
                fileDao.remove(file.getId());
                new File(file.getFilePath()).delete();
            }
        }
    }

    private void download(UploadedFile file, HttpServletResponse response) {

        String filePath = file.getFilePath();
        String fileName = file.getFileName();
        InputStream in = null;

        try {
            in = new BufferedInputStream(new FileInputStream(new File(filePath)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UploadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        try {
            FileCopyUtils.copy(in, response.getOutputStream());
            in.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(UploadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void downloadUploadedFile(String hash, HttpServletResponse response) {

        Account account = accountService.getLoggedInAccount();
        UploadedFile file = fileDao.getFileByHash(hash);

        if (null != account && account.equals(file.getAcc())) {
            download(file, response);
        }
    }

    @Override
    public void downloadSharedFile(String hash, HttpServletResponse response) {

        SharedFile file = shareDao.getFileByHash(hash);
        if (null != file) {
            UploadedFile uploadedFile = file.getFile();
            download(uploadedFile, response);
        }
    }
}
