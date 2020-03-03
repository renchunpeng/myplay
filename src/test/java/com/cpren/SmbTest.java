package com.cpren;

import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmbTest {

    public static void test(String urls) {
        SmbFile file;
        try {
            file = new SmbFile(urls);
            SmbFile[] smbFiles = file.listFiles();
            for (SmbFile smbFile : smbFiles) {
                System.out.println(smbFile.getPath());
                if(smbFile.isDirectory()){
                    test(smbFile.getPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test("smb://dev:dev123@192.168.1.133/dev/a张媛媛/");
    }
}
