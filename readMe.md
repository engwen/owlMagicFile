owlMagicFile工具介绍
-------

*为方便文件功能管理，减少代码耦合，并可重复使用，故而提供本工具*

* 包名
com.owl.magicFile
#### *对外提供 API 如下*  
>如无特殊说明，所有的返回结果均为MsgResult类型（可依据xxx.result判断成功或者失败）
* **/file/uploadFilesByFrom**  多文件上传  
   指定 from 表单属性 enctype="multipart/form-data" ，指定from表单中待提交文件name为 files  
* **/file/uploadFileByFrom**    单文件上传  
   指定 from 表单属性 enctype="multipart/form-data" ，指定from表单中待提交文件name为 file
* **/file/uploadFileByBase64**  使用base64上传文件  
  （请注意大小限制）
 
* **/file/download**    文件下载  
    需要参数：md5