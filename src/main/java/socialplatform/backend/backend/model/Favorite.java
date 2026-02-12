package socialplatform.backend.backend.model;

import lombok.Data;
import java.util.Date;

@Data
public class Favorite {
    private Integer id;//收藏ID
    private Integer userId;//收藏者的ID
    private Integer articleId;//收藏的文章ID
    private Date createTime;//收藏创建时间

    private Article article;
}
