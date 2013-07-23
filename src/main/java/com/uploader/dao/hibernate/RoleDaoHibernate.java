/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uploader.dao.hibernate;

import com.uploader.dao.RoleDao;
import com.uploader.models.Account;
import com.uploader.models.Role;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("roleDao")
public class RoleDaoHibernate extends GenericDaoHibernate<Role, Long> implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public RoleDaoHibernate(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super(Role.class);
        setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        sessionFactory.getCurrentSession().save(role);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public Role getByAccountAndRole(Account account, String role) {
        List<Role> res = this.getHibernateTemplate().find(
                "from " + Role.class.getName()
                + " obj where obj.acc = ? and obj.roleValue = ?",
                account, role);
        if (res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    @Override
    public List<Role> getByAccount(Account account) {
        List<Role> res = this.getHibernateTemplate()
                .find("from " + Role.class.getName()
                + " obj where obj.acc = ?", account);
        if (res.isEmpty()) {
            return null;
        }
        return res;
    }
}
