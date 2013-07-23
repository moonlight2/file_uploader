package com.uploader.dao.hibernate;

import com.uploader.dao.ShareDao;
import com.uploader.models.UploadedFile;
import com.uploader.models.SharedFile;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("shareDao")
public class ShareDaoHibernate extends GenericDaoHibernate<SharedFile, Long> implements ShareDao {

    @Autowired
    public ShareDaoHibernate(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super(SharedFile.class);
        setSessionFactory(sessionFactory);
    }

    @Override
    public void shareFile(String hash, Date expireDate, UploadedFile file) {
        SharedFile sharedFile = new SharedFile(hash, expireDate,  file);
        this.getHibernateTemplate().save(sharedFile);
    }

    @Override
    public SharedFile getFileByHash(String hash) {
        List list = getHibernateTemplate().find("from SharedFile sf where sf.hashName = ?", hash);
        return (!list.isEmpty()) ? (SharedFile) list.get(0) : null;
    }


    @Override
    @Transactional
    public void setLinkLive(Date expiredDate, SharedFile sharedFile) {
        sharedFile.setExpireDate(expiredDate);
        this.getHibernateTemplate().update(sharedFile);
    }



}
