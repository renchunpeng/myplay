package com.cpren.controller;

import com.cpren.utils.yw.YWJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("security")
public class SecurityController {

    @RequestMapping("userInfo")
    public String getUserInfo() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String s = YWJsonUtils.getMapper().writeValueAsString(authentication);
        return s;
    }
}
