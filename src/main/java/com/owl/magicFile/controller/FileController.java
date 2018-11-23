package com.owl.magicFile.controller;

import com.owl.magicFile.service.OMFileService;
import com.owl.magicUtil.model.MsgConstant;
import com.owl.magicUtil.util.RegexUtil;
import com.owl.magicUtil.vo.MsgResultVO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件控製類
 *
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/7/13.
 */
@Controller
@RequestMapping("file")
public class FileController {
    private static final Logger logger = Logger.getLogger(FileController.class.getName());
    @Resource
    private OMFileService fileService;

    /**
     * 多文件上传 指定 from 表單屬性 enctype="multipart/form-data" ，指定from表單中待提交文件name為 files
     *
     * @param files
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadFilesByFrom")
    public MsgResultVO uploadFilesByFrom(@RequestParam("files") MultipartFile[] files) {
        logger.info("upload files by form");
        MsgResultVO result = new MsgResultVO();
        if (null != files && files.length > 0) {
            result = fileService.uploadFilesByFrom(files);
        } else {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        return result;
    }

    /**
     * 单文件上传 指定 from 表單屬性 enctype="multipart/form-data" ，指定from表單中待提交文件name為 file
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadFileByFrom")
    public MsgResultVO uploadFileByFrom(@RequestParam("file") MultipartFile file) {
        logger.info("upload file by form");
        MsgResultVO result = new MsgResultVO();
        if (!file.isEmpty()) {
            result = fileService.uploadFileByFrom(file);
        } else {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        return result;
    }

    /**
     * 使用base64上传文件
     *
     * @param byBase64
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadFileByBase64")
    public MsgResultVO uploadFileByBase64(String byBase64) {
        logger.info("upload file by base64");
        MsgResultVO result = new MsgResultVO();
        if (RegexUtil.isEmpty(byBase64)) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR);
        } else {
            result = fileService.uploadFileByBase64(byBase64);
        }
        logger.info("result is " + result);
        return result;
    }

    /**
     * 下载
     *
     * @param response
     * @param md5
     */
    @ResponseBody
    @RequestMapping("/download")
    public void download(HttpServletResponse response, String md5) {
        logger.info("download file");
        fileService.download(response, md5);
    }
}
