package socialplatform.backend.backend.service.vo;

import lombok.Data;
import java.util.Date;
import java.util.Map;

@Data
public class UserAdminStatsVO {
    private Integer id;
    private String username;
    private String email;
    private String avatar;
    private Date createTime;
    private Integer status;
    private Integer roleId;
    private Integer articleCount;
    private Integer publishedCount;
    private Integer totalViews;
    private Integer commentCount;
    private Date lastActivity;

    public static UserAdminStatsVO fromMap(Map<String, Object> map) {
        if (map == null) return null;
        UserAdminStatsVO vo = new UserAdminStatsVO();
        vo.setId((Integer) map.get("id"));
        vo.setUsername((String) map.get("username"));
        vo.setEmail((String) map.get("email"));
        vo.setAvatar((String) map.get("avatar"));
        vo.setCreateTime((Date) map.get("create_time"));
        vo.setStatus((Integer) map.get("status"));
        vo.setRoleId((Integer) map.get("role_id"));
        vo.setArticleCount(((Long) map.getOrDefault("article_count", 0L)).intValue());
        vo.setPublishedCount(((Long) map.getOrDefault("published_count", 0L)).intValue());
        vo.setTotalViews(((Long) map.getOrDefault("total_views", 0L)).intValue());
        vo.setCommentCount(((Long) map.getOrDefault("comment_count", 0L)).intValue());
        vo.setLastActivity((Date) map.get("last_activity"));
        return vo;
    }
}