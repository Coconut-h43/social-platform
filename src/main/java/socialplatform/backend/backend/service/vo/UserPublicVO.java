package socialplatform.backend.backend.service.vo;  // ✅ vo包

import lombok.Data;
import socialplatform.backend.backend.model.User;  // 导入model
import java.util.Date;

@Data
public class UserPublicVO {
    private Integer id;
    private String username;
    private String avatar;
    private Date createTime;

    public static UserPublicVO fromUser(User user) {
        if (user == null) return null;
        UserPublicVO vo = new UserPublicVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setAvatar(user.getAvatar());
        vo.setCreateTime(user.getCreateTime());
        return vo;
    }
}