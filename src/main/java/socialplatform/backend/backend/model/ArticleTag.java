package socialplatform.backend.backend.model;

import lombok.Data ;
import java.util.Date;

@Data
public class ArticleTag {
    private Integer id;
    private Integer articleId;
    private Integer tagId;
    private Date createTime;
}
