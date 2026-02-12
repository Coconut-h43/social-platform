package socialplatform.backend.backend.model;

import lombok.Data ;
import java.util.Date;

@Data
public class User {
    private Integer id ;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Date createTime;
    private Date updateTime;
    private Date lastPasswordChanged;
    private Date accountLockedUntil;
    private Integer status;
    private Integer roleId;
    private Integer loginFailCount;
}