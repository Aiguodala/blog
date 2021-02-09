package top.nnzi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.nnzi.interceptor.LoginInterceptor;
import top.nnzi.interceptor.RedisInterceptor;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 15:46
 **/

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RedisInterceptor redisInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin","/admin/login");
        registry.addInterceptor(redisInterceptor)
                .addPathPatterns("/blog/**");
    }
}
