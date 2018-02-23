package com.owl.magicFile.conf;

import com.owl.magicFile.utils.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;


/**
 * 項目初始化
 * author engwen
 * email xiachanzou@outlook.com
 * time 2017/12/04.
 */
public class ServletListener implements InitializingBean {//implements InitializingBean

    private static final Logger logger = Logger.getLogger(ServletListener.class.getName());

    public void afterPropertiesSet() throws Exception {
        logger.info("init now");
        File fileDir = new File(PropertiesUtil.readConfigProperties("upload.path.dir"));
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
    }
}
