package com.uploader.services.impl;

import com.uploader.dao.RoleDao;
import com.uploader.models.Account;
import com.uploader.models.Role;
import com.uploader.services.RoleService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource(name = "roleDao")
    RoleDao roleDao;

    @Override
    public Role getByAccountAndRole(Account account, String string) {
        return roleDao.getByAccountAndRole(account, string);
    }

    @Override
    public List<Role> getByAccount(Account account) {
        return roleDao.getByAccount(account);
    }

    @Override
    public Role save(Role model) {
        return roleDao.save(model);
    }

    @Override
    public Role get(Long id) {
        return roleDao.get(id);
    }

    @Override
    public void remove(Long id) {
        roleDao.remove(id);
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }
}
