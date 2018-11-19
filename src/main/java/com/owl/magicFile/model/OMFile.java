package com.owl.magicFile.model;


import com.owl.magicUtil.model.ModelPrototype;

import java.util.Date;

public class OMFile extends ModelPrototype {
    private String md5;

    private Date uploadTime;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}