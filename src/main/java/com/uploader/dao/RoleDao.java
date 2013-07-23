
package com.uploader.dao;

import com.uploader.models.Account;
import com.uploader.models.Role;
import java.util.List;


public interface RoleDao extends GenericDao<Role, Long> {

    public void addRole(Role role);

    public Role getByAccountAndRole(Account account, String string);
	
    public List<Role> getByAccount(Account account);
}
