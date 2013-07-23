package com.uploader.services.impl;

import com.uploader.dao.ShareDao;
import com.uploader.models.UploadedFile;
import com.uploader.models.SharedFile;
import com.uploader.services.ShareService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


@Service("shareService")
public class ShareServiceImpl implements ShareService {

    @Resource(name = "shareDao")
    private ShareDao shareDao;
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final static Integer BASIC_EXPIRED_DAYS_NUMBER = 1;

    private Date setLinkLive(Integer days) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        String dateExpired = DATE_FORMAT.format(calendar.getTime());
        try {
            return (Date) DATE_FORMAT.parse(dateExpired);
        } catch (ParseException ex) {
            Logger.getLogger(ShareServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteExpiredLinks() {

        List<SharedFile> list = shareDao.getAll();
        if (null != list) {
            Calendar current = Calendar.getInstance();
            Calendar expired = Calendar.getInstance();

            try {
                current.setTime(DATE_FORMAT.parse(DATE_FORMAT.format(Calendar.getInstance().getTime())));
            } catch (ParseException ex) {
                Logger.getLogger(ShareServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (SharedFile sharedFile : list) {
                expired.setTime(sharedFile.getExpireDate());
                if (current.after(expired)) {
                    shareDao.remove(sharedFile.getId());
                }
            }
        }
    }

    @Override
    public void shareLink(Integer numberOfDays, String hashName, UploadedFile file) {

        if (null == numberOfDays) {
            numberOfDays = BASIC_EXPIRED_DAYS_NUMBER;
        }
        Date dateExpired = setLinkLive(numberOfDays);
        shareDao.shareFile(hashName, dateExpired, file);
    }

    @Override
    public SharedFile setSharedDate(Integer numberOfDays, String hashName) {

        SharedFile sharedFile = shareDao.getFileByHash(hashName);
        if (null != sharedFile && null == sharedFile.getConfirmation()) {
            Date dateExpired = setLinkLive(numberOfDays);
            sharedFile.setConfirmation("approved");
            shareDao.setLinkLive(dateExpired, sharedFile);
            return sharedFile;
        } else {
            return null;
        }
    }
}
