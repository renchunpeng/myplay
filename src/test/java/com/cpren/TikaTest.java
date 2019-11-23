package com.cpren;


import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.File;
import java.io.FileInputStream;

//@RunWith(SpringRunner.class)
//@SpringBootTest
/**
 * 搜索工具类
 */
@Slf4j
public class TikaTest {
    public static void main(final String[] args) throws Exception {
        File f = new File("D:/TestFile/pdftest.pdf");
        Tika tika = new Tika();
        Metadata metadata = new Metadata();
        metadata.set(Metadata.AUTHOR, "空号");//重新设置文档的媒体内容
        metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
        String str = tika.parseToString(new FileInputStream(f),metadata);
        for(String name:metadata.names()) {
            System.out.println(name+":"+metadata.get(name));
        }
        System.out.println(str);
    }
}