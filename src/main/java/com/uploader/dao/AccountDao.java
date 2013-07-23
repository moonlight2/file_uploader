
package com.uploader.dao;

import com.uploader.models.Account;


public interface AccountDao extends GenericDao<Account, Long> {

    public Account getAccountByLogin(String login);

    public boolean exists(String userName);
}
