package com.cpren.utils;

import com.cpren.pojo.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author cdxu@iyunwen.com on 2019/9/10
 */
public class JsonpUtils {
    public static void main(String[] args) throws IOException {
//        Document jsoup = Jsoup.connect("http://localhost/webaikn/#/library").get();
//        System.out.println(jsoup.outerHtml());

//        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
//        Date date = new Date();
//        String format = df.format(date);
//        System.out.println(format);

//        List<Integer> strings = Arrays.asList(1, 2, 3,2);
//        List<Integer> collect = strings.stream().map(i -> i * i).distinct().collect(Collectors.toList());
//        System.out.println(collect);

        List<User> list = new ArrayList();
        for(int x=0;x<100000;x++){
            User temp = new User();
            temp.setUserName("克隆"+x);
            list.add(temp);
        }
        long start = System.currentTimeMillis();
//        List<User> collect = list.stream().filter(x -> x.getUserName().contains("6661")).collect(Collectors.toList());
//        System.out.println(collect);
        List<User> collect = list.stream().sorted(Comparator.comparingInt(o -> o.getUserName().length())).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("耗时:"+(end-start));
    }



}
