package com.cpren.config.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.io.PrintWriter;

/**
 * @author cdxu@iyunwen.com on 2018/6/15
 */
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@EnableWebSecurity
public class FormLoginSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义密码校验器
     */
    @Autowired
    CustomPasswordEncoder customPasswordEncoder;

    /**
     * 用户去数据库查询基本信息
     */
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     * 登录处理
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启登录配置
        http.authorizeRequests()
                // 允许匿名的url - 可理解为放行接口 - 多个接口使用,分割
                .antMatchers(new String[]{"/login", "/user"}).permitAll()
                // 其余所有请求都需要认证
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()// 报错处理
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("小弟弟，你没有权限访问啊！！！");
                    out.flush();
                })// 没有权限访问时的处理方案
                .and()
                // 登录接口
                .formLogin().loginProcessingUrl("/login")
                // 配置登录失败的回调
                .failureHandler((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("登录失败...");
                    out.flush();
                })
                .permitAll()//和表单登录相关的接口统统都直接通过
                .and()
                // 注销接口
                .logout().logoutUrl("/logout")
                // 配置注销成功的回调
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("注销成功...");
                    out.flush();
                })
                .permitAll()
                .and()
                .httpBasic()
                .and()
                // 关闭CSRF跨域
                .csrf().disable();
    }

    /**
     * 忽略拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        // 这里设置忽略url，但是具体方法上需要权限的时候后台会直接报错，页面跳转到/error页面
        // 如果是在.antMatchers(new String[]{"/login", "/user"}).permitAll()中设置，页面是会跳转到登陆页面，登陆成功还没有权限
        // 就会跳转到设置的没有权限的处理方法中，最好只在这里做静态数据过滤吧
//        web.ignoring().antMatchers("/session/**");
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    /**
     * 这里是处理用户登录逻辑的主要配置方法
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(customPasswordEncoder);
        return authenticationProvider;
    }
}
