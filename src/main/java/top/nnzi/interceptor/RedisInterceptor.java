package top.nnzi.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 19:45
 **/
@Component
public class RedisInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String id = String.valueOf(pathVariables.get("id"));
        if (redisTemplate.boundSetOps(id).size() == 0) {
            redisTemplate.boundSetOps(id).add(request.getRemoteAddr());
            redisTemplate.expire(id,2, TimeUnit.HOURS);
        }else {
            redisTemplate.boundSetOps(id).add(request.getRemoteAddr());
        }
        return true;
    }
}
