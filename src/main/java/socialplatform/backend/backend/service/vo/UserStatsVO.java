package socialplatform.backend.backend.service.vo;

import lombok.Data;
import java.util.Date;

@Data
public class UserStatsVO {
    private Integer id;
    private Integer articleCount;
    private Integer publishedCount;
    private Integer draftCount;
    private Integer totalViews;
    private Date lastArticleTime;
}