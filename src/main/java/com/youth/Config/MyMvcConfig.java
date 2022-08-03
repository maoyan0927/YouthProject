package com.youth.Config;

import com.youth.Util.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new MyInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(
                //添加不拦截路径
                "/websocket/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/**",
                "/swagger-ui.html/**",
                "/",
                "/expert/**",
                "/verify/**",
                "/auth/**",
                "/back/**",
                "/User/**",
                "/YouthInfo/**",
                "/Recognition/**",
                "/slicing/**",
                "/history/**",
                "/login",
                "/index",
                "/consumerLogin",
                "/consumerRegister",
                "/register/**",
                "/calculate",
                "/heightplot",
                "recoImg",
                "count",
                "savedicom",
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.png",
                "/**/*.ico",
                "/**/*.json",
                "/**/*.jpg",
                "/**/*.jpeg",
                "/**/*.svg",
                "/**/*.woff",
                "/**/*.ttf",
                "/**/*.eot"
        );
    }
}