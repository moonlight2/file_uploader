package com.uploader.dao.hibernate;

import com.uploader.dao.AccountDao;
import com.uploader.models.Account;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
public class AccountDaoHibernate extends GenericDaoHibernate<Account, Long> implements AccountDao {

    @Autowired
    public AccountDaoHibernate(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super(Account.class);
        setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public Account getAccountByLogin(String login) {
        List list = getHibernateTemplate().find("from Account a where a.email = ?", login);
        return (!list.isEmpty()) ? (Account) list.get(0) : null;
    }

    @Override
    public boolean exists(String userName) {
        List list = getHibernateTemplate().find("from Account a where a.email = ?", userName);
        return !list.isEmpty();
    }
}
