package com.owl.magicFile.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/4/7.
 */
public class PropertiesUtil {
    private static final String CONFIG = "config";

    /**
     * 读取指定配置文件信息
     * @param propertiesName
     * @param key
     * @return
     */
    public static String readProperties(String propertiesName, String key) {
        return ResourceBundle.getBundle(propertiesName, Locale.getDefault()).getString(key);
    }

    /**
     * 读取配置文件信息
     * @param key
     * @return
     */
    public static String readConfigProperties(String key) {
        return readProperties(CONFIG, key);
    }
}
