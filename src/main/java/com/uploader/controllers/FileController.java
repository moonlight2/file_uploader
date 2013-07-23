package com.uploader.controllers;

import com.uploader.response.ajax.AjaxFilesResponse;
import com.uploader.dao.FileDao;
import com.uploader.services.AccountService;
import com.uploader.services.UploadService;
import com.uploader.tempfiles.FileUploadBean;
import com.uploader.tempfiles.UploadedFiles;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {

    @Resource(name = "fileDao")
    private FileDao fileDao;
    @Resource(name = "uploadService")
    private UploadService uploadService;
    protected final Log logger = LogFactory.getLog(getClass());
    @Resource(name = "accountService")
    private AccountService accountService;


    /* Get list of uploaded files */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "handlefiles.htm", method = RequestMethod.GET)
    public @ResponseBody
    String filesArrayHandler(
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "limit", required = false) Integer limit) {

        AjaxFilesResponse ajaxResponse = new AjaxFilesResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setCount(fileDao.getCount());
        ajaxResponse.setTotalSize(fileDao.getTotalSize());
        ajaxResponse.setSizeLimit(fileDao.getSizeLimit());
        
        if (null != start && null != limit) {
            ajaxResponse.setFilesList(fileDao.getPartOfFiles(start, limit));
        }
        return ajaxResponse.toString();
    }

    /* Delete handler */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "handlefiles.htm", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteHandler(@RequestParam(value = "hash", required = false) String hash)  {

        uploadService.delete(hash);

        AjaxFilesResponse ajaxResponse = new AjaxFilesResponse();
        ajaxResponse.setCount(fileDao.getCount());
        ajaxResponse.setTotalSize(fileDao.getTotalSize());
        ajaxResponse.setSizeLimit(fileDao.getSizeLimit());
        ajaxResponse.setFilesList(fileDao.getPartOfFiles(1, 5));
        ajaxResponse.setSuccess(true);

        return ajaxResponse.toString();
    
    }

    /* Upload a new file */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "upload.htm", method = RequestMethod.POST)
    public @ResponseBody
    String uploadHandler(
            FileUploadBean uploadItem) {

        AjaxFilesResponse ajaxResponse = new AjaxFilesResponse();
        String resp = uploadService.upload(uploadItem.getFile());
        if (null != resp) {
            ajaxResponse.setResponceText(resp);
            ajaxResponse.setSuccess(false);
        } else {
            ajaxResponse.setSizeLimit(fileDao.getSizeLimit());
            ajaxResponse.setSuccess(true);
        }
        return ajaxResponse.toString();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "upload.htm", method = RequestMethod.GET)
    public ModelAndView showUploadForm(
            @ModelAttribute("uploadFiles") UploadedFiles uploadFiles, ModelAndView mav) {

        mav = new ModelAndView("upload");
        return mav;
    }
    
    /* Download selected file */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/file.htm", method = RequestMethod.GET)
    public ModelAndView downloadHandler(@RequestParam(value = "hash", required = false) String hash,
            HttpServletResponse response,
            ModelAndView mav) {

        mav = new ModelAndView("upload");
        uploadService.downloadUploadedFile(hash, response);
        return mav;
    }
}
