package socialplatform.backend.backend.service.vo;

import lombok.Data;
import socialplatform.backend.backend.model.User;
import java.util.Date;

@Data
public class UserAdminVO {
    private Integer id;
    private String username;
    private String email;
    private String avatar;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    private Integer roleId;
    private Integer loginFailCount;
    private Date accountLockedUntil;
    private Date lastPasswordChanged;

    public static UserAdminVO fromUser(User user) {
        if (user == null) return null;
        UserAdminVO vo = new UserAdminVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setEmail(user.getEmail());
        vo.setAvatar(user.getAvatar());
        vo.setCreateTime(user.getCreateTime());
        vo.setUpdateTime(user.getUpdateTime());
        vo.setStatus(user.getStatus());
        vo.setRoleId(user.getRoleId());
        vo.setLoginFailCount(user.getLoginFailCount());
        vo.setAccountLockedUntil(user.getAccountLockedUntil());
        vo.setLastPasswordChanged(user.getLastPasswordChanged());
        return vo;
    }
}