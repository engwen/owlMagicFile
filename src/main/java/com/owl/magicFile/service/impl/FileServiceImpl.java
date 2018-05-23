package com.owl.magicFile.service.impl;


import com.owl.magicFile.dao.OMFileDao;
import com.owl.magicFile.model.OMFile;
import com.owl.magicFile.service.OMFileService;
import com.owl.magicFile.utils.PropertiesUtil;
import com.owl.magicFile.vo.OMFileVO;
import com.owl.magicUtil.constant.MsgConstantUtil;
import com.owl.magicUtil.util.MD5Util;
import com.owl.magicUtil.vo.PageVO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import java.util.UUID;

/**
 * @author engwen
 *         email xiachanzou@outlook.com
 *         2017/7/11.
 */
@Service("fileService")
public class FileServiceImpl implements OMFileService {
    private static final String uploadPath = PropertiesUtil.readConfigProperties("upload.path.dir");

    private static Logger logger = Logger.getLogger(FileServiceImpl.class.getName());
    @Resource
    OMFileDao fileDao;


    /**
     * 下載文件
     * @param md5
     * @return
     */
    public File downloads(String md5) {
        logger.info("request downloads file");
        File result = null;
        OMFile file = fileDao.selectByMD5(md5);
        if (null != file) {
            result = new File(uploadPath + File.separatorChar + md5);
        }
        return result;
    }

    /**
     * 上傳文件
     * @param imgStr
     * @return
     */
    public OMFileVO uploadFileByBase64(String imgStr) {
        OMFileVO fileVO = new OMFileVO();
        BASE64Decoder decoder = new BASE64Decoder();
        String tempName = UUID.randomUUID() + String.valueOf(new Date().getTime());
        try {
            imgStr = imgStr.split(",")[1];
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = uploadPath + File.separatorChar + tempName;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            fileVO.setMd5(countMD5(tempName));
        } catch (Exception e) {
            logger.error("upload by base64 is error " + e);
            fileVO.errorResult(MsgConstantUtil.REQUEST_NO_KNOW_ERROR_CODE, MsgConstantUtil.REQUEST_NO_KNOW_ERROR_MSG);
        }
        return fileVO;
    }

    /**
     * 上傳文件
     * @param files
     * @return
     */
    public PageVO<OMFileVO> uploadFilesByFrom(MultipartFile[] files) {
        logger.info("request uploadByRequest file");
        PageVO<OMFileVO> filePageVO = new PageVO<>();
        for (MultipartFile mf : files) {
            if (!mf.isEmpty()) {
                filePageVO.getObjectList().add(uploadFileByFrom(mf));
            }
        }
        return filePageVO;
    }

    public OMFileVO uploadFileByFrom(MultipartFile file) {
        OMFileVO fileVO = new OMFileVO();
        try {
            String tempName = UUID.randomUUID() + String.valueOf(new Date().getTime());
            File saveFile = new File(uploadPath + File.separatorChar + tempName);
            file.transferTo(saveFile);
            fileVO.setMd5(countMD5(tempName));
            fileVO.setName(file.getOriginalFilename());
            fileVO.setSize(file.getSize());
            fileVO.setType(file.getContentType());
            fileVO.setUploadTime(new Date());
        } catch (Exception e) {
            logger.info("uploadByRequest is error" + e);
            fileVO.errorResult(MsgConstantUtil.REQUEST_NO_KNOW_ERROR_CODE, MsgConstantUtil.REQUEST_NO_KNOW_ERROR_MSG);
        }
        return fileVO;
    }


    private String countMD5(String tempName) {
        File saveFile = new File(uploadPath + File.separatorChar + tempName);
        String md5 = MD5Util.getMD5(saveFile);
        File md5File = new File(uploadPath + File.separatorChar + md5);
        OMFile file = fileDao.selectByMD5(md5);
        if (null == file) {
            file = new OMFile();
            file.setMd5(md5);
            file.setUploadTime(new Date());
            fileDao.insert(file);
            saveFile.renameTo(md5File);
        } else {
            saveFile.delete();
        }
        return md5;
    }
}
