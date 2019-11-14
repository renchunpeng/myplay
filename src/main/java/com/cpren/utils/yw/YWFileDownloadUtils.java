package com.cpren.utils.yw;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class YWFileDownloadUtils {

    /**
     * 下载文件
     * @param file 待下载的文件
     * @param response 响应流
     * @return
     */
    public static void download(File file, HttpServletResponse response) throws IOException {
        if (null != file && file.exists()) {
            InputStream inputStream = null;
            OutputStream os = null;

            try {
                // 设置响应头和客户端保存文件名
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
                os = response.getOutputStream();
                inputStream = new FileInputStream(file);
                response.setHeader("Content-Length", String.valueOf(file.length()));

                // 循环写入输出流
                byte[] b = new byte[1024 * 8];
                int length;
                while ((length = inputStream.read(b)) > 0) {
                    os.write(b, 0, length);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                // 关闭流
                if (null != os) {
                    try { os.close(); } catch (Exception e) {}
                }

                if (null != inputStream) {
                    try { inputStream.close(); } catch (Exception e) {}
                }
            }
        }
    }
}
