package com.cpren.config.springSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2018/6/15
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("login user:"+username);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //为每个授权中心对象写入权限名
        grantedAuthorities.add(new SimpleGrantedAuthority("/user"));
        /**此处的user是springsecurity中的一个实现了UserDetails接口的user类，因为我们没有将entity中的user去实现
         * UserDetails接口，所以只能在此处调用实现好的构造方法
         */
        return new User(username, "123456", grantedAuthorities);
    }

}
