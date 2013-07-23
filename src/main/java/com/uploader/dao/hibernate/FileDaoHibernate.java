package com.uploader.dao.hibernate;

import com.uploader.dao.FileDao;
import com.uploader.models.Account;
import com.uploader.models.Role;
import com.uploader.models.UploadedFile;
import com.uploader.services.AccountService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author iosipov
 */
@Repository("fileDao")
public class FileDaoHibernate extends GenericDaoHibernate<UploadedFile, Long> implements FileDao {

    @Resource(name = "accountService")
    private AccountService accountService;
    @Autowired
    private SessionFactory sessionFactory;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    public FileDaoHibernate(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super(UploadedFile.class);
        setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void addFile(String fileName, String filePath, String hash, Account account, Long fileSize) {

        UploadedFile file = new UploadedFile(fileName, filePath, hash, account, fileSize);
        this.getHibernateTemplate().save(file);
    }

    @Override
    public List<UploadedFile> getOwnFiles() {

        Account account = accountService.getLoggedInAccount();
        List<UploadedFile> list = getHibernateTemplate().find("from UploadedFile f where f.acc = ?", account);
        return (!list.isEmpty()) ? list : null;
    }

    @Override
    @Transactional
    public List<UploadedFile> getPartOfFiles(int start, int limit) {

        Account account = accountService.getLoggedInAccount();
        Query q = sessionFactory.getCurrentSession().createQuery("from UploadedFile f where f.acc = :account order by f.id desc");
        q.setParameter("account", account);
        q.setFirstResult(start);
        q.setMaxResults(limit);
        List<UploadedFile> list = q.list();
        return (!list.isEmpty()) ? list : null;
    }

    @Override
    public int getCount() {
        Account account = accountService.getLoggedInAccount();
        List list = getHibernateTemplate().find("from UploadedFile f where f.acc = ?", account);
        logger.info(list);
        return (!list.isEmpty()) ? list.size() : 0;
    }

    @Override
    public UploadedFile getFileByHash(String hash) {

        List list = getHibernateTemplate().find("from UploadedFile f where f.hashName = ?", hash);
        return (!list.isEmpty()) ? (UploadedFile) list.get(0) : null;
    }

    @Override
    public UploadedFile getFileByName(String name) {

        Account account = accountService.getLoggedInAccount();
        List list = getHibernateTemplate().find("from UploadedFile f where f.fileName = ? and f.acc = ?", name, account);
        return (!list.isEmpty()) ? (UploadedFile) list.get(0) : null;
    }

    @Override
    public Long getTotalSize() {
        Account account = accountService.getLoggedInAccount();
        List list = getHibernateTemplate().find("select sum(f.fileSize)from UploadedFile f where f.acc = ?", account);
        return (!list.isEmpty()) ? (Long) list.get(0) : null;
    }

    @Override
    public long getSizeLimit() {
        Account account = accountService.getLoggedInAccount();
        return account.getSizeLimit();
    }

}
