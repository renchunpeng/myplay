package com.cpren.controller;

import cn.hutool.core.io.FileUtil;
import com.cpren.utils.openoffice.ChangePicUrl;
import com.cpren.utils.yw.YWFileUtils;
import com.cpren.utils.yw.YWSystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author cdxu@iyunwen.com on 2019/9/24
 */
@Controller
@RequestMapping("openoffice")
@Slf4j
public class OpenOfficeController {

    @Autowired
    private DocumentConverter documentConverter;

    @Autowired
    private ChangePicUrl changePicUrl;

    /**
     * @describe 将文件转化为html并写入输出流
     * @author cp.ren
     * @date 2019-09-24 14:04:34
     * @param response
     * @return
     * @version V5.0
     **/
    @RequestMapping("/convert")
    public void convertFile(HttpServletResponse response,String fileName) throws OfficeException, IOException {
        File srcFile = new File("D:/rcp/" + fileName);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String tempFilePath = "D:/rcp/" + uuid + "/";
        File targetFile = FileUtil.newFile(tempFilePath + uuid + ".html");
        documentConverter.convert(srcFile).to(targetFile).execute();
        // 替换图片链接
        String content = FileUtil.readString(targetFile, "GBK");
        String changeContent = changePicUrl.change(content, tempFilePath);
        // 将替换之后的文件内容覆盖写入原文件
        FileUtil.writeString(changeContent, targetFile, "GBK");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(targetFile);
            int count;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fileInputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer,0,count);
                // 解决windows和Linux解析后乱码问题
                if(YWSystemUtils.getOSName().toLowerCase().contains("linux")){
                    response.setContentType("text/html;charset=UTF-8");
                } else{
                    response.setContentType("text/html;charset=GBK");
                }
            }
            YWFileUtils.deleteFile(tempFilePath);
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            assert fileInputStream != null;
            fileInputStream.close();
        }
    }
}
