package com.uploader.services;

import com.uploader.models.Account;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.ModelAndView;

public interface AccountService extends GenericService<Account, Long>, UserDetailsService {

    public Account setAuthentication(String user, String password);

    public Account setAuthentication(String user, String password, Collection<? extends GrantedAuthority> authorities, HttpServletResponse httpServletResponse);

    public Account getLoggedInAccount();

    public boolean hasOneRole(String role);

    public void setRole(Account account, String roleName);

    public String getRole();

    public Account saveAccount(String email, String password, String roleValue, Long sizeLimit);

    public ModelAndView saveAccount(
            ModelAndView mav,
            Long id,
            String zip,
            String gender,
            Date age,
            String email,
            String firstName,
            String lastName,
            String password,
            String roleValue);

    public void deleteAccount(Account account);

    public Account getAccountByLogin(String login);

    public boolean hasRole(ArrayList<String> roles);

    public void logout();
}
