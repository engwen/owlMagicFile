package com.owl.magicFile.utils;

import com.owl.magicUtil.util.RegexUtil;
import org.springframework.http.converter.json.MappingJacksonValue;

/**
 * 提供跨域解决办法
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/4/20.
 */
public class AjaxCrossUtil {
    /**
     * 解决跨域
     * @return
     */
    public static Object backResult(Object result, String callback) {
        if (!RegexUtil.isEmpty(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
