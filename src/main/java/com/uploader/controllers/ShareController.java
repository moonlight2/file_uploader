package com.uploader.controllers;

import com.uploader.response.ajax.AjaxFilesResponse;
import com.uploader.dao.FileDao;
import com.uploader.dao.ShareDao;
import com.uploader.services.ShareService;
import com.uploader.services.UploadService;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShareController {

    @Resource(name = "shareService")
    private ShareService shareService;
    @Resource(name = "uploadService")
    private UploadService uploadService;
    @Resource(name = "fileDao")
    private FileDao fileDao;
    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/sharelink.htm", method = RequestMethod.POST)
    public @ResponseBody
    String share(
            @RequestParam(value = "days", required = false) int days,
            @RequestParam(value = "hash", required = false) String hash) throws IOException {

        shareService.shareLink(days, hash, fileDao.getFileByHash(hash));
        AjaxFilesResponse ajaxResponse = new AjaxFilesResponse();
        ajaxResponse.setSuccess(true);
        ajaxResponse.setCount(fileDao.getCount());
        ajaxResponse.setTotalSize(fileDao.getTotalSize());
        ajaxResponse.setSizeLimit(fileDao.getSizeLimit());
        ajaxResponse.setLink(httpServletRequest.getContextPath() + "/link.htm?hash=" + hash);
        return ajaxResponse.toString();
    }

    @RequestMapping(value = "/link.htm", method = RequestMethod.GET)
    public void downloadSharedFile(@RequestParam(value = "hash", required = false) String hash,
            HttpServletResponse response) throws IOException {

        uploadService.downloadSharedFile(hash, response);
    }
}
