package socialplatform.backend.backend.model;

import lombok.Data ;
import java.util.Date;

@Data
public class Article {
    private Integer id;//文章ID
    private Integer categoryId; //文章对应的分类ID
    private Integer authorId; //文章作者ID
    private String title; //文章标题
    private String summary;
    private String content;
    private Integer viewCount;//文章浏览量，默认为0
    private Integer isTop;//文章是否置顶，0为否，1为是
    private Integer status;//文章状态，0为草稿，1为发布
    private Date updateTime;//文章更新时间
    private Date createTime;//文章创建时间

    //关联的分类对象
    private Category category;
    private User author;
}
