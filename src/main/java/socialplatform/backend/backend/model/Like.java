package socialplatform.backend.backend.model;

import lombok.Data ;
import java.util.Date;

@Data
public class Like {
    private Integer id;//点赞ID
    private Integer userId;
    private Integer targetId;//目标ID（被点赞的文章ID或者评论ID）
    private Integer targetType;//目标类型，1为为文章，2为评论
    private Date createTime;//点赞时间
}
