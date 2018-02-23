package com.owl.magicFile.vo;

import com.owl.magicFile.model.OMFile;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/02/23.
 */
public class OMFileVO extends OMFile {
    private String name;
    private Long size;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
