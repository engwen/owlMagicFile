package com.owl.magicFile.dao;

import com.owl.magicFile.model.OMFile;

public interface OMFileDao {

    int insert(OMFile record);


    OMFile selectByMD5(String md5);

}