package top.nnzi;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.nnzi.bean.Blog;
import top.nnzi.mapper.BlogMapper;
import top.nnzi.mapper.TypeMapper;
import top.nnzi.service.BlogService;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private BlogService blogService;

    @Test
    void contextLoads() throws IOException {


        List<Blog> list = blogService.searchBlogs("帅");
        for (Blog blog : list) {
            System.out.println(blog);
        }
    }




}
