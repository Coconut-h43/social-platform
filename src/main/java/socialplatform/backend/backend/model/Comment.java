package socialplatform.backend.backend.model;

import lombok.Data;
import java.util.Date;

@Data
public class Comment {
    private Integer id;//评论ID，自增
    private Integer userId;//评论者ID
    private Integer articleId;//评论的文章ID
    private String content;//评论内容
    private Integer parentId;//顶层父评论ID
    private Integer status;//评论状态，0删除，1正常
    private Date createTime;
    private Date updateTime;
}
