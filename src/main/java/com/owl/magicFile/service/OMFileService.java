package com.owl.magicFile.service;



import com.owl.magicFile.vo.OMFileVO;
import com.owl.magicUtil.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件服務類
 * @author engwen
 *         email xiachanzou@outlook.com
 *         2017/7/11.
 */
public interface OMFileService {

    //    上傳文件
    OMFileVO uploadFileByBase64(String fileBase64);

    PageVO<OMFileVO> uploadFilesByFrom(MultipartFile[] files);
    OMFileVO uploadFileByFrom(MultipartFile file);

    //    下載文件
    File downloads(String MD5);
}
