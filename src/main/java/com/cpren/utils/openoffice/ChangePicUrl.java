package com.cpren.utils.openoffice;

import com.cpren.exception.BusinessException;
import com.cpren.utils.yw.YWDateUtils;
import com.cpren.utils.yw.YWFileUtils;
import com.cpren.utils.yw.YWHtmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/9/24
 */
@Slf4j
@Component
public class ChangePicUrl {
    private static final String picPath = "C:/Users/yunwen/Desktop/rcp/dist/pic/";

    private static final String lookPath = "http://localhost/pic/";

    /**
     * @describe 将图片路径替换为素材库中的图片地址
     * @author cp.ren
     * @date 2019-09-24 15:47:25
     * @param htmlStr
     * @param tempFilePath
     * @return
     * @version V5.0
     **/
    public String change(String htmlStr, String tempFilePath) throws UnsupportedEncodingException {
        List<HashMap<String, String>> imgs = YWHtmlUtils.getImgStr(htmlStr);
        for(HashMap<String, String> img : imgs){
            String src = img.get("SRC");
            log.debug("图片文件名：{}", URLDecoder.decode(src,"UTF-8"));
            File imgFile = new File(tempFilePath + URLDecoder.decode(src,"UTF-8"));
            if(imgFile.exists()){
                String fileUploadResult = saveFile(imgFile);
                htmlStr = htmlStr.replace(src, fileUploadResult);
            }
        }

        return htmlStr;
    }

    /**
     * @describe 将转化之后的图片存放到系统的素材库中
     * @author cp.ren
     * @date 2019-09-24 15:46:29
     * @param file
     * @return
     * @version V5.0
     **/
    public String saveFile(File file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getName();
        String relativePath = picPath + YWDateUtils.getSystemDate(YWDateUtils.DATE_FORMAT_PURE);
        YWFileUtils.isFolderExist(relativePath, "/");
        String filePath = relativePath + "/" + fileName;
        try {
            File toFile = new File(filePath);
            FileUtils.copyFile(file, toFile);
        } catch (IOException e) {
            throw new BusinessException("changePicUrl.saveFile");
        }
        return filePath.replaceAll(picPath,lookPath);
    }
}
