package com.cpren.config;

import com.cpren.pojo.User;
import com.cpren.utils.yw.YWJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/9/17
 */
//@Configuration
@Slf4j
public class InterceptorTest implements WebMvcConfigurer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${cpren.sso.findToken}")
    private String findToken;

    @Value("${cpren.sso.toLogin}")
    private String toLogin;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String[] str = {"/login","/loginout"};
                List<String> missUrl = Arrays.asList(str);
                String requestURI = request.getRequestURI();
                log.info("访问路径：" + requestURI);
                if(missUrl.contains(requestURI)) {
                    log.info("访问的是免登陆接口");
                }else {
                    String token = request.getParameter("token");
                    if(StringUtils.isEmpty(token)){
                        // 这里需要重定向到单点登录中心，看看客户端和认证中心之间是否存在token信息（一般是在cookie里判断，如果在其他系统登陆过，他们之间就会有token）
                        response.sendRedirect(findToken + "?oldUrl="+request.getRequestURL());
                    }else{
                        // 有token，这时候去redis中获取到token对应的userInfo就行
                        // 如果userInfo没找到，说明token过期了（这个几率很小，登录成功之后cookie和userInfo都会默认两小时）或者在其他系统注销了登录状态，需要重定向到认证中心的登录页面
                        String userInfo = stringRedisTemplate.opsForValue().get(token);
                        if(StringUtils.isEmpty(userInfo)) {
                            response.sendRedirect( toLogin + "?oldUrl="+request.getRequestURL());
                        }else {
                            // 找到了userInfo，userInfo中会包含这个用户所有的菜单权限，判断当前访问的地址是否存在于权限列表中，如果存在，拦截器放行，如果不存在，返回权限不足
                            log.info("校验通过，用户信息：" + userInfo);
                            User user = YWJsonUtils.getMapper().readValue(userInfo, User.class);
                            if(!user.getAuths().contains(requestURI)) {
                                response.setHeader("Content-type", "text/html;charset=utf-8");
                                response.getWriter().write("权限不足，禁止访问！");
                                return false;
                            }
                        }
                    }
                }

                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                System.out.println("拦截器postHandle");
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                System.out.println("拦截器afterHandle");
            }
        }).addPathPatterns("/**").excludePathPatterns("");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
