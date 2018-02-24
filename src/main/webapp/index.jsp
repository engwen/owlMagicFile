<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="/js/jquery-2.1.0.js"></script>
</head>
<body>
<h2>use form commit file list demo</h2>
<form action="/file/uploadFilesByFrom" method="post" accept-charset="utf-8"  enctype="multipart/form-data">
    <input type="file" name="files" >
    <input type="file" name="files">
    <input type="file" name="files">
    <button type="submit" >tijiao</button>
</form>
<h2>use form commit one file demo</h2>
<form action="/file/uploadFileByFrom" method="post" accept-charset="utf-8"  enctype="multipart/form-data">
    <input type="file" name="file" >
    <button type="submit" >tijiao</button>
</form>
<h2>use ajax commit demo</h2>
<input type="file" onchange="uploadFile($(this))">
<script>
    function uploadFile(fileDIV) {
        var myform = new FormData();
        myform.append('file',$(fileDIV)[0].files[0]);
        $.ajax({
            url: "http://localhost:9293/file/uploadFileByFrom",
            type: "POST",
            data: myform,
            async: false,
            contentType: false,
            processData: false,
            success: function (result) {
                if(result && result.result){
                    alert(JSON.stringify(result))
                }else{
                   alert("文件上传失败")
                }
            },
            error:function(data){
                alert("文件上传失败")
            }
        });
    }
</script>
</body>
</html>
