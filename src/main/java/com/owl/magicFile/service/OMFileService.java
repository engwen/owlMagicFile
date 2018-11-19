package com.owl.magicFile.service;


import com.owl.magicFile.model.OMFile;
import com.owl.magicFile.vo.OMFileVO;
import com.owl.magicUtil.vo.MsgResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件服務類
 *
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/7/11.
 */
public interface OMFileService {

    //    上傳文件
    MsgResultVO<OMFileVO> uploadFileByBase64(String fileBase64);

    MsgResultVO<List<OMFileVO>> uploadFilesByFrom(MultipartFile[] files);

    MsgResultVO<OMFileVO> uploadFileByFrom(MultipartFile file);

    void download(HttpServletResponse response, String md5);
    //    查詢將要下載的文件
    MsgResultVO<OMFile> selectByMD5(String MD5);
}
