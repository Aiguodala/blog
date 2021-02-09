package top.nnzi.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-07 15:06
 **/
@Configuration
public class ElasticSearchConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient () {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("121.196.166.231",9200,"http"))
        );
        return client;
    }
}
