package com.cpren.config.springSecurity;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        String pwd = charSequence.toString();
        System.out.println("前端传过来的明文密码:" + pwd);
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String md5Pwd = encoder.encodePassword(pwd,"").toUpperCase();
        System.out.println("加密后:" + md5Pwd);
        return md5Pwd;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String pwd = charSequence.toString();
        System.out.println("前端传过来的明文密码:" + pwd);
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String md5Pwd = encoder.encodePassword(pwd,"").toUpperCase();
        System.out.println("加密后:" + md5Pwd);
        if(md5Pwd.equals(s)){
            System.out.println("pass");
            return true;
        }
        throw new DisabledException("--密码错误--");
    }
}
