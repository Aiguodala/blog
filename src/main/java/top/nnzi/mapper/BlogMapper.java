package top.nnzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import top.nnzi.bean.*;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-05 08:59
 **/
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("select * from t_blog b,t_type t where t.id=b.type_id order by update_time desc")
    @Results({
            @Result(
                    property = "type",
                    column = "type_id",
                    javaType = Type.class,
                    one = @One(select = "top.nnzi.mapper.TypeMapper.selectById")
            )
    })
    Page<Blog> adminFindAllBlogs (Page<Blog> page);


    @Select({"<script> " +
            "select * from t_blog b,t_type t" +
            " where  1 = 1 " +
            "and t.id=b.type_id" +
            "<if test='blog.typeId != null'> and b.type_id = #{blog.typeId}</if> " +
            "<if test='blog.title != null'> and b.title like &apos;%${blog.title}%&apos;</if> " +
            " order by update_time desc"+
            "</script>"}
            )
    @Results({
            @Result(
                    property = "type",
                    column = "type_id",
                    javaType = Type.class,
                    one = @One(select = "top.nnzi.mapper.TypeMapper.selectById")
            )
    })
    Page<Blog> adminSearchBlogs(Page<Blog> page,@Param("blog") Blog blog);

    @Insert("insert into t_blog_tag values (#{blogId},#{tagId})")
    void saveBlogAndTag(@Param("blogId") Long blogId,@Param("tagId") Long tagId);

    @Delete("delete from t_blog_tag where blog_id=#{id}")
    void deleteOriginBlogAndTag(Long id);

    @Select("SELECT * FROM t_blog WHERE recommend = TRUE ORDER BY update_time DESC LIMIT 6")
    List<Blog> findRecommendBlogsTop6();


    @Select("SELECT * FROM t_blog order by update_time desc")
    @Results({
            @Result(

                    property = "flag",
                    column = "flag_id",
                    javaType = Flag.class,
                    many = @Many(select = "top.nnzi.mapper.FlagMapper.selectById")

            ),
            @Result(

                    property = "user",
                    column = "user_id",
                    javaType = User.class,
                    one = @One(select = "top.nnzi.mapper.UserMapper.selectById")
            )
    })
    Page<Blog> indexBlogPage(Page<Blog> blogPage);

    @Select("SELECT * FROM t_blog where id=#{id}")
    @Results({
            @Result(

                    property = "flag",
                    column = "flag_id",
                    javaType = Flag.class,
                    many = @Many(select = "top.nnzi.mapper.FlagMapper.selectById")

            ),
            @Result(

                    property = "user",
                    column = "user_id",
                    javaType = User.class,
                    one = @One(select = "top.nnzi.mapper.UserMapper.selectById")
            )
    })
    Blog blogDetail(Long id);

    @Update("update t_blog set comment_count=comment_count+1 where id = #{blogId} ")
    void addCommentCount(Long blogId);

    @Select("select views from t_blog where id=#{id}")
    Integer getViewCount(Long id);

    @Select("select comment_count from t_blog where id=#{id}")
    Integer getCommentCount(Long id);


    @Select("select * from t_blog where type_id = #{id} order by update_time desc")
    @Results({
            @Result(

                    property = "flag",
                    column = "flag_id",
                    javaType = Flag.class,
                    many = @Many(select = "top.nnzi.mapper.FlagMapper.selectById")

            ),
            @Result(

                    property = "user",
                    column = "user_id",
                    javaType = User.class,
                    one = @One(select = "top.nnzi.mapper.UserMapper.selectById")
            )
    })
    Page<Blog> typesBlogPage(Page<Blog> blogPage, Long id);


    @Select("SELECT * FROM t_blog b,t_blog_tag bt WHERE b.id=bt.blog_id and tag_id = #{id} order by update_time desc")
    @Results({
            @Result(

                    property = "flag",
                    column = "flag_id",
                    javaType = Flag.class,
                    many = @Many(select = "top.nnzi.mapper.FlagMapper.selectById")

            ),
            @Result(

                    property = "user",
                    column = "user_id",
                    javaType = User.class,
                    one = @One(select = "top.nnzi.mapper.UserMapper.selectById")
            )
    })
    Page<Blog> tagsBlogPage(Page<Blog> blogPage, Long id);

    @Update("update t_blog set views=views+#{views} where id=#{id}")
    void updateViews(@Param("id") String key,@Param("views") Long size);
}
