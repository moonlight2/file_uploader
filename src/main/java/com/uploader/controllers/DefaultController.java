package com.uploader.controllers;

import com.uploader.dao.FileDao;
import com.uploader.models.Account;
import com.uploader.response.ajax.AjaxFilesResponse;
import com.uploader.services.AccountService;
import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {

    @Resource(name = "fileDao")
    private FileDao fileDao;
    @Autowired
    protected HttpServletRequest httpServletRequest;
    @Resource(name = "accountService")
    private AccountService accountService;
    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(value = "login.htm", method = RequestMethod.GET)
    public ModelAndView showLoginForm(Account account) {

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login.htm", method = RequestMethod.POST)
    public @ResponseBody
    String loginHandler(
            HttpServletResponse httpServletResponse,
            @Valid Account account, BindingResult result) throws IOException {

        AjaxFilesResponse ajaxResponse = new AjaxFilesResponse();

        if (result.hasErrors()) {
            ajaxResponse.setSizeLimit(fileDao.getSizeLimit());
            ajaxResponse.setResponceText("Accoun or password is wrong. Autorization failed!");
        } else {
            if (null != accountService.setAuthentication(account.getEmail(), account.getPassword())) {
                ArrayList<String> roles = new ArrayList<String>();
                roles.add("ROLE_USER");
                roles.add("ROLE_ADMIN");
                if (accountService.hasRole(roles)) {
                    ajaxResponse.setSizeLimit(fileDao.getSizeLimit());
                    ajaxResponse.setSuccess(true);
                }
            } else {
                ajaxResponse.setSuccess(false);
                ajaxResponse.setResponceText("Accoun or password is wrong. Autorization failed!");
            }
        }
        return ajaxResponse.toString();
    }

    @RequestMapping(value = "registration.htm", method = RequestMethod.GET)
    public ModelAndView showRegisterForm(Account account) {

        return new ModelAndView("registration");
    }

    @RequestMapping(value = "registration.htm", method = RequestMethod.POST)
    public @ResponseBody
    String registerHandler(
            HttpServletResponse httpServletResponse,
            @Valid Account account, BindingResult result) throws IOException {

        AjaxFilesResponse ajaxResponse = new AjaxFilesResponse();

        if (result.hasErrors()) {
            ajaxResponse.setSuccess(false);
            ajaxResponse.setResponceText("Error. Registration failed!");
        } else {
            long sizeLimit = 20000000;
            String roleValue = "ROLE_USER";
            if (null != accountService.saveAccount(account.getEmail(), account.getPassword(), roleValue, sizeLimit)) {
                ajaxResponse.setSuccess(true);
                ajaxResponse.setResponceText("User successfully registered");
            } else {
                ajaxResponse.setSuccess(true);
                ajaxResponse.setResponceText("Error. User is already exists!");
            }
        }
        return ajaxResponse.toString();
    }

    @RequestMapping("logout.htm")
    public void logoutHandler(HttpServletResponse httpServletResponse) throws IOException {

        accountService.logout();
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.htm");
    }
}
