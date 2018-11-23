package com.owl.magicFile.service.impl;


import com.owl.magicFile.dao.OMFileDao;
import com.owl.magicFile.model.OMFile;
import com.owl.magicFile.service.OMFileService;
import com.owl.magicFile.utils.PropertiesUtil;
import com.owl.magicFile.vo.OMFileVO;
import com.owl.magicUtil.model.MsgConstant;
import com.owl.magicUtil.util.MD5Util;
import com.owl.magicUtil.vo.MsgResultVO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/7/11.
 */
@Service("fileService")
public class FileServiceImpl implements OMFileService {
    private static final String uploadPath = PropertiesUtil.readConfigProperties("upload.path.dir");

    private static Logger logger = Logger.getLogger(FileServiceImpl.class.getName());
    @Resource
    OMFileDao fileDao;


    /**
     * 查询文件
     *
     * @param md5
     * @return
     */
    public MsgResultVO<OMFile> selectByMD5(String md5) {
        logger.info("request selectByMD5 file");
        MsgResultVO<OMFile> result = new MsgResultVO<>();
        OMFile file = fileDao.selectByMD5(md5);
        if (null != file) {
            result.successResult(file);
        } else {
            result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return result;
    }

    /**
     * 上傳文件
     *
     * @param imgStr
     * @return
     */
    public MsgResultVO<OMFileVO> uploadFileByBase64(String imgStr) {
        MsgResultVO<OMFileVO> result = new MsgResultVO<>();
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
            result.successResult(fileVO);
        } catch (Exception e) {
            logger.error("upload by base64 is error " + e);
            result.errorResult(MsgConstant.REQUEST_NO_KNOW_ERROR);
        }
        return result;
    }

    /**
     * 上傳文件
     *
     * @param files
     * @return
     */
    public MsgResultVO<List<OMFileVO>> uploadFilesByFrom(MultipartFile[] files) {
        logger.info("request uploadByRequest file");
        MsgResultVO<List<OMFileVO>> result = new MsgResultVO<>();
        for (MultipartFile mf : files) {
            if (!mf.isEmpty()) {
                result.getResultData().add(uploadFileByFrom(mf).getResultData());
            }
        }
        return result;
    }

    public MsgResultVO<OMFileVO> uploadFileByFrom(MultipartFile file) {
        MsgResultVO<OMFileVO> result = new MsgResultVO<>();
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
            result.successResult(fileVO);
        } catch (Exception e) {
            logger.info("uploadByRequest is error" + e);
            result.errorResult(MsgConstant.REQUEST_NO_KNOW_ERROR);
        }
        return result;
    }

    public void download(HttpServletResponse response, String md5) {
        MsgResultVO<OMFile> fileResult = selectByMD5(md5);
        if (fileResult.getResult()) {
            File file = new File(uploadPath + File.separatorChar + md5);
            if (!file.exists()) {
                return;
            }
            try {
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
                FileInputStream in = new FileInputStream(file);
                OutputStream out = response.getOutputStream();
                byte buffer[] = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e) {
                System.out.print(e);
            }
        }
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
