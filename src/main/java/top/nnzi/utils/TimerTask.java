package top.nnzi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.nnzi.mapper.BlogMapper;
import top.nnzi.service.BlogService;

import java.util.Set;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 20:54
 **/
@Component
public class TimerTask {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 */2 * * ? ")
    public void scheduledTask () {
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            Long size = redisTemplate.boundSetOps(key).size();
            blogMapper.updateViews(key,size);
        }
    }
}
