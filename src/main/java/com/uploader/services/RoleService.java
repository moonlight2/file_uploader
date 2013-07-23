package com.uploader.services;

import com.uploader.models.Account;
import com.uploader.models.Role;
import java.util.List;

public interface RoleService extends GenericService<Role, Long> {

    public Role getByAccountAndRole(Account account, String string);

    public List<Role> getByAccount(Account account);
}
