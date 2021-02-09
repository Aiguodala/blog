package top.nnzi.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 12:32
 **/

@Data
@ToString
@NoArgsConstructor
@TableName("t_blog")
@Document(indexName = "blog")
public class Blog {

    @TableId
    private Long id;

    private String title;
    private String content;//文章内容
    private String firstPicture;//首图
    private Long flagId;
    @TableField(exist = false)
    private Flag flag;//标记
    private Integer views;//浏览次数
    private boolean appreciation;//是否开启赞赏
    private boolean shareStatement;//版权开启
    private boolean commentabled;//评论开启
    private boolean published;//发布还是存草稿
    private boolean recommend;//是否推荐
    private String description;//描述
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//更新时间
    private Long typeId;
    private Long userId;
    private Integer commentCount;

    @TableField(exist = false)
    private Type type;
    @TableField(exist = false)
    private List<Tag> tags = new ArrayList<>();
    @TableField(exist = false)
    private String tagIds;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();


    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }


}
