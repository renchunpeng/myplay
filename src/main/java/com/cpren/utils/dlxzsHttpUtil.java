package com.cpren.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import cn.hutool.json.JSONUtil;
import com.cpren.utils.yw.YWJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dlxzsHttpUtil {

    // POST请求
    public static String httpURLConnectionPOST(String Url, String parm) {
        try {
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();// 此时cnnection只是为一个连接对象,待连接中
            connection.setDoOutput(true); // 设置连接输出流为true,默认false (post
            // 请求是以流的方式隐式的传递参数)
            connection.setDoInput(true); // 设置连接输入流为true
            connection.setRequestMethod("POST"); // 设置请求方式为post
            connection.setUseCaches(false); // post请求缓存设为false
            connection.setInstanceFollowRedirects(true); // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setConnectTimeout(3000);
            // connection.setRequestProperty("Content-Type",
            // "application/x-www-form-urlencoded;charset=utf-8");
            connection.setRequestProperty("Content-Type",
                    "application/json;charset=utf-8");
            connection.connect(); // 建立连接
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection
                    .getOutputStream());
            dataout.writeBytes(parm);
            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
            // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder(); // 用来存储响应数据
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();
            connection.disconnect(); // 销毁连接
            return sb.toString();
        } catch (Exception e) {
            System.out.println("调用知识库接口异常" + Url + ";异常信息" + e.getMessage());
            return null;
        }
    }

    // POST请求
    public static String httpURLConnectionPOST1(String Url, String parm) {
        try {
            URL url = new URL(Url);
            // 此时cnnection只是为一个连接对象,待连接中
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置连接输出流为true,默认false (post
            connection.setDoOutput(true);
            /* 请求是以流的方式隐式的传递参数) */
            // 设置连接输入流为true
            connection.setDoInput(true);
            // 设置请求方式为post
            connection.setRequestMethod("POST");
            // post请求缓存设为false
            connection.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("Content-Type","application/json;charset=utf-8");

            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.connect(); // 建立连接

            ///////////////////20190724新方法方法//////////////////
            OutputStream out =connection.getOutputStream();
            out.write(parm.getBytes("UTF-8"));
            out.flush();
            out.close();
            ///////////////////20190724新方法方法/////////////////

            // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            InputStream io = connection.getInputStream();
            String line;
            // 用来存储响应数据
            StringBuilder sb = new StringBuilder();
            // 循环读取流,若不到结尾处
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = io.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len, StandardCharsets.UTF_8));
            }
            br.close();
            connection.disconnect(); // 销毁连接
            return sb.toString();
        } catch (Exception e) {
            System.out.println("调用知识库接口异常" + Url+ ";异常信息"
                    + e.getMessage());
            return null;
        }
    }

    // GET请求
    public static String httpURLConectionGET(String URLParam) {
        try {
            if (URLParam.contains("v1/smart-search/recent-update")) {
                URLParam = URLParam.replace("abcdefg=abcdefg?", "");
            }

            URL url = new URL(URLParam); // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();// 打开连接
            connection.setConnectTimeout(3000);
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            return sb.toString();
        } catch (Exception e) {
            System.out.println("调用知识库接口异常" + URLParam + ";异常信息"
                    + e.getMessage());
            return null;
        }
    }

    // delete请求
    public static String DELETE(String url, Map<String, String> dataForm) {
        HttpClient httpClient = new HttpClient();
        DeleteMethod deleteMethod = new DeleteMethod(url);

        List<NameValuePair> data = new ArrayList<NameValuePair>();
        if (dataForm != null) {
            Set<String> keys = dataForm.keySet();
            for (String key : keys) {
                NameValuePair nameValuePair = new NameValuePair(key,
                        (String) dataForm.get(key));
                data.add(nameValuePair);
            }
        }
        deleteMethod.setQueryString(data.toArray(new NameValuePair[0]));
        try {
            int statusCode = httpClient.executeMethod(deleteMethod);
            if (statusCode != HttpStatus.SC_OK) {
                return "Method failed: " + deleteMethod.getStatusLine();
            }
            byte[] responseBody = deleteMethod.getResponseBody();
            return new String(responseBody);
        } catch (IOException e) {
            System.out.println("调用知识库接口异常" + url + ";异常信息" + e.getMessage());
            return null;
        } finally {
            deleteMethod.releaseConnection();
        }
    }

    // put 请求
    public static String httpURLConnectionPut(String parm, String Url) {
        try {
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();// 此时cnnection只是为一个连接对象,待连接中
            connection.setDoOutput(true); // 设置连接输出流为true,默认false (post
            // 请求是以流的方式隐式的传递参数)
            connection.setDoInput(true); // 设置连接输入流为true
            connection.setRequestMethod("PUT"); // 设置请求方式为post
            connection.setUseCaches(false); // post请求缓存设为false
            connection.setInstanceFollowRedirects(true); // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=utf-8");
            connection.connect(); // 建立连接
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection
                    .getOutputStream());
            dataout.writeBytes(parm);
            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
            // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder(); // 用来存储响应数据
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();
            connection.disconnect(); // 销毁连接
            return sb.toString();
        } catch (Exception e) {
            System.out.println("调用知识库接口异常" + Url + ";异常信息" + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws JSONException, JsonProcessingException {
        String url = "http://192.168.1.72/ai-knowledge/knowledge/notice/v1/list/show?userName=sg001";
//        String url = "http://192.168.1.72/ai-knowledge/knowledge/knowledge/v1/label/region";
        JSONObject jsonObject = new JSONObject();
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        String s1 = JSONUtil.toJsonStr(list);
        System.out.println(s1);

        jsonObject.put("types",s1);
        JSONObject data = new JSONObject();
        data.put("data", jsonObject);

        String params = data.toString();
        System.out.println(params);
        String s = httpURLConnectionPOST1(url, params);
        System.out.println(s);
    }
}
