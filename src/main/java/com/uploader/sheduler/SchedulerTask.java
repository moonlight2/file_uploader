package com.uploader.sheduler;

import com.uploader.services.ShareService;
import javax.annotation.Resource;

public class SchedulerTask {

    @Resource(name = "shareService")
    private ShareService shareService;

    public void cleanLinks() {
        shareService.deleteExpiredLinks();
        System.out.println("Links has been deleted.......");
    }
}
