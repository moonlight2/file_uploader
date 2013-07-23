package com.uploader.services.impl;

import com.uploader.dao.AccountDao;
import com.uploader.dao.RoleDao;
import com.uploader.models.Account;
import com.uploader.models.Role;
import com.uploader.services.AccountService;
import com.uploader.services.CryptService;
import com.uploader.services.RoleService;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource(name = "userDao")
    private AccountDao accountDao;
    @Resource(name = "roleService")
    private RoleService roleService;
    @Resource(name = "roleDao")
    RoleDao roleDao;
    @Resource(name = "cryptService")
    private CryptService cryptService;

    @Override
    public Account save(Account model) {
        return accountDao.save(model);
    }

    @Override
    public Account saveAccount(String email, String password, String roleValue, Long sizeLimit) {

        Account account = new Account();
        String salt = cryptService.getSalt(account.getPassword());
        String encryptedPassword = cryptService.cryptPassword(password, salt);
        Role role = new Role(roleValue, account);
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        if (accountDao.exists(email) == false) {
            account.setPassword(encryptedPassword);
            account.setEmail(email);
            account.setRoles(roles);
            account.setSizeLimit(sizeLimit);
            return save(account);
        }
        return null;
    }

    @Override
    public Account get(Long id) {
        return accountDao.get(id);
    }

    @Override
    public void remove(Long id) {
        accountDao.remove(id);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return getAccountByLogin(username);
    }

    @Override
    public Account setAuthentication(String user, String password) {
        Account account = getAccountByLogin(user);
        if (null != account) {
            String salt = cryptService.getSalt(account.getPassword());
            String encryptedPassword = cryptService.cryptPassword(password, salt);
            String accountPassword = account.getPassword();
            if (accountPassword.equals(encryptedPassword)) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, password, account.getAuthorities()));
                return account;
            }
        }
        return null;
    }

    @Override
    public Account setAuthentication(String user, String password, Collection<? extends GrantedAuthority> authorities, HttpServletResponse httpServletResponse) {

        setAuthentication(user, password);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }

    @Override
    public Account getAccountByLogin(String login) {
        Account account = accountDao.getAccountByLogin(login);
        return account;
    }

    @Override
    public Account getLoggedInAccount() {
        
        Account account;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }

            account = this.getAccountByLogin(username);
            Collection<GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            System.out.println(authorities.toString());

            return account;
        } catch (ClassCastException cce) {
            return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public boolean hasRole(ArrayList<String> roles) {
        try {
            Collection<GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            for (GrantedAuthority auth : authorities) {
                for (String role : roles) {
                    if (auth.getAuthority().equals(role)) {
                        return true;
                    }
                }
            }
        } catch (NullPointerException e) {
        }

        return false;
    }

    @Override
    public void setRole(Account account, String roleName) {

        //remove old roles
        List<Role> oroles = roleService.getByAccount(account);
        for (Role orole : oroles) {
            roleService.remove(orole.getId());
        }

        //add new role
        Role role = new Role();
        role.setRoleValue(roleName);
        role.setAcc(account);
        role = roleService.save(role);

        //add role to account
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        account.setRoles(roles);
        save(account);
    }

    @Override
    public String getRole() {
        try {
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.isEmpty()) {
                return "ROLE_ANONYMOUS";
            } else {
                return authorities.get(0).getAuthority();
            }
        } catch (Exception ex) {
            return "ROLE_ANONYMOUS";
        }
    }

    @Override
    public ModelAndView saveAccount(ModelAndView mav, Long id, String zip, String gender, Date age, String email, String firstName, String lastName, String password, String roleValue) {
        Account account = new Account();
        if (null != id && 0L < id) {
            account = get(id);
        }

        if (null == email && null != id && 0L < id) {
            mav.addObject("status", HttpServletResponse.SC_PRECONDITION_FAILED);
            mav.addObject("description", "email is required");
            return mav;
        } else if (null == password && 0L == id) {
            mav.addObject("status", HttpServletResponse.SC_PRECONDITION_FAILED);
            mav.addObject("description", "password is required");
            return mav;
        } else if (null == firstName && null != id && 0L < id) {
            mav.addObject("status", HttpServletResponse.SC_PRECONDITION_FAILED);
            mav.addObject("description", "firstName is required");
            return mav;
        } else if (null == lastName && null != id && 0L < id) {
            mav.addObject("status", HttpServletResponse.SC_PRECONDITION_FAILED);
            mav.addObject("description", "lastName is required");
            return mav;
        } else if (null == zip && null != id && 0L < id) {
            mav.addObject("status", HttpServletResponse.SC_PRECONDITION_FAILED);
            mav.addObject("description", "zip is required");
            return mav;
        } else if (null == age && null != id && 0L < id) {
            mav.addObject("status", HttpServletResponse.SC_PRECONDITION_FAILED);
            mav.addObject("description", "age is required");
            return mav;
        } else if (null == gender && null != id && 0L < id) {
            mav.addObject("status", HttpServletResponse.SC_PRECONDITION_FAILED);
            mav.addObject("description", "gender is required");
            return mav;
        }

        if (null != password) {
            account.setPassword(password);
        }

        if (null != age) {
            account.setBirthday(age);
        }
        if (null != firstName) {
            account.setFirstName(firstName);
        }
        if (null != gender) {
            account.setGender(gender);
        }
        if (null != lastName) {
            account.setLastName(lastName);
        }
        if (null != zip) {
            account.setZip(zip);
        }
        try {
            account = save(account);

            Role newRole = new Role();
            newRole.setRoleValue(roleValue);
            newRole.setAcc(account);
            roleDao.addRole(newRole);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            mav.addObject("status", HttpServletResponse.SC_CONFLICT);
            mav.addObject("description", "User exists");
            return mav;
        }

        mav.addObject("status", HttpServletResponse.SC_OK);
        mav.addObject("account", account);
        return mav;
    }

    @Override
    public void deleteAccount(Account account) {
        accountDao.remove(account.getId());
    }

    @Override
    public boolean hasOneRole(String role) {
        ArrayList<String> roles = new ArrayList<String>();
        roles.add(role);
        return hasRole(roles);
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
